(ns gh4st.game
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    )
  (:require
    [cljs.core.async :refer [timeout <!]]
    [gh4st.state :refer [app-state]]
    [gh4st.ai :refer [tick-actor]]
    [gh4st.board :refer [ghost-positions]]
    [gh4st.levels :refer [levels attract shuffle-level-actors]]
    [gh4st.texts :refer [texts]]
    [gh4st.history :as history]
    [gh4st.img :refer [fruits]]
    ))

(defonce max-level
  (atom 0))

(def advancing?
  "True from the time a Ghost moves to the time Pacman stops moving."
  (atom false))

(defn remember! []
  (let [state @app-state]
    (history/commit! state)))

(defn try-undo! []
  (when-not @advancing?
    (history/undo! @app-state)))

(defn try-redo! []
  (when-not @advancing?
    (history/redo!)))

(defn check-game-over! []
  (let [actors (:actors @app-state)
        on-ghost? (set (ghost-positions actors))
        pacman-pos (-> actors :pacman :pos)
        fruit-pos (-> actors :fruit :pos)]
    (cond
      (on-ghost? pacman-pos)
      (do
        (swap! max-level max (inc (:level @app-state)))
        (swap! app-state assoc :end :victory))

      (= fruit-pos pacman-pos)
      (if (:allow-defeat @app-state)
        (do
          (swap! max-level max (inc (:level @app-state))) 
          (swap! app-state assoc :end :defeat-allowed))
        (swap! app-state assoc :end :defeat))

      :else nil)))

(def last-anim-id
  (atom {:pacman 0
         :blinky 0
         :pinky 0
         :inky 0
         :clyde 0}))

(defn animate-actor!
  [name-]
  (go
    (let [set-anim! (fn [on](swap! app-state #(assoc-in % [:actors name- :anim?] on)))
          curr-id #(get @last-anim-id name-)
          id (inc (curr-id))]
      (swap! last-anim-id assoc name- id)
      (set-anim! true)
      (<! (timeout 300))
      (when (= id (curr-id))
        (set-anim! false)))))

(defn try-tick-actor!
  [name-]
  (when-not (:end @app-state)
    (swap! app-state tick-actor name-)
    (animate-actor! name-)
    (check-game-over!)))

(defn advance!
  [name-]
  (when-not (or @advancing?
                (:end @app-state))
    (when (get-in @app-state [:actors name- :pos])
      (reset! advancing? true)
      (remember!)
      (go
        (try-tick-actor! name-)
        (<! (timeout 100))
        (try-tick-actor! :pacman)
        (reset! advancing? false)))))

(defn prevent-actor-transitions! []
  (let [no-trans! #(swap! app-state assoc :no-transitions? %)]
    (no-trans! true)
    (go
      (<! (timeout 100))
      (no-trans! false))))

(defn load-custom-level!
  [data]
  (history/forget-all!)
  (prevent-actor-transitions!)
  (swap! app-state assoc :level (rand-int (count fruits)))
  (swap! app-state assoc :end nil)
  (swap! app-state merge data))

(defn load-level!
  [n]
  (let [data (get levels n)]
    (load-custom-level! data)
    (swap! app-state assoc :level n)
    (swap! max-level max n)
    (swap! app-state assoc :level-text (get texts n))))

(defn restart-level! []
  (load-level! (:level @app-state)))

(defn try-next-level! []
  (let [level (:level @app-state)]
    (when (< level @max-level)
      (load-level! (inc level)))))

(defn try-prev-level! []
 (let [level (:level @app-state)]
    (when (> level 0)
      (load-level! (dec level)))))

(defn start-game!
  ([] (start-game! 0))
  ([i]
   (swap! app-state assoc :screen :game)
   (swap! app-state assoc-in [:settings :paths :enabled] false)
   (swap! app-state assoc-in [:settings :targets :enabled] false)
   (load-level! i)))

(defn shuffle-level-actors! []
  (restart-level!)
  (let [shuffled (shuffle-level-actors (get levels (:level @app-state)))]
    (swap! app-state assoc :actors (:actors shuffled))))

(defn teach-toggle! []
  (swap! app-state update-in [:teach-mode] not))

(defn teach-key! [n]
  (swap! app-state assoc :teach-key n))

(def key-functions
  {"1" #(do (advance! :blinky) (teach-key! 1))
   "2" #(do (advance! :pinky)  (teach-key! 2))
   "4" #(do (advance! :inky)  (teach-key! 4))
   "3" #(do (advance! :clyde)  (teach-key! 3))

   "z" try-undo!
   "y" try-redo!

   "shift+right" try-next-level!
   "shift+left" try-prev-level!

   "shift+s" shuffle-level-actors!
   "ctrl+0" teach-toggle!
   "r" restart-level!})


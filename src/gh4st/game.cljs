(ns gh4st.game
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    )
  (:require
    [cljs.core.async :refer [timeout <!]]
    [gh4st.state :refer [app-state]]
    [gh4st.ai :refer [tick-actor]]
    [gh4st.board :refer [ghost-positions]]
    [gh4st.levels :refer [levels]]
    [gh4st.texts :refer [texts]]
    [gh4st.history :as history]
    ))

(defonce max-level
  (atom 0))

(def advancing?
  "True from the time a Ghost moves to the time Pacman stops moving."
  (atom false))

(defn try-undo! []
  (when-not @advancing?
    (history/undo! @app-state)))

(defn try-redo! []
  (when-not @advancing?
    (history/redo!)))

(defn remembered-state
  "Get the current state without the animation flags."
  [state]
  (reduce 
    (fn [state name-]
      (let [path [:actors name- :anim?]]
        (cond-> state
          (get-in state path) (assoc-in path false))))
    state
    [:blinky :pinky :inky :clyde :pacman]))

(defn remember! []
  (let [state (remembered-state @app-state)]
    (history/commit! state)))

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

(defn animate-actor!
  [name-]
  (go
    (let [set-anim! (fn [on] (swap! app-state #(assoc-in % [:actors name- :anim?] on)))]
      (set-anim! true)
      (<! (timeout 300))
      (set-anim! false))))

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

(js/Mousetrap.bind "1" #(advance! :blinky))
(js/Mousetrap.bind "2" #(advance! :pinky))
(js/Mousetrap.bind "4" #(advance! :inky))
(js/Mousetrap.bind "3" #(advance! :clyde))

(js/Mousetrap.bind "z" try-undo!)
(js/Mousetrap.bind "y" try-redo!)

(defn load-level!
  [n]
  (let [data (get levels n)]
    (history/forget-all!)
    (swap! app-state assoc :level n)
    (swap! max-level max n)
    (swap! app-state assoc :end nil)
    (swap! app-state merge data)
    (swap! app-state assoc :level-text (get texts n))))

(defn restart-level! []
  (load-level! (:level @app-state)))

(js/Mousetrap.bind "r" restart-level!)

(defn try-next-level! []
  (let [level (:level @app-state)]
    (when (< level @max-level)
      (load-level! (inc level)))))

(defn try-prev-level! []
 (let [level (:level @app-state)]
    (when (> level 0)
      (load-level! (dec level)))))

(js/Mousetrap.bind "shift+right" try-next-level!)
(js/Mousetrap.bind "shift+left" try-prev-level!)

(defn start-game! []
  (swap! app-state assoc :screen :game)
  (load-level! 0))


(ns gh4st.game
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    )
  (:require
    [cljs.core.async :refer [timeout <!]]
    [gh4st.state :refer [app-state]]
    [gh4st.ai :refer [move-actor
                      steer-actor]]
    [gh4st.board :refer [ghost-positions]]
    [gh4st.levels :refer [levels]]
    ))

;;----------------------------------------------------------------------
;; History Stack
;;----------------------------------------------------------------------

(declare advancing?)

(def history-index
  "index where next item will be remembered"
  (atom 0))

(def history-stack
  (atom []))

(defn undo! [curr-state]
  (when-not @advancing?
    (when (pos? @history-index)

      ;; Allow us to redo what we're undoing if this moment isn't remembered yet.
      (when-not (get @history-stack @history-index)
        (swap! history-stack conj curr-state))

      (swap! history-index dec)
      (reset! app-state (get @history-stack @history-index)))))

(defn redo! []
  (when-not @advancing?
    (when-let [state (get @history-stack (inc @history-index))]
      (reset! app-state state)
      (swap! history-index inc))))

(defn remember! [curr-state]
  (swap! history-stack subvec 0 @history-index)
  (swap! history-stack conj curr-state)
  (swap! history-index inc))

;;----------------------------------------------------------------------
;; State progression
;;----------------------------------------------------------------------

(def advancing?
  (atom false))

(declare steer-all!)

(defn check-game-over! []
  (let [actors (:actors @app-state)
        on-ghost? (set (ghost-positions actors))
        pacman-pos (-> actors :pacman :pos)
        fruit-pos (-> actors :fruit :pos)]
    (cond
      (on-ghost? pacman-pos) (swap! app-state assoc :end :victory)
      (= fruit-pos pacman-pos) (swap! app-state assoc :end :defeat)
      :else nil)))

(defn move-actor!
  [name-]
  (when-not (:end @app-state)
    (swap! app-state #(assoc-in % [:actors name- :pos] (move-actor name- %)))
    (check-game-over!)
    (steer-all!)))

(defn steer-actor!
  [name-]
  (swap! app-state #(assoc-in % [:actors name- :dir] (steer-actor name- %))))

(defn steer-all! []
  (doseq [name- [:pacman :blinky :pinky :inky :clyde]]
    (steer-actor! name-)))

(defn advance!
  [name-]
  (when-not @advancing?
    (when (get-in @app-state [:actors name- :pos])
      (reset! advancing? true)
      (remember! @app-state)
      (go
        (move-actor! name-)
        (<! (timeout 300))
        (move-actor! :pacman)
        (reset! advancing? false)))))


(js/Mousetrap.bind "a" #(advance! :blinky))
(js/Mousetrap.bind "s" #(advance! :pinky))
(js/Mousetrap.bind "d" #(advance! :inky))
(js/Mousetrap.bind "f" #(advance! :clyde))

(js/Mousetrap.bind "z" #(undo! @app-state))
(js/Mousetrap.bind "y" redo!)

(defn load-level!
  [n]
  (let [data (get levels n)]
    (swap! app-state merge data)))

(defn start-game! []
  (swap! app-state assoc :screen :game)
  (load-level! 0))


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
    [gh4st.texts :refer [texts]]
    ))

;;----------------------------------------------------------------------
;; History Stack
;;----------------------------------------------------------------------

(declare advancing?)

(defonce max-level
  (atom 0))

(defonce history-index
  ;; "index where next item will be remembered"
  (atom 0))

(defonce history-stack
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

(defn flush-history! []
  (reset! history-index 0)
  (swap! history-stack empty))

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

(defn move-actor!
  [name-]
  (when-not (:end @app-state)
    (let [pos (get-in @app-state [:actors name- :pos])]
      (swap! app-state #(assoc-in % [:actors name- :pos] (move-actor name- %)))
      (swap! app-state #(assoc-in % [:actors name- :prev-pos] pos)))
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
  (when-not (or @advancing?
                (:end @app-state))
    (when (get-in @app-state [:actors name- :pos])
      (reset! advancing? true)
      (remember! @app-state)
      (go
        (move-actor! name-)
        (<! (timeout 300))
        (move-actor! :pacman)
        (reset! advancing? false)))))


(js/Mousetrap.bind "1" #(advance! :blinky))
(js/Mousetrap.bind "2" #(advance! :pinky))
(js/Mousetrap.bind "4" #(advance! :inky))
(js/Mousetrap.bind "3" #(advance! :clyde))

(js/Mousetrap.bind "z" #(undo! @app-state))
(js/Mousetrap.bind "y" redo!)

(defn load-level!
  [n]
  (let [data (get levels n)]
    (flush-history!)
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


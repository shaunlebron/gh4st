(ns gh4st.game
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    )
  (:require
    [cljs.core.async :refer [timeout <!]]
    [gh4st.state :refer [app-state]]
    [gh4st.ai :refer [move-actor
                      steer-actor]]
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

(defn progress-states
  [name- state]
  (let [new-pos (move-actor name- state)
        state1 (assoc-in state [:actors name- :pos] new-pos)
        new-dir (steer-actor name- state1)
        state2 (assoc-in state1 [:actors name- :dir] new-dir)]
    [state1 state2]))

(defn advance-pacman! []
  (go
    (let [[moved steered] (progress-states :pacman @app-state)]
      (reset! app-state moved)
      (reset! app-state steered))))

(defn advance!
  [name-]
  (when-not @advancing?
    (when (get-in @app-state [:actors name- :pos])
      (remember! @app-state)
      (go
        (let [[moved steered] (progress-states name- @app-state)]

          (reset! app-state moved)
          ;; (<! (timeout 500))

          (reset! app-state steered)
          ;; (<! (timeout 500))

          (<! (advance-pacman!))

          ;; TODO: determine winner?

          (reset! advancing? false)

          )))))


(js/Mousetrap.bind "a" #(advance! :blinky))
(js/Mousetrap.bind "s" #(advance! :pinky))
(js/Mousetrap.bind "d" #(advance! :inky))
(js/Mousetrap.bind "f" #(advance! :clyde))

(js/Mousetrap.bind "z" #(undo! @app-state))
(js/Mousetrap.bind "y" redo!)


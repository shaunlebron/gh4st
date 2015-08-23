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

(def history-stack
  (atom []))

(def advancing?
  (atom false))

(defn undo! []
  (when-not @advancing?
    ))

(defn redo! []
  (when-not @advancing?
    ))

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
      (<! (timeout 500))
      (reset! app-state steered))))

(defn advance!
  [name-]
  (when-not @advancing?
    (when (get-in @app-state [:actors name- :pos])
      ;; TODO: push onto history stack
      (go
        (let [[moved steered] (progress-states name- @app-state)]

          (reset! app-state moved)
          (<! (timeout 500))

          (reset! app-state steered)
          (<! (timeout 500))

          (<! (advance-pacman!))

          ;; TODO: determine winner?

          (reset! advancing? false)

          )))))


(js/Mousetrap.bind "a" #(advance! :blinky))
(js/Mousetrap.bind "s" #(advance! :pinky))
(js/Mousetrap.bind "d" #(advance! :inky))
(js/Mousetrap.bind "f" #(advance! :clyde))

(js/Mousetrap.bind "mod+z" undo!)
(js/Mousetrap.bind "mod+y" redo!)


(ns gh4st.ui
  (:require
    [cljs.pprint :refer [pprint]]
    [gh4st.state :refer [app-state]]
    [gh4st.board :refer [bound-pos]]
    )
  )

(defn select-cell!
  [pos]
  (swap! app-state assoc :select-pos pos))

(defn toggle-tile
  [value]
  (get {:floor :wall
        :wall :floor} value))

(defn toggle-actor
  [actor pos]
  (if (:pos actor)
    (assoc actor :pos nil)
    (assoc actor :pos pos)))

(defn toggle-selected-tile! []
  (when-let [[x y] (:select-pos @app-state)]
    (if-let [actor (:select-actor @app-state)]
      (swap! app-state update-in [:actors actor] toggle-actor [x y])
      (swap! app-state update-in [:board y x] toggle-tile))))

(defn set-mode!
  [mode]
  (swap! app-state assoc :select-actor mode))

(defn set-select-dir!
  [dir]
  (when-let [actor (:select-actor @app-state)]
    (swap! app-state assoc-in [:actors actor :dir] dir)))

(defn move-selection! [[dx dy]]
  (if-let [[x y] (:select-pos @app-state)]
    (swap! app-state assoc-in [:select-pos] (bound-pos [(+ x dx) (+ y dy)] (:board @app-state)))
    (swap! app-state assoc :select-pos [0 0])))

(defn disable-select! []
  (swap! app-state assoc :select-pos nil))

(js/Mousetrap.bind "shift+x" toggle-selected-tile!)
(js/Mousetrap.bind "ctrl+x" #(disable-select!))

(js/Mousetrap.bind "shift+k" #(move-selection! [0 -1]))
(js/Mousetrap.bind "shift+j" #(move-selection! [0 1]))
(js/Mousetrap.bind "shift+h" #(move-selection! [-1 0]))
(js/Mousetrap.bind "shift+l" #(move-selection! [1 0]))

(js/Mousetrap.bind "ctrl+k" #(set-select-dir! [0 -1]))
(js/Mousetrap.bind "ctrl+j" #(set-select-dir! [0 1]))
(js/Mousetrap.bind "ctrl+h" #(set-select-dir! [-1 0]))
(js/Mousetrap.bind "ctrl+l" #(set-select-dir! [1 0]))

(js/Mousetrap.bind "shift+1" #(set-mode! nil))
(js/Mousetrap.bind "shift+2" #(set-mode! :pacman))
(js/Mousetrap.bind "shift+3" #(set-mode! :fruit))
(js/Mousetrap.bind "shift+q" #(set-mode! :blinky))
(js/Mousetrap.bind "shift+w" #(set-mode! :pinky))
(js/Mousetrap.bind "shift+e" #(set-mode! :inky))
(js/Mousetrap.bind "shift+r" #(set-mode! :clyde))

(js/Mousetrap.bind "shift+p" #(pprint @app-state))


(ns gh4st.editor
  (:require
    [cljs.pprint :refer [pprint]]
    [gh4st.state :refer [app-state]]
    [gh4st.board :refer [bound-pos
                         toggle-tile]]
    [gh4st.history :refer [commit!]]
    )
  )

(defn select-cell!
  [pos]
  (swap! app-state assoc :select-pos pos))

(defn toggle-actor
  [actor pos]
  (commit! @app-state)
  (if (:pos actor)
    (assoc actor :pos nil)
    (assoc actor :pos pos
                 :prev-pos nil)))

(defn toggle-selected-tile! []
  (when-let [[x y] (:select-pos @app-state)]
    (commit! @app-state)
    (if-let [actor (:select-actor @app-state)]
      (swap! app-state update-in [:actors actor] toggle-actor [x y])
      (swap! app-state update-in [:board y x] toggle-tile))))

(defn set-mode!
  [mode]
  (swap! app-state assoc :select-actor mode))

(defn set-select-dir!
  [dir]
  (when-let [actor (:select-actor @app-state)]
    (commit! @app-state)
    (swap! app-state assoc-in [:actors actor :dir] dir)
    (swap! app-state assoc-in [:actors actor :prev-pos] nil)))

(defn move-selection! [[dx dy]]
  (if-let [[x y] (:select-pos @app-state)]
    (swap! app-state assoc-in [:select-pos] (bound-pos [(+ x dx) (+ y dy)] (:board @app-state)))
    (swap! app-state assoc :select-pos [0 0])))

(defn disable-select! []
  (swap! app-state assoc :select-pos nil))

(def key-functions
  {"shift+x" toggle-selected-tile!
   "ctrl+x" disable-select!

   "shift+k" #(move-selection! [0 -1])
   "shift+j" #(move-selection! [0 1])
   "shift+h" #(move-selection! [-1 0])
   "shift+l" #(move-selection! [1 0])

   "ctrl+k" #(set-select-dir! [0 -1])
   "ctrl+j" #(set-select-dir! [0 1])
   "ctrl+h" #(set-select-dir! [-1 0])
   "ctrl+l" #(set-select-dir! [1 0])

   "shift+q" #(set-mode! nil)
   "shift+w" #(set-mode! :pacman)
   "shift+e" #(set-mode! :fruit)
   "shift+1" #(set-mode! :blinky)
   "shift+2" #(set-mode! :pinky)
   "shift+3" #(set-mode! :clyde)
   "shift+4" #(set-mode! :inky)

   "shift+p" #(pprint @app-state)
   })

(defn enable-keys []
  (doseq [[k f] key-functions]
    (js/Mousetrap.bind k f)))

(defn disable-keys []
  (doseq [[k f] key-functions]
    (js/Mousetrap.unbind k)))

(ns ^:figwheel-always ld33-gh4st.core
    (:require
      [cljs.pprint :refer [pprint]]
      [sablono.core :refer-macros [html]]
      [om-tools.core :refer-macros [defcomponent]]
      [om.core :as om]
      ))

(def level1
  {:board
   [[:wall :wall :wall :wall :wall :wall :wall]
    [:wall :floor :floor :floor :wall :wall :wall]
    [:wall :wall :wall :floor :wall :floor :floor]
    [:floor :wall :wall :floor :wall :floor :wall]
    [:floor :floor :floor :floor :floor :floor :wall]
    [:floor :wall :wall :wall :wall :wall :wall]],
   :select-pos [0 0],
   :select-actor nil,
   :level 0,
   :actors
   {:pacman {:pos [2 1], :dir [1 0]},
    :blinky {:pos [0 3], :dir [0 1]},
    :pinky {:pos nil, :dir [0 1]},
    :inky {:pos nil, :dir [0 1]},
    :clyde {:pos nil, :dir [0 1]}, 
    :fruit {:pos [6 2]}}})

;; https://craig.is/killing/mice
;; keyboard utility

(enable-console-print!)

(defn empty-board
  "create initial empty board"
  [w h]
  (vec (repeat h (vec (repeat w :wall)))))

(defonce app-state
  (atom level1))

(defn bound
  [x0 x x1]
  (cond
    (< x x0) x0
    (> x x1) x1
    :else x))

(defn bound-pos
  [[x y]]
  [(bound 0 x (-> @app-state :board first count dec))
   (bound 0 y (-> @app-state :board count dec))])

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
    (swap! app-state assoc-in [:select-pos] (bound-pos [(+ x dx) (+ y dy)]))
    (swap! app-state assoc :select-pos [0 0])))

(js/Mousetrap.bind "x" toggle-selected-tile!)

(js/Mousetrap.bind "k" #(move-selection! [0 -1]))
(js/Mousetrap.bind "j" #(move-selection! [0 1]))
(js/Mousetrap.bind "h" #(move-selection! [-1 0]))
(js/Mousetrap.bind "l" #(move-selection! [1 0]))

(js/Mousetrap.bind "shift+k" #(set-select-dir! [0 -1]))
(js/Mousetrap.bind "shift+j" #(set-select-dir! [0 1]))
(js/Mousetrap.bind "shift+h" #(set-select-dir! [-1 0]))
(js/Mousetrap.bind "shift+l" #(set-select-dir! [1 0]))

(js/Mousetrap.bind "1" #(set-mode! nil))
(js/Mousetrap.bind "2" #(set-mode! :pacman))
(js/Mousetrap.bind "3" #(set-mode! :fruit))
(js/Mousetrap.bind "q" #(set-mode! :blinky))
(js/Mousetrap.bind "w" #(set-mode! :pinky))
(js/Mousetrap.bind "e" #(set-mode! :inky))
(js/Mousetrap.bind "r" #(set-mode! :clyde))

(js/Mousetrap.bind "p" #(pprint @app-state))

(def actor-order
  [:blinky
   :pinky
   :inky
   :clyde
   :pacman
   :fruit])

(def dir->name
  {[0 1]  "-d"
   [0 -1] "-u"
   [1 0]  "-r"
   [-1 0] "-l"})

(defn img-src
  [name- dir]
  (if (= name- :fruit)
    (str "img/" (name name-) (:level @app-state) ".png")
    (str "img/" (name name-) (dir->name dir) ".png")))

(defn on-js-reload []
  nil)

(defn select-cell
  [pos]
  (swap! app-state assoc :select-pos pos))

(defn actor
  [actors drawpos]
  (for [name- actor-order]
    (let [{:keys [pos dir]} (get actors name-)]
      (when (= drawpos pos)
        [:img.sprite {:src (img-src name- dir)
                      :on-click #(select-cell drawpos)}]))))

(defn cell
  [data value [x y :as pos]]
  [:div
   {:class (cond-> (str "cell " (name value))
             (= [x y] (:select-pos data)) (str " selected-cell")
             (:select-actor data) (str " selected-" (name (:select-actor data))))
    :on-click #(select-cell pos)}
   (actor (:actors data) pos)])

(defcomponent board
  [data owner]
  (render [_this]
    (html
      [:div.board
       (for [[y row] (map-indexed vector (:board data))]
         [:div.row
          (for [[x value] (map-indexed vector row)]
            (cell data value [x y]))])])))

(defcomponent root
  [data owner]
  (render [_this]
    (html
      (om/build board data)
      )))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

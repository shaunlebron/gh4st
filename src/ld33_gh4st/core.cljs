(ns ^:figwheel-always ld33-gh4st.core
    (:require
      [sablono.core :refer-macros [html]]
      [om-tools.core :refer-macros [defcomponent]]
      [om.core :as om]
      ))

(enable-console-print!)

(defn empty-board
  "create initial empty board"
  [w h]
  (vec (repeat h (vec (repeat w :floor)))))

(defonce app-state
  (atom {:board (empty-board 7 6)
         :select-pos nil
         :level 0
         :actors {:pacman {:pos [0 1] :dir [1 0]}
                  :blinky {:pos [0 2] :dir [0 1]}
                  :fruit {:pos [3 5]}
                  }
         }))

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
             (= [x y] (:select-pos data)) (str " selected-cell"))
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

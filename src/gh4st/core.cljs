(ns ^:figwheel-always gh4st.core
    (:require
      [sablono.core :refer-macros [html]]
      [om-tools.core :refer-macros [defcomponent]]
      [om.core :as om]
      [gh4st.state :refer [app-state]]
      [gh4st.ui :refer [select-cell!]]
      [gh4st.img :refer [actor-order img-src]]
      ))

(enable-console-print!)

(defn actor
  [actors drawpos]
  (for [name- actor-order]
    (let [{:keys [pos dir]} (get actors name-)]
      (when (= drawpos pos)
        [:img.sprite {:src (img-src name- dir)
                      :on-click #(select-cell! drawpos)}]))))

(defn cell
  [data value [x y :as pos]]
  [:div
   {:class (cond-> (str "cell " (name value))
             (= [x y] (:select-pos data)) (str " selected-cell")
             (:select-actor data) (str " selected-" (name (:select-actor data))))
    :on-click #(select-cell! pos)}
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

(defn on-js-reload []
  nil)


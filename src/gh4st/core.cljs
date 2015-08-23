(ns ^:figwheel-always gh4st.core
  (:require-macros
    [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs.core.async :refer [<! timeout chan close! alts!]]
    [sablono.core :refer-macros [html]]
    [om-tools.core :refer-macros [defcomponent]]
    [om.core :as om]
    [gh4st.state :refer [app-state]]
    [gh4st.ui :refer [select-cell!]]
    [gh4st.img :refer [actor-order img-src]]
    [gh4st.game :refer [start-game!]]
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

(def stop-welcome-anim nil)

(def next-home-actor
  {:pacman :blinky
   :blinky :pinky
   :pinky :inky
   :inky :clyde
   :clyde :pacman})

(defcomponent welcome
  [data owner]
  (will-mount [_this]
    (set! stop-welcome-anim (chan))
    (go-loop []
      (let [[c v] (alts! [(timeout 400) stop-welcome-anim])]
        (when-not (= c stop-welcome-anim)
          (swap! app-state update-in [:home-actor] next-home-actor)
          (swap! app-state assoc :home-bump true)
          (<! (timeout 20))
          (swap! app-state assoc :home-bump false)
          (recur))))
    (js/Mousetrap.bind "enter" #(start-game!)))
  (will-unmount [_this]
    (close! stop-welcome-anim))
  (render [_this]
    (html
      [:div.home
       (let [name- (:home-actor data)]
         [:h1 {:class (cond-> (str "color-" (name name-))
                        (:home-bump data) (str " bump"))}
          "GH" [:img.ghost {:src (img-src name- [0 1])}] "ST"])
       [:p "Press " [:em "ENTER"] " to start."]])))

(defcomponent root
  [data owner]
  (render [_this]
    (html
      (case (:screen data)
        :home (om/build welcome data)
        :game (om/build board data)
        nil)
      
      )))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

(defn on-js-reload []
  nil)


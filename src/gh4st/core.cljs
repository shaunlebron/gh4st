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
    [gh4st.texts :refer [victory-text
                         defeat-text
                         allow-defeat-text
                         ]]
    ))

(enable-console-print!)

(defn actor
  [actors drawpos]
  (for [name- actor-order]
    (let [{:keys [pos dir]} (get actors name-)]
      (when (= drawpos pos)
        [:img.sprite {:src (img-src name- dir)
                      :on-click #(select-cell! drawpos)}]))))

(defn normalize-end
  [end]
  (if (= end :defeat-allowed)
    :defeat
    end))

(defn cell
  [data value [x y :as pos]]
  [:div
   {:class (cond-> (str "cell " (name value))
             (= [x y] (:select-pos data)) (str " selected-cell")
             (:end data) (str " " (name (normalize-end (:end data))))
             (:select-actor data) (str " selected-" (name (:select-actor data))))

    ;; FIXME: re-enable for mouse editor control
    ;; :on-click #(select-cell! pos)
    
    }
   (actor (:actors data) pos)])

(defcomponent game
  [data owner]
  (render [_this]
    (html
      [:div.game
       
       [:div.title (-> data :level-text :title)]
       [:div.desc
        (let [end (:end data)]
          (cond
            (= end :victory) victory-text
            (= end :defeat) defeat-text
            (= end :defeat-allowed) allow-defeat-text
            :else (-> @data :level-text :desc)))]
       [:div.board
        (for [[y row] (map-indexed vector (:board data))]
          [:div.row
           (for [[x value] (map-indexed vector row)]
             (cell data value [x y]))])]
       [:div.controls]])))

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
         [:h1 {:class (cond-> (str (name name-))
                        (:home-bump data) (str " bump"))}
          "GH" [:img.ghost {:src (img-src name- [0 1])}] "ST"])
       [:p.instruct [:em "PRESS ENTER"]]
       [:p.author "by " [:a {:href "http://twitter.com/shaunlebron"} "@shaunlebron"]]
       [:p.details "Based on the " [:a {:href "http://pacman.shaunew.com/"} "original ghost AI"] " from the Pac-Man arcade."]
       [:a {:href "http://github.com/shaunlebron/ld33-gh4st"}
        [:p.cljs "Made in" [:img {:src "img/cljs.svg"}] "ClojureScript"]]
       ]
      
      )))

(defcomponent root
  [data owner]
  (render [_this]
    (html
      (case (:screen data)
        :home (om/build welcome data)
        :game (om/build game data)
        nil)
      
      )))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

(defn on-js-reload []
  nil)


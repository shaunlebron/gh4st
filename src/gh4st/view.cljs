(ns gh4st.view
  (:require-macros
    [hiccups.core :as hiccups]
    [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [clojure.string :as string]
    [cljs.core.async :refer [<! timeout chan close! alts!]]
    [hiccups.runtime]
    [sablono.core :refer-macros [html]]
    [om-tools.core :refer-macros [defcomponent]]
    [om.core :as om]
    [gh4st.state :refer [app-state]]
    [gh4st.ui :refer [select-cell!]]
    [gh4st.img :refer [actor-order sprite-class]]
    [gh4st.game :refer [start-game!]]
    [gh4st.board :refer [decode-tile
                         board-size
                         ]]
    [gh4st.math :refer [add-pos]]
    [gh4st.texts :refer [victory-text
                         defeat-text
                         allow-defeat-text
                         ]]
    [gh4st.viz :refer [actor-target-viz
                       actor-path-viz]]
    ))

;;----------------------------------------------------------------------
;; Game screen
;;----------------------------------------------------------------------

(def cell-size 42)
(def cell-pad 3)
(def cell-grow 8)

(def sprite-size 66) ;; make sure to sync with spritesheet.css

(defn normalize-end
  [end]
  (if (= end :defeat-allowed)
    :defeat
    end))

(defn cell
  [data value [x y :as pos]]
  [:div
   {:class (cond-> (str "cell " (name (decode-tile value)))
             (= [x y] (:select-pos data)) (str " selected-cell")
             (:end data) (str " " (name (normalize-end (:end data))))
             (:select-actor data) (str " selected-" (name (:select-actor data))))

    ;; FIXME: re-enable for mouse editor control
    ;; :on-click #(select-cell! pos)

    :style {:width cell-size
            :height cell-size
            :margin-right cell-pad
            :margin-bottom cell-pad}

    }])

(defn actor
  [name- {:keys [dir pos anim?]}]
  (let [[x y] pos
        mult (+ cell-size cell-pad)
        offset (/ (- sprite-size cell-size) 2)
        px (- (* x mult) offset)
        py (- (* y mult) offset)
        transform (str
                    "translate(" px "px, " py "px) "
                    "scale(0.9)"
                    )
        classname (cond-> (sprite-class name- dir)
                    anim? (str "-anim"))
        style (cond-> {:transform transform}
                (nil? pos) (assoc :display "none"))]
    [:div {:class (str "spritesheet " classname)
           :style style}]))

(defcomponent game
  [data owner]
  (render [_this]
    (let [[cols rows] (board-size (:board data))
          scale (+ cell-size cell-pad)
          width (* cols scale)
          height (* rows scale)]
      (html
        [:div.game
         [:div.title {:style {:width width}}
          (-> data :level-text :title)]
         [:div.desc {:style {:width width}}
          (let [end (:end data)]
            (cond
              (= end :victory) victory-text
              (= end :defeat) defeat-text
              (= end :defeat-allowed) allow-defeat-text
              :else (-> @data :level-text :desc)))]

         [:div {:class (cond-> "board"
                         (:no-transitions? data) (str " no-transitions"))
                :style {:width width
                        :height height}}
          (for [[y row] (map-indexed vector (:board data))]
            [:div.row
             (for [[x value] (map-indexed vector row)]
               (cell data value [x y]))])

          ;; draw sprites
          (let [;; SVG ViewBox:
                ;;    Transforms [col,row] coords to the center pixels
                ;;    of the respective cells.
                ;;
                ;; .---------------
                ;; |dxd  ^     |  |
                ;; |     |     |  |
                ;; |<----O     |  |  A 1x1 Box
                ;; |           |  |
                ;; | (cell)    |  |  O = desired origin
                ;; |-----------/  |  dxd = desired offset size
                ;; | (pad)        |
                ;; |--------------/ 
                svg-props {:class "viz-layer"
                           :width width
                           :height height
                           :view-box 
                           (let [mult (+ cell-size cell-pad)
                                 d (- (/ cell-size mult 2))]
                             (string/join " " [d d cols rows]))}]

            (list
              ;; draw paths
              [:svg svg-props
               (for [name- (remove #{:fruit} actor-order)]
                 (when (get-in data [:actors name- :pos])
                   (actor-path-viz data name- 8)))]

              ;; draw sprites
              (for [name- actor-order]
                (actor name- (get-in data [:actors name-])))

              ;; draw targets
              [:svg svg-props
               (for [name- (remove #{:fruit} actor-order)]
                 (when (get-in data [:actors name- :pos])
                   (actor-target-viz data name-)))]))
          ]
         [:div.controls]]))))

;;----------------------------------------------------------------------
;; Welcome screen
;;----------------------------------------------------------------------

(def stop-welcome-anim nil)

(def next-home-actor
  {:blinky :pinky
   :pinky :inky
   :inky :clyde
   :clyde :blinky})

(defcomponent welcome
  [data owner]
  (will-mount [_this]
    (set! stop-welcome-anim (chan))
    (go-loop []
      (let [[v c] (alts! [(timeout 400) stop-welcome-anim])]
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
          [:div.letter "GH"]
          [:div {:class (str "ghost spritesheet "
                             (sprite-class name- [0 1]) "-anim")}]
          [:div.letter "ST"]
          ])
       [:p.instruct [:em "PRESS ENTER"]]
       [:p.author
        "by " [:a {:href "http://twitter.com/shaunlebron"} "@shaunlebron"]
        " on " [:a {:href "https://github.com/shaunlebron/gh4st"} "github"]]
       [:p.details "Based on the ghost AI of the original Pac-Man."]
       ]
      )))

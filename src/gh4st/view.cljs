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
    [gh4st.editor :as editor :refer [select-cell!]]
    [gh4st.img :refer [actor-order sprite-class]]
    [gh4st.game :as game :refer [start-game!
                                 load-custom-level!
                                 advance!
                                 ]]
    [gh4st.board :refer [decode-tile
                         board-size
                         ]]
    [gh4st.math :refer [add-pos]]
    [gh4st.texts :refer [victory-text
                         defeat-text
                         allow-defeat-text
                         texts
                         ]]
    [gh4st.viz :refer [actor-target-viz
                       actor-path-viz]]
    [gh4st.levels :refer [levels freeplay]]
    ))

(defn enable-keys [keyfuncs]
  (doseq [[k f] keyfuncs]
    (js/Mousetrap.bind k f)))

(defn disable-keys [keyfuncs]
  (doseq [[k f] keyfuncs]
    (js/Mousetrap.unbind k)))

;;----------------------------------------------------------------------
;; In-Game Settings
;;----------------------------------------------------------------------

(defcomponent settings-button
  [data owner {:keys [caption] :as opts}]
  (render [_this]
    (let [enabled (:enabled data)]
      (html
        [:div
         {:class (cond-> "settings-button" enabled (str " enabled"))
          :on-click #(om/transact! data :enabled not)}
         caption
         ])))
  )

(defcomponent settings
  [data owner]
  (render [_this]
    (html
      [:div.settings
       [:div.settings-title "insight"]
       (om/build settings-button (:paths data) {:opts {:caption "Paths"}})
       (om/build settings-button (:targets data) {:opts {:caption "Targets"}})
       ]
      )
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

(def escape-keys
  {"escape" #(swap! app-state assoc :screen :home)})

(defn game-board
  [state]
  (let [[cols rows] (board-size (:board state))
        scale (+ cell-size cell-pad)
        width (* cols scale)
        height (* rows scale)]
    [:div {:class (cond-> "board"
                    (:no-transitions? state) (str " no-transitions"))
           :style {:width width
                   :height height}}
     (for [[y row] (map-indexed vector (:board state))]
       [:div.row
        (for [[x value] (map-indexed vector row)]
          (cell state value [x y]))])

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
                        (string/join " " [d d cols rows]))}

           settings (:settings state)

           game-over? (:end state)

           get-svg-props
           (fn [type-]
             (let [visible? (and (not game-over?)
                                 (:enabled (get settings type-)))]
               (merge svg-props
                      {:style {:opacity (if visible? 1 0)}})))
           ]

       (list
         ;; draw paths
         [:svg (get-svg-props :paths)
          (for [name- (remove #{:fruit} actor-order)]
            (when (get-in state [:actors name- :pos])
              (actor-path-viz state name- 9)))]

         ;; draw sprites
         (for [name- actor-order]
           (actor name- (get-in state [:actors name-])))

         ;; draw targets
         [:svg (get-svg-props :targets)
          (for [name- (remove #{:fruit} actor-order)]
            (when (get-in state [:actors name- :pos])
              (actor-target-viz state name-)))]))
     ]))

(defcomponent game
  [data owner]
  (will-mount [_this]
    (enable-keys editor/key-functions)
    (enable-keys game/key-functions)
    (enable-keys escape-keys)
    )
  (will-unmount [_this]
    (disable-keys editor/key-functions)
    (disable-keys game/key-functions)
    (disable-keys escape-keys)
    )
  (render [_this]
    (let [[cols rows] (board-size (:board data))
          scale (+ cell-size cell-pad)
          width (* cols scale)
          height (* rows scale)]
      (html
        [:div.game
         (om/build settings (:settings data))
         [:div.title {:style {:width width}}
          (str (inc (:level data)) ". " (-> data :level-text :title))
          ]
         [:div.desc {:style {:width width}}
          (let [end (:end data)]
            (cond
              (= end :victory) victory-text
              (= end :defeat) defeat-text
              (= end :defeat-allowed) allow-defeat-text
              :else (-> @data :level-text :desc)))]
         (game-board data)
         
         [:div.controls]]))))

;;----------------------------------------------------------------------
;; Welcome screen
;;----------------------------------------------------------------------

(def welcome-keys
  {"enter" #(start-game!)}
  )

(def stop-welcome-anim nil)

(defcomponent welcome
  [data owner]
  (will-mount [_this]
    (swap! app-state assoc-in [:settings :paths :enabled] true)

    (load-custom-level! freeplay)

    (set! stop-welcome-anim (chan))

    ;; move actors
    (go-loop []
      (let [wait (rand-nth [1000 500 200])
            [v c] (alts! [(timeout wait) stop-welcome-anim])]
        (when-not (= c stop-welcome-anim)
          (if (:end @app-state)
            (do
              (<! (timeout 3000))
              (when (= :home (:screen @app-state))
                (load-custom-level! freeplay)))
            (advance! (rand-nth [:blinky :pinky :inky :clyde])))
          (recur))))

    ;; change viz settings periodically
    (go-loop []
2     (let [[v c] (alts! [(timeout 2000) stop-welcome-anim])]
        (when-not (= c stop-welcome-anim)
          (swap! app-state update-in [:settings :targets :enabled] not)
          (recur))))

    ;; fade in
    (go
      (<! (timeout 1000))
      (swap! app-state assoc :home-fadein true)))

  (will-unmount [_this]
    (close! stop-welcome-anim))

  (render [_this]
    (html
      [:div
       {:class (cond-> "home"
                 (:home-fadein data) (str " fadein"))}
       (game-board data)
       [:div.menu
        [:div.menu-button.main
         {:on-click #(start-game!)}
         "START"]
        [:div.menu-button
         {:on-click #(swap! app-state assoc :screen :level-select)}
         "Select Level"]
        [:div.menu-button "Editor"]]
       [:p.author
        "by " [:a {:href "http://twitter.com/shaunlebron"} "@shaunlebron"]
        " on " [:a {:href "https://github.com/shaunlebron/gh4st"} "github"]]
       [:div.details "Based on the ghost A.I. of the original Pac-Man."
          [:div {:class (str "mini-pacman spritesheet "
                             (sprite-class "pacman" [-1 0]) "-anim")}]
        ]
       ]
      )))

;;----------------------------------------------------------------------
;; Splash Screen
;;----------------------------------------------------------------------

(def next-splash-actor
  {:blinky :pinky
   :pinky :inky
   :inky :clyde
   :clyde :blinky})

(def stop-splash-bump nil)

(defcomponent splash
  [data owner]
  (will-mount
    [_this]

    (.play (js/document.getElementById "intro-song"))

    ;; bump animation
    (set! stop-splash-bump (chan))
    (go-loop []
      (let [[v c] (alts! [(timeout 400) stop-splash-bump])]
        (when-not (= c stop-splash-bump)
          (swap! app-state update-in [:splash-actor] next-splash-actor)
          (swap! app-state assoc :splash-bump true)
          (<! (timeout 20))
          (swap! app-state assoc :splash-bump false)
          (recur))))

    ;; fade out
    (go
      (swap! app-state assoc :splash-fadein true)
      (<! (timeout 4000))
      (swap! app-state dissoc :splash-fadein)
      (<! (timeout 1000))
      (swap! app-state assoc :screen :home))

    )
  (will-unmount
    [_this]
    (close! stop-splash-bump))
  (render
    [_this]
    (html
      [:div
       {:class (cond-> "splash"
                 (:splash-fadein data) (str " fadein"))}
       (let [name- (:splash-actor data)]
         [:h1 {:class (cond-> (str (name name-))
                        (:splash-bump data) (str " bump"))}
          "GH"
          [:div {:class (str "ghost spritesheet "
                             (sprite-class name- [0 1]) "-anim")}]
          "ST"
          ])])   
    )
  )

;;----------------------------------------------------------------------
;; Level Select
;;----------------------------------------------------------------------

(defcomponent level-select
  [data owner]
  (will-mount [_this]
    (enable-keys escape-keys)
    )
  (will-unmount [_this]
    (disable-keys escape-keys)
    )
  (render [_this]
    (html
      [:div.level-select
       [:div.title
        "Select Level"]
       [:ol
        (for [[i level text] (map vector (range) levels texts)]
          [:li
           {:on-click #(start-game! i)}
           (:title text)
           [:div.ghosts
            (let [actors (set (keys (:actors level)))]
              (for [a [:blinky :pinky :inky :clyde]]
                (when (actors a)
                  [:div
                   {:class (str "spritesheet sprite-" (name a) "-left")}
                   ])
                ))
            ]
           
           ]
          )]

       ])
    )
  )


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
         :actors {:pacman [0 1]
                  :blinky [0 2]
                  :end [3 5]
                  }
         }))

(defn on-js-reload []
  nil)

(defcomponent board
  [data owner]
  (render [_this]
    (html
      [:div.board
       (for [[y row] (map-indexed vector data)]
         [:div.row
          (for [[x cell] (map-indexed vector row)]
            [:div
             {:class (str "cell " (if (= :floor cell) "floor" "wall"))}
             ]
            )]
         )
       ]
      )
    )
  )

(defcomponent root
  [data owner]
  (render [_this]
    (html
      (om/build board (:board data))
      )))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

(ns gh4st.board
  (:require
    [gh4st.state :refer [app-state]])
  )

(defn empty-board
  "create initial empty board"
  [w h]
  (vec (repeat h (vec (repeat w :wall)))))

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

(ns gh4st.board
  (:require
    [gh4st.math :refer [add-pos]]
    )
  )

;; board size
(def rows 11)
(def cols 21) ;; NOTE: make sure this matches "$colls" in style.scss

(def toggle-tile
  {:O :_
   :_ :O})

(def decode-tile
  {:O :floor
   :_ :wall})

(def encode-tile
  {:floor :O
   :wall :_})

(defn empty-board
  "create initial empty board"
  ([] (empty-board cols rows))
  ([w h]
   (vec (repeat h (vec (repeat w (encode-tile :wall)))))))

(defn bound
  [x0 x x1]
  (cond
    (< x x0) x0
    (> x x1) x1
    :else x))

(defn board-size [board]
  (let [w (count (first board))
        h (count board)]
    [w h]))

(defn bound-pos
  [[x y] board]
  (let [[w h] (board-size board)]
    [(bound 0 x (dec w))
     (bound 0 y (dec h))]))

(defn can-walk?
  [pos dir board]
  (let [[x y] (add-pos pos dir)]
    (= (encode-tile :floor) (get-in board [y x]))))

(defn walkable-tiles
  [pos board]
  (->> [[0 -1] [1 0] [0 1] [-1 0]]
       (filter #(can-walk? pos % board))
       (map #(add-pos pos %))))

(defn ghost-positions
  [actors]
  (map #(get-in actors [% :pos]) [:blinky :pinky :inky :clyde]))

(defn next-ghost-positions
  [actors]
  (let [next-pos #(add-pos (:pos %) (:dir %))]
    (map #(next-pos (get actors %)) [:blinky :pinky :inky :clyde])))


(ns gh4st.math)

(defn scale-dir
  [[x y] s]
  [(* s x) (* s y)])

(defn add-pos
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn sub-pos
  [[x y] [x0 y0]]
  [(- x x0) (- y y0)])

(defn dist-sq
  [[x y] [x0 y0]]
  (let [dx (- x x0)
        dy (- y y0)]
    (+ (* dx dx) (* dy dy))))

(defn reflect-pos
  [[cx cy :as center] [x y :as pos]]
  (let [dx (- x cx)
        dy (- y cy)]
    [(- cx dx)
     (- cy dy)]))

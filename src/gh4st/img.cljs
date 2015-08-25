(ns gh4st.img
  (:require
    [gh4st.state :refer [app-state]]))

(def actor-order
  (reverse
    [:blinky
     :pinky
     :inky
     :clyde
     :pacman
     :fruit]))

(def fruits
  ["cherry"
   "strawberry"
   "orange"
   "apple"
   "melon"
   "pretzel"
   "pear"
   "banana"])

(defn level->fruit
  [level]
  (let [index (mod level (count fruits))]
    (fruits index)))

(def dir-name
  {[0 1]  "down"
   [0 -1] "up"
   [1 0]  "right"
   [-1 0] "left"})

(defn sprite-class
  [name- dir]
  (if (= name- :fruit)
    (str "sprite-" (level->fruit (:level @app-state)))
    (str "sprite-" (name name-) "-" (dir-name dir))))


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

(def dir->name
  {[0 1]  "-d"
   [0 -1] "-u"
   [1 0]  "-r"
   [-1 0] "-l"})

(defn img-src
  [name- dir]
  (if (= name- :fruit)
    (str "img/" (name name-) (js-mod (:level @app-state) 5) ".png")
    (str "img/" (name name-) (dir->name dir) ".png")))


(ns gh4st.levels)

(def level0
  {:allow-defeat false
   :actors
   {:pacman {:pos [7 2], :dir [1 0]},
    :blinky {:pos [12 2], :dir [-1 0]},
    :fruit {:pos [14 2]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level1
  {:allow-defeat true
   :actors
   {:pacman {:pos [9 1], :dir [-1 0]},
    :blinky {:pos [9 5], :dir [1 0]},
    :fruit {:pos [11 1]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]} )

(def level2
  {:allow-defeat true
   :actors
   {:pacman {:pos [10 6], :dir [0 -1]},
    :blinky {:pos [13 2], :dir [0 1]},
    :fruit {:pos [10 0]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level3
  {:actors
   {:pacman {:pos [10 0], :dir [0 1]},
    :blinky {:pos [8 6], :dir [1 0]},
    :fruit {:pos [10 4]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level4
  {:actors
   {:pacman {:pos [10 0], :dir [-1 0]},
    :blinky {:pos [12 2], :dir [-1 0]},
    :fruit {:pos [10 4]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level5
  {:allow-defeat true
   :actors
   {:pacman {:pos [7 2], :dir [1 0]},
    :blinky {:pos [11 4], :dir [-1 0]},
    :fruit {:pos [9 4]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level6
  {:allow-defeat true
   :actors
   {:pacman {:pos [11 1], :dir [1 0]},
    :blinky {:pos [8 1], :dir [1 0]},
    :pinky {:pos [13 3], :dir [0 -1]},
    :fruit {:pos [15 1]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level7
  {:allow-defeat true
   :actors
   {:pacman {:pos [9 1], :dir [1 0], :prev-pos [14 1]},
    :pinky {:pos [12 3], :dir [0 -1], :prev-pos [13 2]},
    :fruit {:pos [15 1]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :O :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level8
  {:allow-defeat false
   :actors
   {:pacman {:pos [11 1], :dir [1 0], :prev-pos [14 1]},
    :blinky {:pos [15 3], :dir [0 -1]},
    :pinky {:pos [10 4], :dir [-1 0], :prev-pos [13 2]},
    :fruit {:pos [15 1]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :O :O :O :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level9
  {:allow-defeat true
   :actors
   {:pacman {:pos [6 2], :dir [1 0], :prev-pos [14 1]},
    :clyde {:pos [12 2], :dir [-1 0]},
    :fruit {:pos [11 2]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]} )

(def level10
  {:allow-defeat false
   :actors
   {:pacman {:pos [3 2], :dir [1 0], :prev-pos [14 1]},
    :blinky {:pos [4 6], :dir [0 -1]},
    :pinky {:pos [17 4], :dir [-1 0], :prev-pos [13 2]},
    :inky {:pos [9 0], :dir [1 0]},
    :clyde {:pos [12 4], :dir [-1 0]},
    :fruit {:pos [15 1]}},
   :board
   [[:_ :O :O :O :_ :O :O :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :O :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :O :_ :O :_ :_ :_ :_ :_]
    [:O :O :O :O :O :O :O :O :O :_ :O :O :_ :O :O :O :_ :O :O :O :O]
    [:O :_ :_ :O :_ :_ :_ :_ :O :_ :_ :O :_ :O :_ :_ :_ :_ :_ :O :_]
    [:O :_ :O :O :O :_ :O :O :O :O :O :O :O :O :_ :O :O :O :O :O :_]
    [:O :_ :O :_ :O :_ :O :_ :_ :O :_ :_ :_ :O :_ :O :_ :_ :O :_ :_]
    [:O :O :O :_ :O :O :O :O :O :O :_ :_ :_ :O :O :O :O :O :O :_ :_]]})

(def levels
  [level0
   level1
   level2
   level3
   level4
   level5
   level6
   level7
   level8
   level9
   level10
   
   ])


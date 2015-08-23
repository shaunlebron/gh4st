(ns gh4st.levels)

(def level0
  {:allow-defeat false
   :actors
   {:pacman {:pos [7 2], :dir [1 0]},
    :blinky {:pos [12 2], :dir [-1 0]},
    :pinky {:pos nil, :dir [0 0]},
    :inky {:pos nil, :dir [0 0]},
    :clyde {:pos nil, :dir [0 0]},
    :fruit {:pos [14 2]}},
   :board
   [[:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :floor :floor :floor :floor :floor :floor :floor :floor :floor :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]]})

(def level1
 {
  :allow-defeat true
 :actors
 {:pacman {:pos [9 1], :dir [-1 0]},
  :blinky {:pos [9 5], :dir [1 0]},
  :pinky {:pos nil, :dir [0 0]},
  :inky {:pos nil, :dir [0 0]},
  :clyde {:pos nil, :dir [0 0]},
  :fruit {:pos [11 1]}},
 :board
 [[:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :floor
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :floor
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :floor
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :floor
   :floor
   :floor
   :floor
   :floor
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall]
  [:wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall 
   :wall]]} 
  )

(def level2
  {:allow-defeat true
   :actors
   {:pacman {:pos [10 6], :dir [0 -1]},
    :blinky {:pos [13 2], :dir [0 1]},
    :pinky {:pos nil, :dir [0 0]},
    :inky {:pos nil, :dir [0 0]},
    :clyde {:pos nil, :dir [0 0]},
    :fruit {:pos [10 0]}},
   :board
   [[:wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :floor
     :floor
     :floor
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall]
    [:wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :floor
     :wall
     :wall
     :wall
     :wall
     :floor
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall]
    [:wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :floor
     :wall
     :floor
     :wall
     :wall
     :floor
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall]
    [:wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :floor
     :wall
     :floor
     :wall
     :wall
     :floor
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall]
    [:wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :floor
     :floor
     :floor
     :wall
     :wall
     :floor
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall
     :wall]
[:wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :floor
 :wall
 :wall
 :floor
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall]
[:wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :wall
 :floor
 :wall 
 :wall 
 :wall 
 :wall 
 :wall 
 :wall 
 :wall 
 :wall 
 :wall 
 :wall]]})

(def levels
  [level0
   level1
   level2
   
   ])


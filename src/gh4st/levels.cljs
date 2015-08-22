(ns gh4st.levels)

(def level1
  {:board
   [[:wall :wall :wall :wall :wall :wall :wall]
    [:wall :floor :floor :floor :wall :wall :wall]
    [:wall :wall :wall :floor :wall :floor :floor]
    [:floor :wall :wall :floor :wall :floor :wall]
    [:floor :floor :floor :floor :floor :floor :wall]
    [:floor :wall :wall :wall :wall :wall :wall]],
   :select-pos [0 0],
   :select-actor nil,
   :level 0,
   :actors
   {:pacman {:pos [2 1], :dir [1 0]},
    :blinky {:pos [0 3], :dir [0 1]},
    :pinky {:pos nil, :dir [0 1]},
    :inky {:pos nil, :dir [0 1]},
    :clyde {:pos nil, :dir [0 1]}, 
    :fruit {:pos [6 2]}}})


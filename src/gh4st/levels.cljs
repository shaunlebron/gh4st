(ns gh4st.levels)

(def level1
  {:board
   [[:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :floor :floor :floor :floor :floor :floor :floor :floor :floor :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]
    [:wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall :wall]],
   :select-pos nil
   :select-actor nil
   :level 0,
   :actors
   {:pacman {:pos [7 4], :dir [1 0]},
    :blinky {:pos [13 4], :dir [-1 0]},
    :pinky {:pos nil, :dir [0 1]},
    :inky {:pos nil, :dir [0 1]},
    :clyde {:pos nil, :dir [0 1]}, 
    :fruit {:pos [14 4]}}})



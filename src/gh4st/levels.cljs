(ns gh4st.levels)

(def level0
  {:actors
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

(def levels
  [level0
   
   ])


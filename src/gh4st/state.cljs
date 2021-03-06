(ns gh4st.state
  (:require
    [gh4st.board :refer [empty-board]]))

(def empty-state
  {:screen :splash

   :splash-actor :blinky

   :level-text nil

   ;; editor
   :select-pos nil,
   :select-actor nil,

   :settings
   {:paths {:enabled false
            :actors #{}}
    :targets {:enabled false
               :actors #{}}
    }

   ;; game
   :board (empty-board), 
   :level 0,
   :actors
   {:pacman {:pos [2 1], :dir [1 0]},
    :blinky {:pos [0 3], :dir [0 1]},
    :pinky {:pos nil, :dir [0 1]},
    :inky {:pos nil, :dir [0 1]},
    :clyde {:pos nil, :dir [0 1]}, 
    :fruit {:pos [6 2]}}})

(defonce app-state
  (atom empty-state))

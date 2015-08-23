(ns gh4st.state
  (:require
    [gh4st.board :refer [empty-board]]))

(def empty-state
  {:screen :home ;; :home or :game

   :home-actor :blinky

   :level-text nil

   ;; editor
   :select-pos nil,
   :select-actor nil,

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

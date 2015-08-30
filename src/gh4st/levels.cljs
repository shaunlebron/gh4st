(ns gh4st.levels
  (:require
    [gh4st.board :refer [board-size floor?]]
    [gh4st.math :refer [add-pos sub-pos]]
    )
  )

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
  {:allow-defeat true
   :actors
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
  {:allow-defeat false
   :actors
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
  {:allow-defeat false
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
   {:pacman {:pos [12 1], :dir [-1 0], :prev-pos [14 1]},
    :pinky {:pos [9 3], :dir [0 -1], :prev-pos [13 2]},
    :fruit {:pos [6 1]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
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
   {:pacman {:pos [12 0], :dir [-1 0], :prev-pos [14 1]},
    :clyde {:pos [12 5], :dir [-1 0]},
    :fruit {:pos [8 5]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]} )

(def level10
  {:allow-defeat true,
   :actors
   {:pacman {:pos [15 3], :dir [-1 0], :prev-pos nil, :anim? false},
    :clyde {:pos [10 1], :dir [0 1], :prev-pos nil, :anim? false},
    :fruit {:pos [7 2], :prev-pos nil}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :O :O :O :O :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :O :O :O :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :_ :_ :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :O :O :O :O :_ :_ :_ :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level11
  {:allow-defeat false
   :actors
   {:pacman {:pos [5 2], :dir [1 0], :prev-pos nil, :anim? false},
    :clyde {:pos [13 4], :dir [-1 0], :prev-pos nil, :anim? false},
    :fruit {:pos [12 2], :prev-pos nil},
    :blinky {:prev-pos nil, :dir [0 -1], :pos [8 5]}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :O :O :O :O :O :_ :O :O :O :_ :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :O :_ :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :O :O :O :_ :_ :_ :O :_ :_ :_ :_ :_ :_ :_ :_]]})

(def level12
  {:allow-defeat false
   :actors
   {:pacman {:pos [17 2], :dir [0 1], :prev-pos nil, :anim? false},
    :clyde {:pos [15 5], :dir [0 1], :prev-pos nil, :anim? false},
    :fruit {:pos [5 2], :prev-pos nil},
    :pinky {:prev-pos nil, :dir [0 1], :pos [10 2], :anim? false}},
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :_ :_ :O :_ :_ :_ :_ :O :_ :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :_ :O :O :O :O :_ :_ :_ :_ :O :_ :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :O :O :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :O :_ :O :O :O :O :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]
   })

(def level13
  {:allow-defeat false
   :actors
   {:pacman {:pos [7 3], :dir [1 0], :prev-pos nil, :anim? false},
    :inky {:pos [16 4], :dir [-1 0], :prev-pos nil, :anim? false},
    :fruit {:pos [16 2], :prev-pos nil},
    :blinky {:prev-pos [6 1], :dir [1 0], :pos [7 1], :anim? false},
    :clyde {:prev-pos nil, :pos nil}}
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :_ :_ :_ :O :_ :_ :_ :O :O :O :O :O :O :_ :_]
    [:_ :_ :_ :_ :_ :O :O :O :O :O :_ :_ :_ :O :_ :_ :_ :_ :O :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :O :O :O :O :O :O :O :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :O :O :O :O :O :O :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]
    [:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]
   }
  )

(def level14
  {:allow-defeat false
   :actors
   {:pacman {:pos [3 2], :dir [1 0], :prev-pos [14 1]},
    :blinky {:pos [10 2], :dir [1 0], :prev-pos nil},
    :pinky {:pos [5 0], :dir [0 1], :prev-pos nil},
    :inky {:pos [11 0], :dir [-1 0], :prev-pos nil},
    :clyde {:pos [17 4], :dir [-1 0], :prev-pos nil},
    :fruit {:pos [15 2], :prev-pos nil}},
   :board
   [[:_ :_ :_ :_ :_ :O :O :O :O :O :O :O :O :O :O :O :O :O :_ :_ :_]
    [:_ :_ :_ :_ :_ :O :_ :_ :O :_ :_ :_ :_ :O :_ :O :_ :O :_ :_ :_]
    [:_ :O :O :O :O :O :_ :_ :O :O :O :O :_ :O :_ :O :O :O :O :O :_]
    [:_ :O :_ :O :_ :O :_ :_ :O :_ :_ :O :_ :O :_ :_ :_ :O :_ :O :_]
    [:_ :O :O :O :O :O :O :O :O :O :O :O :O :O :_ :O :O :O :O :O :_]
    [:_ :_ :_ :_ :O :_ :O :_ :_ :O :_ :_ :_ :O :_ :O :_ :_ :O :_ :_]
    [:_ :_ :_ :_ :O :O :O :_ :_ :O :O :O :O :O :O :O :O :O :O :_ :_]]})

(def level-end
  {:actors nil
   :board
   [[:_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_ :_]]})

(def attract
  {:allow-defeat false
   :actors
   {:pacman {:pos [3 2], :dir [1 0], :prev-pos [14 1]},
    :blinky {:pos [4 6], :dir [0 -1]},
    :pinky {:pos [13 4], :dir [0 -1], :prev-pos [13 2]},
    :inky {:pos [9 0], :dir [-1 0]},
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

(defn shuffle-level-actors
  [level]
  (let [{:keys [board actors]} level
        [w h] (board-size board)
        opens (set (for [y (range h) x (range w)
                         :when (= :O (get-in board [y x]))]
                     [x y]))
        dirs [[0 -1][-1 0][0 1][1 0]]]
    (loop [level level
           opens opens
           actors actors]
      (if-let [[name- actor] (first actors)]
        (let [new-pos (rand-nth (vec opens))
              adjacents (set (map #(add-pos new-pos %) dirs))
              look-pos (rand-nth (filterv #(floor? % board) adjacents))
              next-dir (sub-pos look-pos new-pos)
              new-actor (cond-> (assoc actor :pos new-pos)
                          (not= :fruit name-) (assoc :dir next-dir))]
          (recur
            (assoc-in level [:actors name-] new-actor)
            (apply disj opens new-pos adjacents)
            (next actors)))
        level))))

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
   level11
   level12
   level13
   level14

   level-end
   
   ])


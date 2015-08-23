(ns gh4st.ai)

;;-----------------------------------------------------------------------
;; AI controlled by these two methods
;;-----------------------------------------------------------------------

(defmulti next-actor
  "Determines the next state for the given actor."
  (fn [name- state]
    name-))

(defmulti target-to-chase
  "Determines target tile for the given actor and state of the board."
  (fn [name- state]
    name-))

(defmethod next-actor :default
  [name- state]
  (let [target (target-to-chase name- state)
        {:keys [pos dir] :as actor} (-> state :actors name-)
        ]

    ;; determine new pos by stepping toward current dir
    ;; choose closest cell to target
    ;;   taking into account ability to change direction (i.e. ghost's can only do this at intersections)
    ;; determine new dir by facing closest valid cell

    actor))

;;-----------------------------------------------------------------------
;; Targetting Logic (based on original game)
;;-----------------------------------------------------------------------

(defmethod target-to-chase :pacman
  [_name state]
  (-> state :actors :fruit :pos))

(defmethod target-to-chase :blinky
  [_name state]
  (-> state :actors :pacman :pos))

(defmethod target-to-chase :pinky
  [_name state]
  (let [pacman (-> state :actors :pacman)
        [x y] (:pos pacman)
        [dx dy] (:dir pacman)
        target [(+ x (* 2 dx))
                (+ y (* 2 dy))]]
    target))

(defmethod target-to-chase :inky
  [_name state]
  (let [[bx by] (-> state :actors :blinky :pos)
        pacman (-> state :actors :pacman)
        [x y] (:pos pacman)
        [dx dy] (:dir pacman)

        ;; point in front of pacman
        tx (+ x dx)
        ty (+ y dy)

        ;; reflect blinky's position across that point
        dx (- bx tx)
        dy (- by ty)
        target [(- tx dx) (- ty dy)]]
    target))

(defmethod target-to-chase :clyde
  [_name state]
  (let [[cx cy] (-> state :actors :clyde :pos)
        [px py] (-> state :actors :pacman :pos)
        radius 2
        dx (- px cx)
        dy (- py cy)
        distsq (+ (* dx dx) (* dy dy))
        too-close? (<= distsq (* radius radius))

        target (if too-close?
                 [(- cx dx) (- cy dy)] ;; run away
                 [px py] ;; chase
                 )
        ]
    target))

(ns gh4st.ai
  (:require
    [clojure.set :refer [union]]
    [gh4st.math :refer [add-pos
                        sub-pos
                        scale-dir
                        reflect-pos
                        dist-sq]]
    [gh4st.board :refer [floor?
                         ghost-positions
                         next-ghost-positions]]))

;;-----------------------------------------------------------------------
;; AI dispatch
;;-----------------------------------------------------------------------

(def actor-hierarchy
  "identify the ghosts"
  (-> (make-hierarchy)
      (derive :blinky :ghost)
      (derive :pinky :ghost)
      (derive :inky :ghost)
      (derive :clyde :ghost)))

(defmulti next-actor-pos
  "Determines the next position for the given actor."
  (fn [state name-] name-)
  :hierarchy #'actor-hierarchy)

(defmulti next-actor-dir
  "Determines the next direction for the given actor."
  (fn [state name-] name-)
  :hierarchy #'actor-hierarchy)

(defmulti actor-target
  "Determines target tile for the given actor."
  (fn [state name-] name-)
  :hierarchy #'actor-hierarchy)

(defmulti tick-actor
  "Calculate the next game state after running the given actor's AI."
  (fn [state name-] name-)
  :hierarchy #'actor-hierarchy)

;;-----------------------------------------------------------------------
;; Moving/Steering state functions
;;-----------------------------------------------------------------------

(defn steer-actor
  "Steer the given actor in the given game state."
  [state name-]
  (assoc-in state [:actors name- :dir]
    (next-actor-dir state name-)))

(defn move-actor
  "Move the given actor in the game."
  [state name-]
  (let [prev-pos (get-in state [:actors name- :pos])
        next-pos (next-actor-pos state name-)]
    (-> state
        (assoc-in [:actors name- :pos] next-pos)
        (assoc-in [:actors name- :prev-pos] prev-pos))))

;;-----------------------------------------------------------------------
;; AI tick functions
;;-----------------------------------------------------------------------

;;; SUBTLETY NOTE:
;;;
;;; Ghosts and Pacman differ by the order at which they STEER and MOVE on each
;;; turn. This is very important for game balancing since it slows down Ghosts
;;; reaction time, while quickening Pacman:
;;;
;;;                            (quick reaction)
;;;                                v-----v
;;; PACMAN ->                   [STEER, MOVE]
;;;
;;;                 t=0              t=1               t=2
;;;             -----|----------------|-----------------|--------------> TIME
;;;
;;;  GHOST ->  [MOVE, STEER]                       [MOVE, STEER]
;;;                     ^----------------------------^
;;;                            (slow reaction)

;;; RELATION TO ORIGINAL GAME AI:
;;; 
;;; In the original game, the Ghost's eyes are always broadcasting its next
;;; move for a *full tile* before every turn, giving the player some time to
;;; react.  Pacman had no such restriction for committing to turns.

(defmethod tick-actor :ghost
  [state name-]
  (-> state
      (move-actor name-)
      (steer-actor name-)))

(defmethod tick-actor :pacman
  [state name-]
  (-> state
      (steer-actor name-)
      (move-actor name-)))

;;-----------------------------------------------------------------------
;; Determining next position and direction
;;-----------------------------------------------------------------------

(def priority-directions
  "counter-clockwise directions used when disputing path distance.
  lower index = higher priority"
  [[ 0 -1] ;; up
   [-1  0] ;; left
   [ 0  1]  ;; down
   [ 1  0]  ;; right
   ])

(defn next-actor-dir*
  "Common logic for determining next direction."
  [state name- & {:keys [off-limits?] :or {off-limits? #{}}}]
  (let [;; get actor data
        {:keys [pos dir prev-pos]} (-> state :actors name-)
        prev-pos (or prev-pos (sub-pos pos dir))

        ;; get adjacent tiles
        adjacents (->> priority-directions
                       (map #(add-pos pos %))
                       (filter #(floor? % (:board state))))

        ;; get available openings
        openings (remove off-limits? adjacents)

        ;; avoid turning back if possible
        openings (or (seq (remove #{prev-pos} openings))
                     openings)

        ;; choose the opening closest to the target
        target (:pos (actor-target state name-))
        closest (apply min-key #(dist-sq % target)
                  (reverse openings)) ;; reversing so it chooses the first min

        ;; stay the course unless a valid tile found
        next-dir (if closest
                   (sub-pos closest pos)
                   dir)]
    next-dir))

(defn next-actor-pos*
  "Common logic for determining next position."
  [state name- & {:keys [off-limits?] :or {off-limits? #{}}}]
  (let [;; get actor data
        {:keys [pos dir]} (-> state :actors name-)
        next-pos (add-pos pos dir)

        ;; decide if next position is valid
        valid? (and (floor? next-pos (:board state))
                    (not (off-limits? next-pos)))

        ;; stay put unless valid tile found
        next-pos (if valid? next-pos pos)]
    next-pos))

(defmethod next-actor-dir :ghost
  [state name-]
  (next-actor-dir* state name-))

(defmethod next-actor-pos :ghost
  [state name-]
  (next-actor-pos* state name-))

(defmethod next-actor-dir :pacman
  [state name-]
  ;;; pacman needs to steer away from ghosts and potential traps
  (let [ghost-pos? (set (ghost-positions (:actors state)))
        trap? (-> (set (next-ghost-positions (:actors state)))
                  (disj (get-in state [:actors :fruit :pos])))]
    (next-actor-dir* state name-
      :off-limits? (union ghost-pos? trap?))))

(defmethod next-actor-pos :pacman
  [state name-]
  ;;; pacman should never walk onto a ghost
  (let [ghost-pos? (set (ghost-positions (:actors state)))]
    (next-actor-pos* state name-
      :off-limits? ghost-pos?)))

;;-----------------------------------------------------------------------
;; Targetting Viz
;;-----------------------------------------------------------------------

(defn target-point
  [[x y]  name-]
  [:circle
   {:class (str "target target-" (name name-))
    :cx x :cy y}])

(defn guide-line
  [[x1 y1] [x2 y2]]
  [:line.guide-line {:x1 x1 :y1 y1 :x2 x2 :y2 y2}])

;;-----------------------------------------------------------------------
;; Targetting
;;-----------------------------------------------------------------------

(defmethod actor-target :pacman
  ;;; Target the fruit.
  [state _name]
  (let [target (-> state :actors :fruit :pos)]
    {:pos target
     :viz nil}))

(defmethod actor-target :blinky
  ;;; Target pacman.
  [state _name]
  (let [target (-> state :actors :pacman :pos)]
    {:pos target
     :viz nil}))

(defmethod actor-target :pinky
  ;;; Try to get ahead of pacman.
  [state name-]
  (let [pacman (-> state :actors :pacman)
        target (add-pos (:pos pacman)
                        (scale-dir (:dir pacman) 2))]
    {:pos target
     :viz [:g
           (guide-line (:pos pacman) target)
           (target-point target name-)]}))

(defmethod actor-target :inky
  ;;; Try to flank pacman from side opposite to blinky.
  [state name-]
  (let [blinky (-> state :actors :blinky)
        pacman (-> state :actors :pacman)
        nose (add-pos (:pos pacman) (:dir pacman))
        [nx ny] nose
        target (reflect-pos nose (:pos blinky))]
    {:pos target
     :viz [:g
           (guide-line (:pos pacman) nose)
           (guide-line (:pos blinky) target)
           [:circle.hinge {:cx nx :cy ny}]
           (target-point target name-)]}))

(defmethod actor-target :clyde
  ;;; Target pacman, but flee if he gets too close.
  [state _name]
  (let [pos (-> state :actors :clyde :pos)
        pacpos (-> state :actors :pacman :pos)
        [cx cy] pacpos
        radius 2
        too-close? (<= (dist-sq pos pacpos) (* radius radius))
        target (if too-close?
                 (reflect-pos pos pacpos)
                 pacpos)]
    {:pos target
     :viz [:circle
           {:class (cond-> "clyde-boundary" too-close? (str " inside"))
            :cx cx :cy cy :r radius}]}))

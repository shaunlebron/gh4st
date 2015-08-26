(ns gh4st.ai
  (:require
    [clojure.set :refer [union]]
    [gh4st.math :refer [add-pos
                        sub-pos
                        scale-dir
                        reflect-pos
                        dist-sq]]
    [gh4st.board :refer [floor?
                         adjacent-tiles
                         ghost-positions
                         next-ghost-positions]]
    [gh4st.state :refer [app-state]]))

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

(defmulti target-to-chase
  "Determines target tile for the given actor."
  (fn [state name-] name-)
  :hierarchy #'actor-hierarchy)

(defmulti tick-actor
  "Run one tick of the actor's AI for the given game state, returning a new state."
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

(defmethod tick-actor :ghost
  [state name-]
  ;; ghosts move to their last committed direction,
  ;; then determine next direction.
  (-> state
      (move-actor name-)
      (steer-actor name-)))

(defmethod tick-actor :pacman
  [state name-]
  ;; pacman determines next direction, then moves there immediately.
  (-> state
      (steer-actor name-)
      (move-actor name-)))

;;-----------------------------------------------------------------------
;; Determining next position and direction
;;-----------------------------------------------------------------------

(defn next-actor-dir*
  "Common logic for determining next direction."
  [state name- & {:keys [off-limits?] :or {off-limits? #{}}}]
  (let [;; get actor data
        {:keys [pos dir prev-pos]} (-> state :actors name-)
        prev-pos (or prev-pos (sub-pos pos dir))

        ;; get available openings
        openings (->> (adjacent-tiles pos (:board state))
                      (remove off-limits?))

        ;; avoid turning back if possible
        openings (or (seq (remove #{prev-pos} openings))
                     openings)

        ;; choose the opening closest to the target
        target (target-to-chase state name-)
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
;; Targetting
;;-----------------------------------------------------------------------

(defmethod target-to-chase :pacman
  [state _name]
  (-> state :actors :fruit :pos))

(defmethod target-to-chase :blinky
  [state _name]
  (-> state :actors :pacman :pos))

(defmethod target-to-chase :pinky
  [state _name]
  (let [pacman (-> state :actors :pacman)
        target (add-pos (:pos pacman)
                        (scale-dir (:dir pacman) 2))]
    target))

(defmethod target-to-chase :inky
  [state _name]
  (let [blinky (-> state :actors :blinky)
        pacman (-> state :actors :pacman)
        nose (add-pos (:pos pacman) (:dir pacman))
        target (reflect-pos nose (:pos blinky))]
    target))

(defmethod target-to-chase :clyde
  [state _name]
  (let [pos (-> state :actors :clyde :pos)
        pacpos (-> state :actors :pacman :pos)
        r 2
        too-close? (<= (dist-sq pos pacpos) (* r r))
        target (if too-close?
                 (reflect-pos pos pacpos)
                 pacpos)]
    target))

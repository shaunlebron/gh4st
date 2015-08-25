(ns gh4st.ai
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    )
  (:require
    [cljs.core.async :refer [timeout <!]]
    [clojure.set :refer [union]]
    [gh4st.math :refer [add-pos
                        sub-pos
                        scale-dir
                        reflect-pos
                        dist-sq
                        ]]
    [gh4st.board :refer [floor?
                         adjacent-tiles
                         ghost-positions
                         next-ghost-positions
                         ]]
    [gh4st.state :refer [app-state]]
    )
  )

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
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

(defmulti next-actor-dir
  "Determines the next direction for the given actor."
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

(defmulti target-to-chase
  "Determines target tile for the given actor and state of the board."
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

(defmulti tick-actor!
  "Run one tick of the actor's AI."
  identity
  :hierarchy #'actor-hierarchy)

;;-----------------------------------------------------------------------
;; Moving/Steering state functions
;;-----------------------------------------------------------------------

(defn steer-actor!
  "Common function for steering the given actor in the game."
  [name-]
  (swap! app-state #(assoc-in % [:actors name- :dir] (next-actor-dir name- %))))

(defn move-actor*
  "Common function for moving the given actor in the game."
  [name-]

  ;; move to next position, remembering the previous.
  (let [pos (get-in @app-state [:actors name- :pos])]
    (swap! app-state #(assoc-in % [:actors name- :pos] (next-actor-pos name- %)))
    (swap! app-state #(assoc-in % [:actors name- :prev-pos] pos)))

  ;; animate them for a short time to show motion.
  (go
    (let [set-anim! (fn [on] (swap! app-state #(assoc-in % [:actors name- :anim?] on)))]
      (set-anim! true)
      (<! (timeout 300))
      (set-anim! false))))

(defmethod tick-actor! :ghost
  [name-]
  ;; ghosts move to their last committed direction,
  ;; then determine next direction.
  (move-actor* name-)
  (steer-actor! name-))

(defmethod tick-actor! :pacman
  [name-]
  ;; pacman determines next direction, then moves there immediately.
  (steer-actor! name-)
  (move-actor* name-))

;;-----------------------------------------------------------------------
;; Determining next position and direction
;;-----------------------------------------------------------------------

(defn next-actor-dir*
  "Common logic for determining next direction."
  [name- state & {:keys [off-limits?] :or {off-limits? #{}}}]
  (let [;; get actor data
        {:keys [pos dir prev-pos]} (-> state :actors name-)
        prev-pos (or prev-pos (sub-pos pos dir))

        ;; get available openings
        openings (->> (adjacent-tiles pos (:board state))
                      (remove off-limits?))

        ;; make turning back the last resort
        openings (cond->> openings
                   (> (count openings) 1) (remove #{prev-pos}))

        ;; choose the opening closest to the target
        target (target-to-chase name- state)
        closest (apply min-key #(dist-sq % target)
                  (reverse openings)) ;; reversing so it chooses the first min

        ;; stay the course unless a valid tile found
        next-dir (if closest
                   (sub-pos closest pos)
                   dir)]
    next-dir))

(defn next-actor-pos*
  "Common logic for determining next position."
  [name- state & {:keys [off-limits?] :or {off-limits? #{}}}]
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
  [name- state]
  (next-actor-dir* name- state))

(defmethod next-actor-pos :ghost
  [name- state]
  (next-actor-pos* name- state))

(defmethod next-actor-dir :pacman
  [name- state]
  ;;; pacman needs to steer away from ghosts and potential traps
  (let [ghost-pos? (set (ghost-positions (:actors state)))
        trap? (-> (set (next-ghost-positions (:actors state)))
                  (disj (get-in state [:actors :fruit :pos])))]
    (next-actor-dir* name- state
      :off-limits? (union ghost-pos? trap?))))

(defmethod next-actor-pos :pacman
  [name- state]
  ;;; pacman should never walk onto a ghost
  (let [ghost-pos? (set (ghost-positions (:actors state)))]
    (next-actor-pos* name- state
      :off-limits? ghost-pos?)))

;;-----------------------------------------------------------------------
;; Targetting
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
        target (add-pos (:pos pacman)
                        (scale-dir (:dir pacman) 2))]
    target))

(defmethod target-to-chase :inky
  [_name state]
  (let [blinky (-> state :actors :blinky)
        pacman (-> state :actors :pacman)
        nose (add-pos (:pos pacman) (:dir pacman))
        target (reflect-pos nose (:pos blinky))]
    target))

(defmethod target-to-chase :clyde
  [_name state]
  (let [pos (-> state :actors :clyde :pos)
        pacpos (-> state :actors :pacman :pos)
        r 2
        too-close? (<= (dist-sq pos pacpos) (* r r))
        target (if too-close?
                 (reflect-pos pos pacpos)
                 pacpos)]
    target))

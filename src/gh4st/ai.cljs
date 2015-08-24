(ns gh4st.ai
  (:require
    [gh4st.math :refer [add-pos
                        sub-pos
                        scale-dir
                        reflect-pos
                        dist-sq
                        ]]
    [gh4st.board :refer [can-walk?
                         walkable-tiles
                         ghost-positions
                         ]]
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

(defmulti move-actor
  "Determines the next position for the given actor."
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

(defmulti steer-actor
  "Determines the next direction for the given actor."
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

(defmulti target-to-chase
  "Determines target tile for the given actor and state of the board."
  (fn [name- state] name-)
  :hierarchy #'actor-hierarchy)

;;-----------------------------------------------------------------------
;; Moving/Steering
;;-----------------------------------------------------------------------

(def tile-type
  "# of open adjacent tiles -> type"
  {0 :dead-end
   1 :dead-end
   2 :tunnel
   3 :intersection
   4 :intersection})

(defmethod steer-actor :default
  [name- state]
  (let [target (target-to-chase name- state)
        {:keys [pos dir prev-pos]} (-> state :actors name-)
        prev-pos (or prev-pos (sub-pos pos dir))
        opens (walkable-tiles pos (:board state))
        choices (if (= :dead-end (tile-type (count opens)))
                  (take 1 opens)
                  (remove #(= prev-pos %) opens)) ;; can't turn back
        closest (apply min-key #(dist-sq % target) (reverse choices))
        next-dir (sub-pos closest pos)]
    next-dir))

(defmethod steer-actor :pacman
  [name- state]
  (let [target (target-to-chase name- state)
        {:keys [pos dir prev-pos]} (-> state :actors name-)
        prev-pos (or prev-pos (sub-pos pos dir))
        ghost-pos? (set (ghost-positions (:actors state)))
        opens (->> (walkable-tiles pos (:board state))
                   (remove ghost-pos?))
        _ (println (pr-str opens))
        choices (if (= :dead-end (tile-type (count opens)))
                  (take 1 opens)
                  (remove #(= prev-pos %) opens)) ;; can't turn back
        closest (apply min-key #(dist-sq % target) (reverse choices))
        next-dir (if closest
                   (sub-pos closest pos)
                   dir)]
    next-dir))

(defmethod move-actor :default
  [name- state]
  (let [{:keys [pos dir] :as actor} (-> state :actors name-)
        next-pos (if (can-walk? pos dir (:board state))
                   (add-pos pos dir)
                   pos)]
    next-pos))

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
                        (scale-dir (:dir pacman) 3))]
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

(ns gh4st.history
  (:require
    [gh4st.state :refer [app-state]]))

(declare cherry-pick)
(declare cherry-merge)

;;; This undo/redo model assumes that the user will call `commit!` right before
;;; entering a new state.

;; Below is our history stack. Each slot represents the state at some point in
;; time.  The current index *always* represents the current time.
;;              
;;   t=0   t=1   t=2   t=3   t=4   t=5
;; |-----|-----|-----|-----|-----|-----|-----
;; |     |     |     |     |     |     | ...
;; |-----|-----|-----|-----|-----|-----|-----
;;                      ^
;;         UNDO <----- NOW -----> REDO
;;
;; UNDO and REDO performs:
;;   - stores current state at NOW index if empty
;;   - move the NOW index backward or forward if possible
;;   - copies the state at the new NOW index to the app state
;;
;; COMMIT performs:
;;   - remove all future values since we're starting a new one.
;;   - store current state at the NOW index
;;   - move NOW index forward

(defonce history-index
  (atom 0))

(defonce history-stack
  (atom []))

(defn undo! [curr-state]
  (when (pos? @history-index)

    ;; Allow us to redo what we're undoing if this moment isn't remembered yet.
    (when-not (get @history-stack @history-index)
      (swap! history-stack conj (cherry-pick curr-state)))

    (swap! history-index dec)
    (swap! app-state cherry-merge (get @history-stack @history-index))))

(defn redo! []
  (when-let [state (get @history-stack (inc @history-index))]
    (swap! app-state cherry-merge state)
    (swap! history-index inc)))

(defn commit!
  "Call this right before entering a new state."
  [curr-state]
  (swap! history-stack subvec 0 @history-index)
  (swap! history-stack conj (cherry-pick curr-state))
  (swap! history-index inc))

(defn forget-all! []
  (reset! history-index 0)
  (swap! history-stack empty))

;;----------------------------------------------------------------------
;; Cherry picking and merging
;; (i.e. only remembering and restoring state that we want)
;;----------------------------------------------------------------------

(defn remove-actor-anim
  "Remove actors animation state (shouldn't be remembered)"
  [actors]
  (reduce 
    (fn [result name-]
      (let [path [name- :anim?]]
        (cond-> result
          (get-in result path) (assoc-in path false))))
    actors
    [:blinky :pinky :inky :clyde :pacman]))

(defn cherry-pick
  "Only pick the parts of the state we want to remember."
  [curr-state]
  (-> curr-state
      (select-keys [:board :actors :end])
      (update-in [:actors] remove-actor-anim)))

(defn cherry-merge
  "Restore the given cherry-picked state onto the app-state."
  [curr-state cherry-state]
  (merge curr-state cherry-state))

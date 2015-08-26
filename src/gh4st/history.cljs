(ns gh4st.history
  (:require
    [gh4st.state :refer [app-state]]))

;;; This is our history stack. Each slot represents the state at some point in
;;; time.  The current index *always* represents the current time.
;;;              
;;;   t=0   t=1   t=2   t=3   t=4   t=5
;;; |-----|-----|-----|-----|-----|-----|-----
;;; |     |     |     |     |     |     | ...
;;; |-----|-----|-----|-----|-----|-----|-----
;;;                      ^
;;;         UNDO <----- NOW -----> REDO
;;;
;;; UNDO and REDO performs:
;;;   - stores current state at NOW index if empty
;;;   - move the NOW index backward or forward if possible
;;;   - copies the state at the new NOW index to the app state
;;;
;;; COMMIT performs:
;;;   - remove all future values since we're starting a new one.
;;;   - store current state at the NOW index
;;;   - move NOW index forward

(defonce history-index
  (atom 0))

(defonce history-stack
  (atom []))

(defn undo! [curr-state]
  (when (pos? @history-index)

    ;; Allow us to redo what we're undoing if this moment isn't remembered yet.
    (when-not (get @history-stack @history-index)
      (swap! history-stack conj curr-state))

    (swap! history-index dec)
    (reset! app-state (get @history-stack @history-index))))

(defn redo! []
  (when-let [state (get @history-stack (inc @history-index))]
    (reset! app-state state)
    (swap! history-index inc)))

(defn commit! [curr-state]
  (swap! history-stack subvec 0 @history-index)
  (swap! history-stack conj curr-state)
  (swap! history-index inc))

(defn forget-all! []
  (reset! history-index 0)
  (swap! history-stack empty))

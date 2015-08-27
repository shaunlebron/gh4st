(ns gh4st.viz
  (:require
    [gh4st.ai :refer [actor-target]]
    )
  )

(defn target-point
  [[x y]  name-]
  [:circle
   {:class (str "target target-" (name name-))
    :cx x :cy y :r 0.1}])

(defn guide-line
  [[x1 y1] [x2 y2]]
  [:line.guide-line {:x1 x1 :y1 y1 :x2 x2 :y2 y2}])

(defmulti actor-target-viz
  (fn [state name-] name-))

(defmethod actor-target-viz :default
  [state name-]
  )

(defmethod actor-target-viz :pacman
  [state name-]
  nil)

(defmethod actor-target-viz :blinky
  [state name-]
  nil)

(defmethod actor-target-viz :pinky
  [state name-]
  (let [{:keys [pos viz-data]} (actor-target state name-)]
    [:g
     (guide-line (:pacman-pos viz-data) pos)
     (target-point pos name-)]))

(defmethod actor-target-viz :inky
  [state name-]
  (let [{:keys [pos viz-data]} (actor-target state name-)]
    [:g
     (guide-line (:pacman-pos viz-data) (:nose viz-data))
     (guide-line (:blinky-pos viz-data) pos)
     (let [[nx ny] (:nose viz-data)]
       [:circle.hinge {:cx nx :cy ny :r 0.075}])
     (target-point pos name-)]))

(defmethod actor-target-viz :clyde
  [state name-]
  (let [{:keys [pos viz-data]} (actor-target state name-)
        [cx cy] (:pacman-pos viz-data)
        ]
    [:circle
     {:class (cond-> "clyde-boundary" (:too-close? viz-data) (str " inside"))
      :cx cx :cy cy :r (:radius viz-data)}]))

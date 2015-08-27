(ns gh4st.viz
  (:require
    [clojure.string :as string]
    [gh4st.math :refer [add-pos]]
    [gh4st.ai :refer [tick-actor
                      actor-target]]
    )
  )

;;----------------------------------------------------------------------
;; Actor Target Viz
;;----------------------------------------------------------------------

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

;;----------------------------------------------------------------------
;; Future Path Viz
;;----------------------------------------------------------------------

(def offset-factor 0.06)

(def actor-path-offset
  "Offsets so you can see overlaid paths"
  {:pacman (* offset-factor 0)
   :blinky (* offset-factor 1)
   :pinky (* offset-factor 2)
   :inky (* offset-factor -1)
   :clyde (* offset-factor -2)})

(defn actor-path
  [state name- len]
  (let [target (:pos (actor-target state name-))]
    (loop [points []
           curr-state state]
      (if (and (not= (last points) target)
               (< (count points) len))
        (let [next-state (tick-actor curr-state name-)]
          (recur
            (conj points (get-in curr-state [:actors name- :pos]))
            next-state))
        points))))

(defn actor-path-viz
  [state name- len]
  (let [points (actor-path state name- len)
        d (get actor-path-offset name-)]
    [:g
     [:polyline
      {:class (str "path path-" (name name-))
       :points (->> points
                    (map #(add-pos % [d d]))
                    (map #(string/join "," %))
                    (string/join " "))}]]))

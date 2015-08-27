(ns gh4st.viz
  (:require
    [clojure.string :as string]
    [gh4st.math :refer [add-pos
                        sub-pos
                        scale-dir
                        ]]
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
      (let [last-pos (last points)
            new-pos (get-in curr-state [:actors name- :pos])]
        (cond
          ;; stop if we are stuck in a position or have reached our target
          (or (= last-pos new-pos)
              (= last-pos target))
          points

          ;; stop if we are at the path length limit
          (< (count points) len)
          (recur
            (conj points new-pos)
            (tick-actor curr-state name-))

          :else points)))))

(defn actor-path-viz
  [state name- len]
  (let [path-points (actor-path state name- len)

        ;; offset
        offset (get actor-path-offset name-)

        [p1 p2] (take-last 2 path-points)
        arrow? (and p1 p2)

        ;; height direction
        [dx dy :as hdir] (when arrow?
                           (sub-pos p2 p1))

        ;; width direction 
        wdir [(- dy) dx] 

        al 0.5 ;; arrow length
        aw 0.1 ;; arrow width
        ah 0.1 ;; arrow height

        end (when arrow? (add-pos p1 (scale-dir hdir al)))

        arrow-point
        (fn [side]
          (-> end
              (sub-pos (scale-dir hdir ah))
              (add-pos (scale-dir wdir (* side aw)))))

        arrow-points (when arrow? [(arrow-point -1) end (arrow-point 1)])

        line-points (cond-> path-points
                      arrow? (-> butlast (concat [end])))

        make-svg-points
        (fn [pts]
          (->> pts
               (map #(add-pos % [offset offset]))
               (map #(string/join "," %))
               (string/join " ")))

        classname (str "path path-" (name name-))
        ]
    (when arrow?
      [:g
       [:polyline
        {:class classname
         :points (make-svg-points line-points)}]
       [:polyline
        {:class classname
         :points (make-svg-points arrow-points)}]
       ])))

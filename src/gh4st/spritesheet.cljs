(ns gh4st.spritesheet
  (:require
    [garden.core :refer [css]]
    [garden.stylesheet :refer [at-keyframes]]))

;; spritesheet source: http://pacman.shaunew.com/play/sprites

(def scale 3) ;; rendered the spritesheet at this scale (using the UI link above)
(def size (* scale 22))
(def cols 14)
(def rows 22)

(def pac-anim
  {:framelength 3
   :fps 30
   :direction :alternate
   :anims [:up :left :down :right]})

(def ghost-anim
  {:framelength 2
   :fps 30
   :direction :normal
   :anims [:up :left :down :right]})

(def sprites
  [{:name :blinky    :pos [0 1] :default-frame 0 :anim ghost-anim}
   {:name :pinky     :pos [0 2] :default-frame 1 :anim ghost-anim}
   {:name :inky      :pos [0 3] :default-frame 1 :anim ghost-anim}
   {:name :clyde     :pos [0 4] :default-frame 0 :anim ghost-anim}
   {:name :pacman    :pos [0 6] :default-frame 1 :anim pac-anim}
   {:name :mspacman  :pos [0 7] :default-frame 1 :anim pac-anim}
   {:name :cookieman :pos [0 8] :default-frame 1 :anim pac-anim}
   {:name :cherry     :pos [0 0]}
   {:name :strawberry :pos [1 0]}
   {:name :orange     :pos [2 0]}
   {:name :apple      :pos [3 0]}
   {:name :melon      :pos [4 0]}
   {:name :pretzel    :pos [8 0]}
   {:name :pear       :pos [9 0]}
   {:name :banana     :pos [10 0]}
   ])

(def sprite-map
  (zipmap (map :name sprites) sprites))

(defn bg-pos
  "background-position property value in garden format
  [[a b c d]]   ->   a b c d   (space delimited)"
  [[x y]]
  (let [tx (* size x)
        ty (* size y)]
    [[:left (str tx "px")
      :top  (str ty "px")]]))

(defmulti sprite-css
  (fn [sprite]
    (if (:anims sprite)
      :anim
      :static)))

(defmethod sprite-css :static
  [sprite]
  (let [rule (str ".sprite-" (name (:name sprite)))]
    (css
      [rule
       {:background-position (bg-pos (:pos sprite))}])))

(defmethod sprite-css :anim
  [sprite]
  (let [prefix (str ".sprite-" (name (:name sprite)))
        frame (:default-frame sprite)
        {:keys [framelength
                fps
                direction
                anims]} (:anim sprite)
        [x y] (:pos sprite)]
    (apply css (apply concat
      (for [[i aname] (map-indexed vector anims)]
        (let [rule (str prefix "-" (name aname))
              frames-id (str (subs rule 1) "-frames")
              anim-rule (str rule "-anim")
              dx (* framelength i)]
          (list
            [rule
             {:background-position (bg-pos [(+ x dx (or frame 0)) y])}]
            (at-keyframes
              frames-id
              [:from {:background-position (bg-pos [(+ x dx) y])}]
              [:to   {:background-position (bg-pos [(+ x dx (dec framelength)) y])}])
            [anim-rule
             {:animation-name frames-id
              :animation-duration (str (* framelength (/ 1 fps)) "s")
              :animation-timing-function (str "steps(" framelength ")")
              :animation-iteration-count "infinite"
              :animation-direction direction}])))))))

(ns gh4st.spritesheet)

;; spritesheet source: http://pacman.shaunew.com/play/sprites

(def scale 5) ;; rendered the spritesheet at this scale (using the UI link above)
(def size (* scale 22))
(def cols 14)
(def rows 22)

(def pac-anims
  {:up    {:x 0 :y 0 :n 3}
   :left  {:x 3 :y 0 :n 3}
   :down  {:x 6 :y 0 :n 3}
   :right {:x 9 :y 0 :n 3}
   })

(def ghost-anims
  {:up    {:x 0 :y 0 :n 2}
   :left  {:x 2 :y 0 :n 2}
   :down  {:x 4 :y 0 :n 2}
   :right {:x 6 :y 0 :n 2}
   })

(def coords
  {:pacman    {:x 0 :y 6 :default-frame 1 :anims pac-anims}
   :mspacman  {:x 0 :y 7 :default-frame 1 :anims pac-anims}
   :cookieman {:x 0 :y 8 :default-frame 1 :anims pac-anims}
   :blinky    {:x 0 :y 1 :default-frame 0 :anims ghost-anims}
   :pinky     {:x 0 :y 2 :default-frame 1 :anims ghost-anims}
   :inky      {:x 0 :y 3 :default-frame 1 :anims ghost-anims}
   :clyde     {:x 0 :y 4 :default-frame 0 :anims ghost-anims}

   :cherry     {:x 0 :y 0}
   :strawberry {:x 1 :y 0}
   :orange     {:x 2 :y 0}
   :apple      {:x 3 :y 0}
   :melon      {:x 4 :y 0}
   :pretzel    {:x 8 :y 0}
   :pear       {:x 9 :y 0}
   :banana     {:x 10 :y 0}
   })

(defn css-for-sprite
  [{:keys [x y anims default-frame] :as data}]
  
  )

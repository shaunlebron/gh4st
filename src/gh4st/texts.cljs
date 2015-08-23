(ns gh4st.texts)

(def victory-text
  [:p.victory-text
   "You got him!" [:br]
   [:span.focus "Press" [kbd "SHIFT + →"]]
   ])

(def texts
  [
   {:title "Meet Pac-Man..."
    :desc [:p
           "He wants fruit, but must run from the face of a ghost." [:br]
           [:span.focus "Press " [:kbd.blinky "1"] " to move your ghost."] [:br]
           "(Pac-Man will wait for his turn to move.)"]
    }

   {:title "No turning back"}
   {:title "Follow your nose"}
   {:title "Decisions, decisions"}
   {:title "Release the Ghost!"}
   {:title "Blinky needs help"}
   {:title "Pinky can help"}
   {:title "Pinky is different"}
   {:title "Work together"}
   {:title "Oh Clyde"}
   {:title "Well done Clyde"}
   {:title "Blinky and Clyde"}

   ]
  )

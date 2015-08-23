(ns gh4st.texts)

(def victory-text
  [:p.victory-text
   "You got him!" [:br]
   "Press " [:kbd.green "SHIFT"] " + " [:kbd.green "→"] " for next level." [:br]
   "Or press Z or Y to undo/redo for study."])

(def defeat-text
  [:p.defeat-text
   "Bah!  He got the fruit!" [:br]
   [:span.focus "Press " [:kbd "Z"] " or " [:kbd "Y"] " to undo/redo."] [:br]
   "Or press R to restart from the beginning."])

(def allow-defeat-text
  [:p.defeat-text
   "Yup.  It was inevitable." [:br]
   "Press " [:kbd.green "SHIFT"] " + " [:kbd.green "→"] " for next level." [:br]
   "Or press Z or Y to undo/redo for study."])

(def text0
  {:title "Meet Pac-Man..."
   :desc [:p
          "He wants fruit, but must run from the face of a ghost." [:br]
          [:span.focus "Press " [:kbd.blinky "1"] " to move your ghost."] [:br]
          "(Pac-Man will wait for his turn to move.)"]
   })

(def text1
  {:title "No turning back" 
   :desc [:p
          "Even though Pac-Man wants his fruit," [:br]
          " he can't turn back and is forced to take the long way." [:br]

          "Press " [:kbd.blinky "1"] " to cycle your turn to see Pac-Man move."]})

(def text2
  {:title "Follow your nose"
   :desc [:p
          "Pac-Man follows his nose, but he can smell fruit through walls." [:br]
          "He'll take a wrong turn if it smells better than the others." [:br]
          "Press" [:kbd.blinky "1"] " to cycle. " [:kbd "Z"] " to undo."]
   }
  )

(def texts
  [
   text0
   text1
   text2
   
   ;; {:title "Decisions, decisions"}
   ;; {:title "Release the Ghost!"}
   ;; {:title "Blinky needs help"}
   ;; {:title "Pinky can help"}
   ;; {:title "Pinky is different"}
   ;; {:title "Work together"}
   ;; {:title "Oh Clyde"}
   ;; {:title "Well done Clyde"}
   ;; {:title "Blinky and Clyde"}

   ]
  )

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

(def text3
  {:title "Decisions, decisions"
   :desc [:p
          "When he can't decide which turn smells better," [:br]
          "he favors UP, LEFT, DOWN, then RIGHT (counter-clockwise)." [:br]
          "Press " [:kbd.blinky "1"] " to see him favor the LEFT turn."
          ]}
  )

(def text4
  {:title "Release the ghost!"
   :desc [:p "Meet " [:span.blinky "Blinky"] ". He behaves exactly like Pac-Man," [:br]
          "except he prefers fruit of the Pac-Man variety :)" [:br]
          "Press " [:kbd.blinky "1"] " to cycle Blinky until he catches Pac-Man."]
   
   })

(def text5
 {:title "Blinky needs help"
  :desc [:p
         "Blinky has no hope of catching Pac-Man here." [:br]
         "His behavior simply won't allow him to." [:br]
         "Press " [:kbd.blinky "1"] " to cycle to the inevitable defeat."]
  } 
  )

(def text6
  {:title "Pinky can help"
   :desc [:p
          "Meet " [:span.pinky "Pinky"] ", your second ghost!" [:br]
          "You can now choose which ghost to move each turn." [:br]
          "Press" [:kbd.pinky "2"] " to move Pinky. " [:kbd.blinky "1"] " for Blinky."]
   }
  )

(def text7
  {:title "Pinky is different"
   :desc [:p
          "Pinky tries to get ahead of Pac-Man" [:br]
          "by aiming two tiles ahead of where Pac-Man is looking." [:br]
          "This doesn't always work out, as you can see here..." [:br]
          "Press" [:kbd.pinky "2"] " to move Pinky. "]
   }
  )

(def text8
  {:title "Work together"
   :desc [:p
          "They have to work together here." [:br]
          "Press" [:kbd.pinky "2"] " to move Pinky. " [:kbd.blinky "1"] " for Blinky."]
   }
  )

(def text9
  {:title "Oh, Clyde"
   :desc [:p
          "Meet your third ghost, " [:span.clyde "Clyde!"] [:br]
          " He chases Pac-Man like Blinky, except he gets scared" [:br]
          "when he's too close and tries to run away." [:br]
          "Press" [:kbd.clyde "3"] " to move Clyde."]

   }
  )

(def text10
  {:title "Forced Bravery"
   :desc [:p
          "Despite Clyde's fear, he can still spook Pac-Man since" [:br]
          "Clyde can only change his mind at intersections." [:br]
          "Press" [:kbd.clyde "3"]  " to move Clyde."]
   }
  )

(def text11
  {:title "Blinky and Clyde"
   :desc [:p
          "Enable the Paths/Target visuals on the right" [:br]
          "to help Clyde charge at the right moment." [:br]
          "Press " [:kbd.blinky "1"] " to move Blinky. " [:kbd.clyde "3"] " for Clyde."
          ]
   }
  )

(def text12
  {:title "Pinky and Clyde"
   :desc [:p
          "You may be noticing some foolproof strategies at this point." [:br]
          "Press " [:kbd.pinky "2"] " to move Pinky. " [:kbd.clyde "3"] " for Clyde."
          ]
   }
  )

(def text13
  {:title "Inky, the wingman"
   :desc [:p
          "Meet "[:span.inky "Inky"] ", your last ghost. He tries to flank Pac-Man" [:br]
          "from the side opposite to Blinky. See \"Targets\" button." [:br]
          "Press " [:kbd.inky "4"] " to move Inky." [:kbd.blinky "1"] " for Blinky."
          ]
   }
  )

(def text14
  {:title "All together now"
   :desc [:p
          "Alright, we can do this!" [:br]
          "Use "
          [:kbd.blinky "1"] " "
          [:kbd.pinky "2"] " "
          [:kbd.clyde "3"] " "
          [:kbd.inky "4"] " to coordinate a swift death."
          ]
   }
  )

(def text-end
  {:title "Well Done!"
   :desc [:div
          [:p
          "Now that you've lived as these meticulous monsters," [:br]
          "you should have no problem outsmarting them in their original games." [:br]
          "Thanks for playing this 'post-compo' concept and Happy Ludum Dare 33!" [:br]]
          [:div.links
          [:a {:href "http://twitter.com/shaunlebron"} "@shaunlebron"]
          " | " [:a {:href "https://github.com/shaunlebron/gh4st"} "github"]
          " | " [:a {:href "http://ludumdare.com/compo/ludum-dare-33/?action=preview&uid=31638"} "Ludum Entry"]
          ]]
   }
  )

(def texts
  [
   text0
   text1
   text2
   text3
   text4
   text5
   text6
   text7
   text8
   text9
   text10
   text11
   text12
   text13
   text14

   text-end

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

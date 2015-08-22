(ns gh4st.state
  (:require
    [gh4st.levels :refer [level1]]))

(defonce app-state
  (atom level1))

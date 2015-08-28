(ns ^:figwheel-always gh4st.core
  (:require
    [sablono.core :refer-macros [html]]
    [om-tools.core :refer-macros [defcomponent]]
    [om.core :as om]
    [gh4st.state :refer [app-state]]
    [gh4st.view :refer [welcome game level-select]]
    ))

(enable-console-print!)

(defcomponent root
  [data owner]
  (render [_this]
    (html
      (case (:screen data)
        :home (om/build welcome data)
        :game (om/build game data)
        :level-select (om/build level-select data)
        nil))))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

(defn on-js-reload []
  nil)


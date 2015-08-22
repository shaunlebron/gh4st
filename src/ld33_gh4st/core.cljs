(ns ^:figwheel-always ld33-gh4st.core
    (:require
      [sablono.core :refer-macros [html]]
      [om-tools.core :refer-macros [defcomponent]]
      [om.core :as om]
      ))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  nil)

(defcomponent root
  [data owner]
  (render [_this]
    (html
      [:div (:text data)])))

(om/root
  root
  app-state
  {:target (. js/document getElementById "app")})

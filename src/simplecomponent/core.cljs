(ns simplecomponent.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :refer [register-handler
                                                path
                                                register-sub
                                                dispatch
                                                dispatch-sync
                                                subscribe]]
            [cljsjs.d3]))

(enable-console-print!)
(println "Edits to this text should show up in your developer console.")

(def app-state {:circles [{:name "circle 1"
                      :x 10
                      :y 10
                      :r 10
                      :color "black"}
                     {:name "circle 2"
                      :x 35
                      :y 35
                      :r 15
                      :color "red"}
                     {:name "circle 3"
                      :x 100
                      :y 100
                      :r 30
                      :color "blue"}]})

;; define your app data so that it doesn't get over-written on reload
;;---- Event handlers-----------
(register-handler
  :initialize-db
  (fn
    [_ _]
    app-state))

(register-handler
  :update
  (fn
    [db [_ idx param val]]
    (println "idx " idx "param " param "val " val)
    (assoc-in db [:circles idx param ] val)))

;;---- Subscription handlers-----------
(register-sub
  :circles
  (fn
    [db _]
    (reaction (:circles @db))))

(defn d3-inner [data]
 (reagent/create-class
    {:reagent-render (fn [] [:div [:svg {:width 400 :height 800}]])

     :component-did-mount (fn []
                            (let [d3data (clj->js data)]
                              (println "didmount! " (str data))
                              (println "didmount! " (str d3data))
                              (.. js/d3
                                  (select "svg")
                                  (selectAll "circle")
                                  (data d3data)
                                  enter
                                  (append "svg:circle")
                                  (attr "cx" (fn [d] (.-x d)))
                                  (attr "cy" (fn [d] (.-y d)))
                                  (attr "r" (fn [d] (.-r d)))
                                  (attr "fill" (fn [d] (.-color d))))))

     :component-did-update (fn [this]
                             (let [[_ data] (reagent/argv this)
                                   d3data (clj->js data)]
                               (println "in did-update!")
                               (println "in did-update! data " data)
                               (println "in did-update! d3data " d3data)
                               (.. js/d3
                                   (selectAll "circle")
                                   (data d3data)
                                   (attr "cx" (fn [d] (.-x d)))
                                   (attr "cy" (fn [d] (.-y d)))
                                   (attr "r" (fn [d] (.-r d)))))
    )}))


(defn slider [param idx value]
  [:input {:type "range"
           :value value
           :min 0
           :max 500
           :style {:width "100%"}
           :on-change #(dispatch [:update idx param (-> % .-target .-value)])}])

(defn sliders [data]
    [:div (for [[idx d] (map-indexed vector data)]
            ^{:key (str "slider-" idx)}
            [:div
             [:h3 (:name d)]
             "x " (:x d) (slider :x idx (:x d))
             "y " (:y d) (slider :y idx (:y d))
             "r " (:r d) (slider :r idx (:r d))])])

(defn app []
  (let [data (subscribe [:circles])]
    (fn []
      [:div {:class "container"}
        [:div {:class "row"}
          [:div {:class "col-md-5"}
            [d3-inner @data]]
          [:div {:class "col-md-5"}
            [sliders @data]]]]
      )))

(let []
  (dispatch-sync [:initialize-db])
  (reagent/render-component [app]
                            (. js/document (getElementById "app"))))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(ns al.image
  (:require [clojure.string :as string]
            [al.input :as input]))

(defrecord Image [data width height])

(defn pixel [image x y]
  (let [offset (+ (* y (:width image)) x)]
    (nth (:data image) offset)))

(defn set-pixel [image val x y]
  (let [offset (+ (* y (:width image)) x)]
    (Image.
     (assoc (:data image) offset val)
     (:width image)
     (:height image))))

(defn read-image [day]
  (->> (input/raw-lines day)
       (mapv (fn [line] (mapv #(read-string (str %)) line)))
       ((fn [vv]
          (Image. (into [] (apply concat vv))
                  (count (first vv))
                  (count vv))))))

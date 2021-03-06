(ns al.image
  (:require [clojure.string :as string]
            [al.input :as input]))

(defrecord Image [data width height])

(defn pixel
  ([image [x y]]
   (pixel image x y))
  ([image x y]
  (let [offset (+ (* y (:width image)) x)]
    (nth (:data image) offset))))

(defn set-pixel
  ([image val [x y]]
   (set-pixel image val x y))
  ([image val x y]
   (let [offset (+ (* y (:width image)) x)]
     (Image.
      (assoc (:data image) offset val)
      (:width image)
      (:height image)))))

(defn read-image [day]
  (->> (input/raw-lines day)
       (mapv (fn [line] (mapv #(read-string (str %)) line)))
       ((fn [vv]
          (Image. (into [] (apply concat vv))
                  (count (first vv))
                  (count vv))))))

(defn create [width height x]
  (->Image (vec (repeat (* width height) x))
           width
           height))

(defn neighbors
  ([image [x y]]
   (neighbors image x y))
  ([image x y]
   (let [left (- x 1)
         up (- y 1)
         right (+ x 1)
         down (+ y 1)]
     (->>
      (if (>= left 0) [[left y]])
      (concat (if (< right (:width image)) [[right y]]))
      (concat (if (>= up 0) [[x up]]))
      (concat (if (< down (:height image)) [[x down]]))))))


(defn print-image [image]
  (println (:width image) "x" (:height image) ":")
  (doseq [y (range (:width image))]
    (let [i (* y (:width image))]
    (println (apply str (subvec (:data image) i (+ i (:width image))))))))

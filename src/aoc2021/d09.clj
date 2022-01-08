(ns aoc2021.d09
  (:require [al.image :as image]))

(def input (image/read-image "d09"))

(defn neighbors [image x y]
  (let [left (- x 1)
        up (- y 1)
        right (+ x 1)
        down (+ y 1)]
    (->>
     (if (>= left 0) [[left y]])
     (concat (if (< right (:width image)) [[right y]]))
     (concat (if (>= up 0) [[x up]]))
     (concat (if (< down (:height image)) [[x down]])))))

(defn is-low-point [image x y]
  (let [val (image/pixel image x y)
        left (- x 1)
        up (- y 1)
        right (+ x 1)
        down (+ y 1)]
    (->>
     (or (< left 0) (< val (image/pixel image left y)))
     (and (or (>= right (:width image)) (< val (image/pixel image right y))))
     (and (or (< up 0) (< val (image/pixel image x up))))
     (and (or (>= down (:height image)) (< val (image/pixel image x down)))))))

(defn find-low-points [image]
  (->>
   (for [y (range (:height image))
         x (range (:width image))]
     (if (is-low-point image x y) [x y]))
   (filter identity)))

(defn part1 []
  (let [image input]
    (->>
     (find-low-points image)
     (map #(apply image/pixel image %))
     (map #(+ 1 %))
     (reduce +))))

(defn flood-fill [image guard-val val start-x start-y]
  (loop [image image
         candidates [[start-x start-y]]
         touched #{}]
    (if-let [[x y] (first candidates)]
      (let [inside (and (not= guard-val (image/pixel image x y))
                        (not (contains? touched [x y])))]
        (recur (if inside (image/set-pixel image val x y)
                   image)
               (into (subvec candidates 1)
                     (if inside (neighbors image x y)))
               (if inside (conj touched [x y])
                   touched)))
      [image touched])))

(defn region-sizes [image]
  (loop [image image
         [pos & low-points] (find-low-points image)
         counts []
         val \a]
    (if pos
      (let [[image touched] (apply flood-fill image 9 val pos)]
        (recur image
               low-points
               (conj counts (count touched))
               (char (inc (int val)))))
      counts)))

(defn part2 []
  (->> input
       (region-sizes)
       (sort)
       (take-last 3)
       (reduce *)))

(ns aoc2021.d15
  (:require [al.heap :as heap]
            [al.image :as image]))

(def input (image/read-image "d15"))

(defn relax [image cost queue prev-cost neighbors]
  (loop [cost cost
         queue queue
         [pos & rest] neighbors]
    (if (some? pos)
      (let [current-cost (+ (image/pixel image pos) prev-cost)]
        (if (< current-cost (image/pixel cost pos))
          (recur (image/set-pixel cost current-cost pos)
                 (conj queue [current-cost pos])
                 rest)
          (recur cost
                 queue
                 rest)))
      [cost queue])))

(defn shortest-path [image start end]
  (loop [cost (->> (image/create (:width image) (:height image) Integer/MAX_VALUE)
                   (#(image/set-pixel % 0 start)))
         visited #{}
         queue (heap/make-heap [[0 start]])]
    (let [[current-cost pos] (peek queue)
          queue (pop queue)]
      (if (not= pos end)
        ;; Nodes may be inserted multiple times into queue, skip if already visited
        (if (contains? visited pos)
          (recur cost
                 visited
                 queue)
          (let [unvisited-neighbors (->> (image/neighbors image pos)
                                         (filter #(not (contains? visited %))))
                [cost queue] (relax image cost queue (image/pixel cost pos) unvisited-neighbors)]
            (recur cost
                   (conj visited pos)
                   queue)))
        (image/pixel cost end)))))

(defn part1 []
  (shortest-path input [0 0] [(dec (:width input)) (dec (:height input))]))

(def full-map-width (* 5 (:width input)))
(def full-map-height (* 5 (:height input)))
(def full-map (image/->Image
               (into []
                     (for [y (range full-map-height)
                           x (range full-map-width)]
                       (let [orig-x (mod x (:width input))
                             orig-y (mod y (:height input))
                             increment (+ (quot x (:width input)) (quot y (:height input)))]
                         (+ 1
                            (mod (- (+ increment
                                       (image/pixel input orig-x orig-y))
                                    1)
                                 9)))))
               (* 5 (:width input))
               (* 5 (:height input))))
(defn part2 []
  (shortest-path full-map [0 0] [(dec (:width full-map)) (dec (:height full-map))]))

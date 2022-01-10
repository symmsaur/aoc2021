(ns aoc2021.d15
  (:require [al.heap :as heap]
            [al.image :as image]))

(def input (image/read-image "d15"))

(defn relax [image cost queue prev-cost neighbors]
  (loop [cost cost
         queue queue
         [current & rest] neighbors]
    (if (nil? current)
      [cost queue]
      (let [current-cost (+ (image/pixel image current) prev-cost)]
        (if (<  current-cost (image/pixel cost current))
          (recur (image/set-pixel cost current-cost current)
                 (conj queue [current-cost current])
                 rest)
          (recur cost
                 queue
                 rest))))))

(defn shortest-path [image start end]
  (loop [cost (->> (image/create (:width image) (:height image) Integer/MAX_VALUE)
                   (#(image/set-pixel % 0 start)))
         visited #{}
         queue (heap/make-heap [[0 start]])]
    (let [[current-cost current-pos] (peek queue)]
      (if (not= current-pos end)
        ;; Nodes may be inserted multiple times into queue, skip if already visited
        (if (contains? visited current-pos)
          (recur cost
                 visited
                 (pop queue))
          (let [unvisited-neighbors (->> (image/neighbors image current-pos)
                                         (filter #(not (contains? visited %))))
                [cost queue] (relax image cost queue current-cost unvisited-neighbors)]
            (recur cost
                   (conj visited current-pos)
                   (pop queue))))
        (image/pixel cost end)))))

(defn part1 []
  (shortest-path input [0 0] [(dec (:width input)) (dec (:height input))]))
(defn part2 [] nil)

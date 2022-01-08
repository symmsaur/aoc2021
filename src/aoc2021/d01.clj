(ns aoc2021.d01
  (:require [al.input :as input]))

(def input (input/read-lines "d01"))

(defn count-increasing-pairs [seq]
  (->> seq
       (partition 2 1)
       (filter #(apply < %))
       (count)))

(defn part1 []
  (count-increasing-pairs input))

(defn part2 [] (->> input
                    (partition 3 1)
                    (map #(apply + %))
                    (count-increasing-pairs)))

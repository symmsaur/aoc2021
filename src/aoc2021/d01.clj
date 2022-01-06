(ns aoc2021.d01
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))
;; generic
(defn read-input []
  (->> (slurp (io/resource "d01/input"))
       (string/split-lines)
       (map edn/read-string)))

;; specific
(defn count-increasing-pairs [seq]
  (->> seq
       (partition 2 1)
       (filter #(apply < %))
       (count)))

(defn part1 []
  (count-increasing-pairs (read-input)))

(defn part2 [] (->> (read-input)
       (partition 3 1)
       (map #(apply + %))
       (count-increasing-pairs)))

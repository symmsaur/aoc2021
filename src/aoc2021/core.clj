(ns aoc2021.core
  (:gen-class)
  (:require [aoc2021.d01 :as d01]
            [aoc2021.d02 :as d02]))

(defn -main
  "Advent of Code 2021"
  [& args]
  (println "Day 1")
  (println "Part 1" (d01/part1))
  (println "Part 2" (d01/part2))
  (println "Day 2")
  (println "Part 1" (d02/part1))
  (println "Part 2" (d02/part2)))

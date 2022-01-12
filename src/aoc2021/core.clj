(ns aoc2021.core
  (:gen-class)
  (:require [aoc2021.d01 :as d01]
            [aoc2021.d02 :as d02]
            [aoc2021.d03 :as d03]
            [aoc2021.d09 :as d09]
            [aoc2021.d15 :as d15]))

(defn -main
  "Advent of Code 2021"
  [& args]
  (println "Advent of Code 2021")
  (println "===================")
  (println "Day 1")
  (println "Part 1" (d01/part1))
  (println "Part 2" (d01/part2))
  (println "Day 2")
  (println "Part 1" (d02/part1))
  (println "Part 2" (d02/part2))
  (println "Day 3")
  (println "Part 1" (d03/part1))
  (println "Part 2" (d03/part2))
  (println "...")
  (println "Day 9")
  (println "Part 1" (d09/part1))
  (println "Part 2" (d09/part2))
  (println "...")
  (println "Day 15")
  (println "Part 1" (d15/part1))
  (println "Part 2" (d15/part2)))

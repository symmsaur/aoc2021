(ns aoc-clojure-noob.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))
;; generic
(defn read-input []
  (->> (slurp "input")
       (string/split-lines)
       (map edn/read-string)))

;; specific
(defn count-increasing-pairs [seq]
  (->> seq
       (partition 2 1)
       (filter #(apply < %))
       (count)))

(println "Part 1")
(println (count-increasing-pairs (read-input)))

(println "Part 2")
(println
 (->> (read-input)
      (partition 3 1)
      (map (fn [triple] (apply + triple)))
      (count-increasing-pairs)))

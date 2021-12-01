(ns aoc-clojure-noob.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))
;; generic
(defn input [] (slurp "input"))
;; specific
(println "Part 1")
(println (->>
          (input)
          (string/split-lines)
          (map edn/read-string)
          (partition 2 1)
          (map (fn [pair] (apply < pair)))
          (filter identity)
          (count)))

(println "Part 2")
(println (->>
          (input)
          (string/split-lines)
          (map edn/read-string)
          (partition 3 1)
          (map (fn [triple] (apply + triple)))
          (partition 2 1)
          (map (fn [pair] (apply < pair)))
          (filter identity)
          (count)))

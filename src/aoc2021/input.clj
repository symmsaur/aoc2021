(ns aoc2021.input
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn slurp-day [day]
  (slurp (io/resource (str day "/input"))))

(defn read-lines [day]
  (->> (slurp-day day)
       (string/split-lines)
       (map edn/read-string)))

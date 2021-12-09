(ns noob
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

(defn read-input []
  (->> (slurp "input") (#(string/split % #",")) (map edn/read-string)))

(defn part1 []
  (let [data (read-input)
        median (nth (sort data) (/ (count data) 2))
        diffs (map #(Math/abs (- % median)) data)]
    (reduce + diffs)))

(defn cost-for-pos [pos]
  (let [data (read-input)
        diffs (map #(Math/abs (- % pos)) data)
        cost (map #(quot (* (+ 1 %) %) 2) diffs)]
    (reduce + cost)))

(defn part2 []
  (->> (map cost-for-pos (range 1000))
       (apply min)))

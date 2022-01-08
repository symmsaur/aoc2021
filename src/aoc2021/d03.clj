(ns aoc2021.d03
  (:require [al.input :as input]
            [clojure.string :as string]))

(def input (input/raw-lines "d03"))

(defn transpose [vv]
  (apply mapv vector vv))

(defn gamma []
  (->> input
       (transpose)
       (map frequencies)
       (map (fn [kv] (key (apply max-key val kv))))
       (string/join)
       (#(read-string (str "2r" %)))))

(defn epsilon []
  (->> input
       (transpose)
       (map frequencies)
       (map (fn [kv] (key (apply min-key val kv))))
       (string/join)
       (#(read-string (str "2r" %)))))

(defn part1 []
  (* (gamma) (epsilon)))

(defn internal-at-index [key-fn l i]
  (->> (map #(nth % i) l)
       (frequencies)
       (#(key (apply key-fn val %)))))

(defn max-at-index [l i]
  (internal-at-index max-key l i))

(defn min-at-index [l i]
  (internal-at-index min-key l i))

(defn scan-filter [sel-fn l]
  (loop [i 0
         l l]
    (if (< i (count (first l)))
      (recur (+ 1 i)
             (filter #(= (nth % i)
                         (sel-fn l i)) l))
      (first l))))

(defn rating [sel-fn]
  (->> input
       (scan-filter sel-fn)
       (#(read-string (str "2r" %)))))

(defn oxygen []
  (rating max-at-index))

(defn co-2 []
  (rating min-at-index))

(defn part2 []
  (* (oxygen) (co-2)))

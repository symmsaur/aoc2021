(ns noob
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

(defn read-input []
  (->> (slurp "test")
       string/split-lines))

(defn transpose [vv]
  (apply mapv vector vv))

(defn gamma []
  (->> (read-input)
       (transpose)
       (map frequencies)
       (map (fn [kv] (key (apply max-key val kv))))
       (string/join)
       (#(read-string (str "2r" %)))))

(defn epsilon []
  (->> (read-input)
       (transpose)
       (map frequencies)
       (map (fn [kv] (key (apply min-key val kv))))
       (string/join)
       (#(read-string (str "2r" %)))))

(println "part1"
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
  (->> (read-input)
       (scan-filter sel-fn)
       (#(read-string (str "2r" %)))))

(defn oxygen []
  (rating max-at-index))

(defn co-2 []
  (rating min-at-index))

(println "part2"
         (* (oxygen) (co-2)))

(ns noob
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

(defn read-input []
  (->> (slurp "input")
       (string/trim)
       (#(string/split % #"\,"))
       (map edn/read-string)))

(defn spawn [m]
  (assoc m 9
         (+ (get m 9 0)
            (get m 0 0))))

(defn reset-timers [m]
  (assoc m 7 (+ (get m 7 0) (get m 0 0))))

(defn shuffle-down [m]
  (into (sorted-map)
        (map (fn [[k v]]
               (case k
                 0 nil
                 [(- k 1) v]))
             m)))

(defn step [m]
  (->> m
       (do (println m) m)
       (spawn)
       (reset-timers)
       (shuffle-down)))

(defn part1 []
  (->> (read-input)
       (#(into (sorted-map) (frequencies %)))
       (#(nth (iterate step %) 256))
       (vals)
       (reduce +)))

(println "====")
(println "Part 1" (part1))

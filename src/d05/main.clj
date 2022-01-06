(ns noob
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [clojure.pprint :as pprint]))

(defrecord Coord [x y])

(defn parse-coords [s]
  (->> (string/split s #"\,")
       (map edn/read-string)
       (#(Coord. (first %) (second %)))))

(defn read-input []
  (->> (slurp "input")
       (string/split-lines)
       (map #(string/split % #" \-> "))
       (map (fn [v] (mapv parse-coords v)))))

(defn inclusive-range [start end] (range start (inc end)))

(defn cardinal-line [[p1 p2]]
  (if (= (:x p1) (:x p2))
    (into #{} (map #(Coord. (:x p1) %) (apply inclusive-range (sort (map :y [p1 p2])))))
    (if (= (:y p1) (:y p2))
      (into #{} (map #(Coord. % (:y p1)) (apply inclusive-range (sort (map :x [p1 p2])))))
      #{})))

(defn diagonal-line [[p1 p2]]
  (if (and (not= (:x p1) (:x p2)) (not= (:y p1) (:y p2)))
    (into #{} (map #(Coord. (first %) (second %))
                   (map vector
                        (let [dir (if (< (:x p1) (:x p2)) 1 -1)]
                          (range (:x p1) (+ dir (:x p2)) dir))
                        (let [dir (if (< (:y p1) (:y p2)) 1 -1)]
                          (range (:y p1) (+ dir (:y p2)) dir)))))
    #{}))

(defn partx [line-fn]
  (count
   (loop [[first & rest] (read-input)
          once #{}
          twice #{}]
     (if first
       (let [touched-squares (line-fn first)]
         (recur rest
                (set/union once touched-squares)
                (set/union twice (set/intersection once touched-squares))))
       twice))))

(defn part1 []
  (partx cardinal-line))

(defn part2 []
  (partx #(set/union (cardinal-line %) (diagonal-line %))))

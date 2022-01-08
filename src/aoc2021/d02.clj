(ns aoc2021.d02
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [al.input :as input]))

(defn parse-line [line]
  (let [[p1 p2] line]
       [p1 (edn/read-string p2)]))

(def input
  (input/read-lines
   "d02"
   (fn [line]
     (parse-line (string/split line #"\s")))))

(def dir-lookup-x {"forward" 1})
(def dir-lookup-y {"down" 1,
                   "up" -1})

(defn do-dir [lookup]
  (->> (keep
        #(let [[dir len] %]
           (if-let [d (lookup dir)]
             (* d len)))
        input)
       (reduce +)))

(defn part1 []
  (* (do-dir dir-lookup-x) (do-dir dir-lookup-y)))

(defn part2 []
  (->> (reduce (fn [state instr]
                 (let [[x y aim] state
                       [dir len] instr]
                   (case dir
                     "forward" [(+ x len) (+ y (* len aim)) aim]
                     "up" [x y (- aim len)]
                     "down" [x y (+ aim len)])))
               [0, 0, 0]
               input)
       (take 2) ;; x and y in state
       (reduce *)))

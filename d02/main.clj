(ns noob
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

(defn parse-line [line]
  (let [[p1 p2] line]
       [p1 (edn/read-string p2)]))

(defn read-input []
  (->> (slurp "input")
       string/split-lines
       (map #(string/split % #"\s"))
       (map parse-line)))

(def dir-lookup-x {"forward" 1})
(def dir-lookup-y {"down" 1,
                   "up" -1})

(defn do-dir [lookup]
  (->> (keep
        #(let [[dir len] %]
           (if-let [d (lookup dir)]
             (* d len)))
        (read-input))
       (reduce +)))

(* (do-dir dir-lookup-x) (do-dir dir-lookup-y))

; Part 2

(->> (reduce (fn [state instr]
               (let [[x y aim] state
                     [dir len] instr]
                 (case dir
                   "forward" [(+ x len) (+ y (* len aim)) aim]
                   "up" [x y (- aim len)]
                   "down" [x y (+ aim len)])))
             [0, 0, 0]
             (read-input))
     (take 2) ;; x and y in state
     (reduce *))

(ns noob
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(def score {\) 3 \] 57 \} 1197 \> 25137})
(def matching {\) \( \] \[ \} \{ \> \<})
(def rev-matching (set/map-invert matching))

(defn score-corrupted [line]
  (loop [[c & cs] line
         stack []]
    (if-let [s (get score c)]
      (if (not= (matching c) (last stack))
        s
        (recur cs
                (pop stack)))
    (if cs
      (recur cs
             (conj stack c))))))

(defn part1 []
  (->> (slurp "input")
       (string/split-lines)
       (map score-corrupted)
       (filter identity)
       (reduce +)))

(def test ["[(()[<>])]({[<{<<[]>>("])

(defn completion [line]
  (loop [[c & cs] line
         stack []]
    (if (get score c)
      (if (not= (matching c) (last stack))
        (assert false)
        (recur cs
               (pop stack)))
      (if c
        (recur cs
               (conj stack c))
        (map rev-matching stack)))))

(def part2-score {\) 1 \] 2 \} 3 \> 4})

(defn part2 []
  (->> (slurp "input")
       (string/split-lines)
       (filter #(nil? (score-corrupted %)))
       (map completion)
       (map reverse)
       (map #(reduce (fn [s c] (+ (* 5 s) (part2-score c))) 0 %))
       (sort)
       (#(nth % (quot (count %) 2)))))

(part2)

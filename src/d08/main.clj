(ns noob
  (:require [clojure.string :as string]
            [clojure.set :as set]))

(defn part1 []
  (->> (slurp "input")
       (string/split-lines)
       (map (fn [line] (->> line
                            (#(string/split % #"\|"))
                            (second)
                            (string/trim)
                            (#(string/split % #" ")))))
       (reduce concat)
       (filter #(case (count %)
                  2 true
                  4 true
                  3 true
                  7 true
                  false))
       (count)))

; 0: 6
; 1: 2 *
; 2: 5
; 3: 5
; 4: 4 *
; 5: 5
; 6: 6
; 7: 3 *
; 8: 7 *
; 9: 6

; 6 segments
; 0 contains 1 7
; 6 contains none
; 9 contains 1 4 7
;
; 4 - 1 gives a
;
; 5 segments
; 2 contains none
; 3 contains 1 7
; 5 contains a

(defn pattern-len [n coll]
  (filter #(= n (count %)) coll))

(defn pattern-intersect [coll positive negative]
  (filter (fn [pattern]
            (and
             (every? #(set/subset? % pattern) positive)
             (not-any? #(set/subset? % pattern) negative)))
          coll))

(defn decode-digits [signal-pattern]
  (let [one (first (pattern-len 2 signal-pattern))
        four (first (pattern-len 4 signal-pattern))
        seven (first (pattern-len 3 signal-pattern))
        eight (first (pattern-len 7 signal-pattern))
        zero (first (pattern-intersect (pattern-len 6 signal-pattern) [one seven] [four]))
        six (first (pattern-intersect (pattern-len 6 signal-pattern) [][one four seven]))
        nine (first (pattern-intersect (pattern-len 6 signal-pattern) [one four seven] []))
        a (set/difference four one)
        two (first (pattern-intersect (pattern-len 5 signal-pattern) [] [one four seven a]))
        three (first (pattern-intersect (pattern-len 5 signal-pattern) [one seven] [four]))
        five (first (pattern-intersect (pattern-len 5 signal-pattern) [a] [one four seven]))]
    {zero 0 one 1 two 2 three 3 four 4 five 5 six 6 seven 7 eight 8 nine 9}))

(def t "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

(defn get-digits [digit-map output-value]
  (map #(get digit-map %) output-value))

(defn to-num [digits]
  (reduce (fn [a b] (+ (* 10 a) b))
          digits))

(defn part2 []
  (let [displays (->> (slurp "input")
                      (string/split-lines)
                      (map (fn [line] (->> line
                                           (#(string/split % #"\|"))
                                           (map string/trim)
                                           (map #(string/split % #" "))
                                           (map #(map set %))))))]
    (->> displays
         (map #(get-digits
           (decode-digits (first %))
           (second %)))
         (map to-num)
         (reduce +))))

(part2)

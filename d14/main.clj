(ns noob
  (:require [clojure.string :as string]))

(defn read-input []
  (let [lines (->> (slurp "input")
                   (string/split-lines))
        template (first lines)
        rules (->> (drop 2 lines)
                   (map #(string/split % #" -> "))
                   (reduce (fn [m kv] (apply assoc m kv)) {}))]
    [template rules]))

;; naive approach
(defn polymerize [[template rules]]
  [(->> template
        (partition 2 1)
        (map (fn [s] (str (get rules (apply str s)) (second s))))
        (concat [(str (first template))])
        (string/join))
   rules])

(defn part1 []
  (->> (first (nth (iterate polymerize (read-input)) 10))
       (frequencies)
       (#(- (val (apply max-key val %)) (val (apply min-key val %))))))

;; counting pairs approach
(defn mapify [template rules]
  [(->> template
        (partition 2 1)
        (map (partial apply str))
        frequencies)
   (->> rules
        (mapcat (fn [[k v]]
                  [k
                   [(str (first k) v) (str v (last k))]]))
        (apply hash-map))])

(defn sum-into-map [coll]
  (reduce (fn [m [key n]]
            (assoc m key (+ n (get m key 0))))
          {}
          coll))

(defn polymerize-counts [[pair-nums pair-rules]]
  [(->> (map (fn [[key n]]
               (->> (get pair-rules key)
                    (map #(vector % n))))
             pair-nums)
        (apply concat)
        (sum-into-map))
   pair-rules])

(defn generation [[pair-nums pair-rules] i]
  (nth (iterate polymerize-counts [pair-nums pair-rules]) i))

(defn part2 []
  (let [[template rules] (read-input)]
    (->>
     (first (generation (mapify template rules) 40))
     (map(fn [[k n]] [(first k) n]))
     (concat [[(last template) 1]])
     (sum-into-map)
     (#(- (val (apply max-key val %)) (val (apply min-key val %)))))))

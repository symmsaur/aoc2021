(ns noob
  (:require [clojure.string :as string]
            [clojure.edn :as edn]))

(defn read-input []
  (->> (slurp "input")
       (string/trim)
       (#(string/split % #"\,"))
       (map edn/read-string)))

(def memo-fishy
  (memoize
   (fn [days timer]
     (cond
       (= 0 days) 1
       (= 0 timer) (+ (memo-fishy (dec days) 6) (memo-fishy (dec days) 8))
       :else (memo-fishy (dec days) (dec timer))))))

(apply + (map (partial memo-fishy 256) (read-input)))

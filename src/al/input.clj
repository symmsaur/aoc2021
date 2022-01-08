(ns al.input
  (:require [clojure.string :as string]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn slurp-day [day]
  (slurp (io/resource (str day "/input"))))

(defn raw-lines [day]
  (->> (slurp-day day)
       (string/split-lines)))

(defn read-lines
  ([day] (read-lines day edn/read-string))
  ([day f] (->> (raw-lines day)
                (map f))))

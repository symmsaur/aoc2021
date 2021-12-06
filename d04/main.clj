(ns noob
  (:require [clojure.string :as string]))

(->> (slurp "test")
     (string/split-lines))

(ns util)

;; Convenient function to generate scaffold for implementing interface
(defn scaffold [iface]
  (doseq [[iface methods] (->> iface .getMethods
                            (map #(vector (.getName (.getDeclaringClass %))
                                    (symbol (.getName %))
                                    (count (.getParameterTypes %))))
                            (group-by first))]
    (println (str "  " iface))
    (doseq [[_ name argcount] methods]
      (println
        (str "    "
          (list name (into ['this] (take argcount (repeatedly gensym)))))))))

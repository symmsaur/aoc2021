(ns al.heap
  (:import (clojure.lang IPersistentCollection)))

(defn parent [idx]
  (quot (- idx 1) 2))

(defn children [idx]
  (let [cdx1 (+ (* idx 2) 1)
        cdx2 (+ cdx1 1)]
    [cdx1 cdx2]))

(defn swap [v i j]
  (assoc v i (v j) j (v i)))

(defn lt [a b]
  (if (= (compare a b) -1)
    true
    false))

(defn sift-up
  "Sift up the last element in a heapy vector"
  [heapv]
  (loop [heapv heapv
         idx (- (count heapv) 1)
         pdx (parent idx)]
    (if (and (not= idx pdx)
             (lt (heapv idx) (heapv pdx)))
      (recur (swap heapv idx pdx)
             pdx
             (parent pdx))
      heapv)))

(defn sift-down
  "Sift down the first element in a heapy vector"
  [heapv]
  (loop [heapv heapv
         idx 0]
    (let [[cdx1 cdx2] (children idx)
          child1-smaller (and (< cdx1 (count heapv))
                              (lt (heapv cdx1) (heapv idx)))
          child2-smaller (and (< cdx2 (count heapv))
                              (lt (heapv cdx2) (heapv idx)))
          child2<child1  (and child1-smaller
                              child2-smaller
                              (lt (heapv cdx2) (heapv cdx1)))
          swap-idx (case [child1-smaller child2-smaller child2<child1]
                     [true false false] cdx1
                     [true true false] cdx1
                     [true true true] cdx2
                     [false true false] cdx2
                     idx)]
      (if (not= swap-idx idx)
        (recur (swap heapv idx swap-idx)
               swap-idx)
        heapv))))

(deftype Heap
    [data]
  clojure.lang.Counted
  (count [this] (count data))
  clojure.lang.IPersistentCollection
  (empty [this] (Heap. []))
  (cons [this elem] (Heap. (sift-up (conj data elem))))
  clojure.lang.IPersistentStack
  (peek [this] (first data))
  (pop [this] (Heap. (sift-down (subvec (swap data 0 (- (count data) 1)) 0 (- (count data) 1)))))
  )

(defmethod print-method Heap [heap writer]
  (.write writer (str "h" (.data heap))))

(defn make-heap
  "Creates a heap from a collection"
  [coll]
  (loop [[item & rest] coll
         res (->Heap (vector))]
    (if (some? item)
      (recur rest
             (conj res item))
      res)))

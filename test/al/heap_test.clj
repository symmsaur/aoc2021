(ns al.heap-test
  (:require [al.heap :refer :all]
            [clojure.test :refer :all]))

;; (defn is-heap? [v]
;;   )

(deftest make-heap-test
  (testing "Create empty"
    (let [h (make-heap [])]
      (is (= 0 (count h)))
      (is (nil? (peek h)))))
  (testing "Create with one element"
    (let [h (make-heap [7])]
      (is (= 1 (count h)))
      (is (= 7 (peek h)))))
  (testing "Create with two elements in order"
    (let [h (make-heap [1 2])]
      (is (= 2 (count h)))
      (is (= 1 (peek h)))))
  (testing "Create with two elements out of order"
    (let [h (make-heap [2 1])]
      (is (= 2 (count h)))
      (is (= 1 (peek h))))))

(deftest conj-test
  (testing "Add to empty"
    (let [h (conj (make-heap []) 15)]
      (is (= 1 (count h)))
      (is (= 15 (peek h)))))
  (testing "Add to one lower"
    (let [h (conj (make-heap [2]) 1)]
      (is (= 2 (count h)))
      (is (= 1 (peek h)))))
  (testing "Add to one higher"
    (let [h (conj (make-heap [1]) 2)]
      (is (= 2 (count h)))
      (is (= 1 (peek h))))))

(deftest pop-test
  (testing "Pop from one"
    (let [h (pop (make-heap [1]))]
      (is (= 0 (count h))))))

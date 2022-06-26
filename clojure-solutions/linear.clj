(defn abstractOperation
  "Return specified operation"
  [operation]
  (fn [& args] (apply mapv operation args)))


(def v+ (abstractOperation +))
(def m+ (abstractOperation v+))
(def c+ (abstractOperation m+))

(def v- (abstractOperation -))
(def m- (abstractOperation v-))
(def c- (abstractOperation m-))

(def v* (abstractOperation *))
(def m* (abstractOperation v*))
(def c* (abstractOperation m*))

(def vd (abstractOperation /))
(def md (abstractOperation vd))
(def cd (abstractOperation md))


(defn v*s
  ;; 1 vector n scalars
  [vec & scals]
  (mapv (fn [item] (apply * item scals)) vec))


(defn m*s
  ;; 1 matrix n scalars
  [mat & scals]
  (mapv (fn [item] (apply v*s item scals)) mat))


(defn scalar
  ;; n vectors
  [& vecs]
  (apply + (apply v* vecs)))


(defn m*v
  ;; 1 matrix n vectors
  [mat & vecs]
  (mapv (fn [item] (apply scalar item vecs)) mat))


(defn determinant
  "Return determinant for vector multiplication"
  [a00 a01 a10 a11]
  (- (* a00 a11)
     (* a01 a10)))


(defn vect
  ;; 2 vectors
  [vec1 vec2]
  (vector (determinant (nth vec1 1)
                       (nth vec1 2)
                       (nth vec2 1)
                       (nth vec2 2))
          (- (determinant (nth vec1 0)
                          (nth vec1 2)
                          (nth vec2 0)
                          (nth vec2 2)))
          (determinant (nth vec1 0)
                       (nth vec1 1)
                       (nth vec2 0)
                       (nth vec2 1))))


(defn transpose
  ;; 1 matrix
  [mat]
  (apply mapv vector mat))


(defn m*m
  ;; 2 matrices
  [mat1 mat2]
  (transpose
    (mapv (partial m*v mat1)
          (transpose mat2))))

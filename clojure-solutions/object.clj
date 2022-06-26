(declare CONST_ZERO)

(def _curr (field :CURR))


(defn getFirst
  [th]
  (nth (_curr th) 0))


(def evaluate
  (method :EVALUATE))


(def toString
  (method :TOSTRING))


(def toStringSuffix
  (method :TOSTRINGSUFFIX))


(def diff
  (method :DIFF))


(defn newExpression
  [evaluate toString toStringSuffix diff]
  (constructor
    (fn [this & _curr]
      (assoc this :CURR _curr))
    {:EVALUATE      evaluate
     :TOSTRING      toString
     :TOSTRINGSUFFIX toStringSuffix
     :DIFF          diff}))


(def Constant
  (newExpression
    (fn [this & _] (getFirst this))
    (fn [this & _] (format "%.01f" (double (getFirst this))))
    (fn [this & _] (format "%.01f" (double (getFirst this))))
    (fn [& _] CONST_ZERO)))


(def CONST_ZERO (Constant 0))


(def Variable
  (newExpression
    (fn [this args] (get args (clojure.string/lower-case (str (nth (getFirst this) 0)))))
    (fn [this] (getFirst this))
    (fn [this] (getFirst this))
    (fn [this varName] (if (not= varName (getFirst this)) CONST_ZERO (Constant 1)))))


(defn newOperation
  [operation sign derivative]
  (newExpression
    (fn [this args] (apply operation (mapv (fn [arg] (evaluate arg args)) (_curr this))))
    (fn [this] (str "(" sign " " (clojure.string/join " " (mapv toString (_curr this))) ")"))
    (fn [this] (str "(" (clojure.string/join " " (mapv toStringSuffix (_curr this))) " " sign ")"))
    (fn [this name] (apply derivative (lazy-cat (_curr this) (mapv (fn [arg] (diff arg name)) (_curr this)))))))


(def Add
  (newOperation
    +
    "+"
    #(Add %3 %4)))


(def Subtract
  (newOperation
    -
    "-"
    #(Subtract %3 %4)))


(def Negate
  (newOperation
    #(- %)
    "negate"
    #(Subtract %2)))


(def Multiply
  (newOperation
    *
    "*"
    #(Add (Multiply % %4) (Multiply %2 %3))))


(def Divide
  (newOperation
    (fn
      ([arg] (/ 1.0 (double arg)))
      ([arg1 &  args] (/ (double arg1) (double (apply * args)))))
    "/"
    #(Divide (Subtract (Multiply %2 %3) (Multiply % %4)) (Multiply %2 %2))))


(def Exp
  (newOperation
    #(Math/exp (double %))
    "exp"
    #(Multiply %2 (Exp %))))


(def Ln
  (newOperation
    #(Math/log (double %))
    "ln"
    #(Divide %2 %)))


(def OperationBank2
  {'+ Add,
   '- Subtract,
   '* Multiply,
   '/ Divide,
   'negate Negate,
   'exp Exp,
   'ln Ln})


(defn parseObject
  [inputExpression]
  ((defn expressionParser
     [e]
     (cond
       (list? e)
       (apply ((nth e 0)  OperationBank2) (mapv expressionParser (next e)))

       (symbol? e)
       (Variable (str e))

       (number? e)
       (Constant e)))
   (read-string inputExpression)))

(defn newOperation
  [operation]
  (fn [& args1]
    (fn [args2] (apply operation ((apply juxt args1) args2)))))


(def add (newOperation +))
(def subtract (newOperation -))
(def negate subtract)
(def multiply (newOperation *))
(def exp (newOperation (fn [arg] (Math/exp arg))))
(def ln (newOperation (fn [arg] (Math/log arg))))


(def divide
  (newOperation (fn
                  ([arg1 &  args] (/ (double arg1) (double (apply * args)))))))


(def OperationBank
  {'+ add,
   '- subtract,
   'negate negate,
   '* multiply,
   '/ divide,
   'exp exp,
   'ln ln})


(defn variable
  [var]
  (fn [args] (args var)))


(def constant constantly)


(defn parseFunction
  [inputExpression]
  ((defn expressionParser
     [e]
     (cond
       (list? e)
       (apply ((nth e 0)  OperationBank) (mapv expressionParser (next e)))

       (symbol? e)
       (variable (str e))

       (number? e)
       (constant e)))
   (read-string inputExpression)))

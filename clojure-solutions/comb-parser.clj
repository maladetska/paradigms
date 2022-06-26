(def *digit (+char "0123456789"))
(def *space (+char " \t\n\r"))
(def *sign (+char "/*-+"))
(def *point (+char ",."))
(def *ws (+ignore (+star *space)))
(def *letter (+char (apply str (filter #(Character/isLetter %) (mapv char (range 32 128))))))
(def left_brack (+char "("))
(def right_brack (+char ")"))


((defn +seq1
   [& ps]
   (apply +seqf (fn [& vs] (nth vs 0)) ps)))


((defn +seq2
   [& ps]
   (apply +seqf (fn [& vs] (nth vs 1)) ps)))


(defn *parentheses
  [e]
  (+seq2 *ws left_brack *ws e *ws right_brack *ws))


(def *const
  (+seqf (comp Constant read-string)
         (+str (+seq
                 (+opt *sign)
                 (+str (+star *digit))
                 *point
                 (+str (+star *digit))))))


(def *var
  (+seqf (comp (partial
                 (fn [arg]
                   (OperationBank2 arg
                                   (Variable (str arg))))) symbol)
         (+str (+plus (+or *sign *letter)))))


(def *str
  (+seqf (fn [arg] (apply (last arg) (butlast arg)))
         (*parentheses (+star (+seq1 *ws (delay (+or *const *var *str)) *ws)))))


(def *e (+seq1 *ws (delay (+or *const *var *str)) *ws))

(def parseObjectSuffix (+parser *e))

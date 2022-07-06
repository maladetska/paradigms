# paradigms-2022 
 
[Условия домашних заданий](https://www.kgeorgiy.info/courses/paradigms/homeworks.html)

[Тесты + модификации](https://github.com/maladetska/paradigms-2022/tree/master/tests)


#|Name|Сomplexity|Mod
---|---|---|---
1|Обработка ошибок||
2|[Бинарный поиск](https://github.com/maladetska/paradigms-2022/blob/master/java-solutions/search/BinarySearchMissing.java)|easy|*Missing*
3|[Очередь на массиве](https://github.com/maladetska/paradigms-2022/tree/master/java-solutions/queue)|easy|*Count*
4|[Очереди](https://github.com/maladetska/paradigms-2022/tree/master/java-solutions/queue)|easy|*CountIf*
5|Вычисление в различных типах||
6|[Функциональные выражения на JavaScript](https://github.com/maladetska/paradigms-2022/blob/master/javascript-solutions/functionalExpression.js)|easy|*Pie*
7|[Объектные выражения на JavaScript](https://github.com/maladetska/paradigms-2022/blob/master/javascript-solutions/objectExpression.js)|easy|*MinMax*
8|[Обработка ошибок на JavaScript](https://github.com/maladetska/paradigms-2022/blob/master/javascript-solutions/objectExpression.js)|easy|*Prefix*: *SinhCosh*
9|[Линейная алгебра на Clojure](https://github.com/maladetska/paradigms-2022/blob/master/clojure-solutions/linear.clj)|easy|*Cuboid*
10|[Функциональные выражения на Clojure](https://github.com/maladetska/paradigms-2022/blob/master/clojure-solutions/functional.clj)|easy|*ExpLn*
11|[Объектные выражения на Clojure](https://github.com/maladetska/paradigms-2022/blob/master/clojure-solutions/object.clj)|easy|*ExpLn*
12|[Комбинаторные парсеры](https://github.com/maladetska/paradigms-2022/blob/master/clojure-solutions/comb-parser.clj)|easy|*Variables*
13|[Простые числа на Prolog](https://github.com/maladetska/paradigms-2022/blob/master/prolog-solutions/primes.pl)|easy/hard|*Nth*/*Palindrome*
14|[Дерево поиска на Prolog](https://github.com/maladetska/paradigms-2022/blob/master/prolog-solutions/tree-map.pl)|easy/hard|*Replace*/*Floor*
15|[Разбор выражений на Prolog](https://github.com/maladetska/paradigms-2022/blob/master/prolog-solutions/expression.pl)|easy/hard|*Variables*

Домашнее задание 15. Разбор выражений на Prolog
Доработайте правило eval(Expression, Variables, Result), вычисляющее арифметические выражения.
Пример вычисления выражения 2x-3 для x = 5:
eval(
    operation(op_subtract,
        operation(op_multiply,
            const(2),
            variable(x)
        ),
        const(3)
    ),
    [(x, 5)],
    7
)
                    
Поддерживаемые операции: сложение (op_add, +), вычитание (op_subtract, -), умножение (op_multiply, *), деление (op_divide, /), противоположное число(op_negate, negate).
Простой вариант. Реализуйте правило suffix_str(Expression, Atom), разбирающее/выводящее выражения, записанные в суффиксной форме. Например,
    suffix_str(
        operation(op_subtract,operation(op_multiply,const(2),variable(x)),const(3)),
        '((2 x *) 3 -)'
    )
Сложный вариант. Реализуйте правило infix_str(Expression, Atom), разбирающее/выводящее выражения, записанные в полноскобочной инфиксной форме. Например,
    infix_str(
        operation(op_subtract,operation(op_multiply,const(2),variable(x)),const(3)),
        '((2 * x) - 3)'
    )
Правила должны быть реализованы с применением DC-грамматик.

------
01.03.02 ITMO, first year, second term

spring, 2022.

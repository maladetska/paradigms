'use strict'

const operation = func => (...operands) => (...values) => func(...operands.map(operand => operand(...values)));

const cnst = value => () => value;
const variable = name => (...values) => values[["x", "y", "z"].indexOf(name)];
const add = operation((num1, num2) => num1 + num2);
const subtract = operation((num1, num2) => num1 - num2);
const multiply = operation((num1, num2) => num1 * num2);
const divide = operation((num1, num2) => num1 / num2);
const negate = operation(num1 => -num1);

const pi = cnst(Math.PI);
const e = cnst(Math.E);

let expression = add(
    subtract(
        multiply(
            variable("x"),
            variable("x")
        ),
        multiply(
            cnst(2),
            variable("x")
        )
    ),
    cnst(1)
);

for (let i = 0; i < 11; i++) {
    println(expression(i, 0, 0));
}
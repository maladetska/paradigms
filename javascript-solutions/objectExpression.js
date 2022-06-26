'use strict'

const operationBank = new Map()
const operationBank2 = new Map()

function newExpression(toString, evaluate) {
    function Expression(...args) {
        this.args = args
    }

    Expression.prototype.toString = toString
    Expression.prototype.evaluate = evaluate
    Expression.prototype.prefix = Expression.prototype.toString

    return Expression
}

const VARIABLES = 'xyz'
const Variable = newExpression(function () {
    return this.args[0]
}, function (...values) {
    return values[VARIABLES.indexOf(this.args[0])]
})
const Const = newExpression(function () {
    return this.args.toString()
}, function () {
    return this.args[0]
})

function newOperation(symbol, variables, operation) {
    function Operation(...args) {
        this.args = args
    }

    operationBank[Operation.prototype.symbol = symbol] = Operation
    operationBank2[Operation.prototype.symbol = symbol] = [Operation.prototype.variables = variables, Operation]
    Operation.prototype.operation = operation
    Operation.prototype.toString = function () {
        return (this.args.map(o => o.toString()).join(' ')) + (' ' + this.symbol)
    }
    Operation.prototype.evaluate = function (...values) {
        return this.operation(...this.args.map(o => o.evaluate(...values)))
    }
    Operation.prototype.prefix = function () {
        return '(' + this.symbol + ' ' + this.args.map(o => o.prefix()).join(' ') + ')'
    }

    return Operation
}

const Negate = newOperation('negate', 1, arg => -arg)
const Add = newOperation('+', 2, (arg1, arg2) => arg1 + arg2)
const Subtract = newOperation('-', 2, (arg1, arg2) => arg1 - arg2)
const Multiply = newOperation('*', 2, (arg1, arg2) => arg1 * arg2)
const Divide = newOperation('/', 2, (arg1, arg2) => arg1 / arg2)
const Sinh = newOperation('sinh', 1, (arg) => Math.sinh(arg))
const Cosh = newOperation('cosh', 1, (arg) => Math.cosh(arg))
const Min3 = newOperation('min3', 3, (a1, a2, a3) => Math.min(a1, a2, a3))
const Max5 = newOperation('max5', 5, (a1, a2, a3, a4, a5) => Math.max(a1, a2, a3, a4, a5))

const parse = expression => {
    const expressionSize = expression.length
    let result = []

    for (let i = 0; i < expressionSize; i++) {
        let arg = ''
        if (expression[i] !== ' ') {
            while (i < expressionSize && expression[i] !== ' ') {
                arg += expression[i++]
            }
            const operation = operationBank[arg]
            if (VARIABLES.includes(arg)) {
                result.push(new Variable(arg))
            } else if (operation) {
                result.push(new operation(...result.splice(-operation.prototype.operation.length)))
            } else {
                result.push(new Const(parseInt(arg)))
            }
        }
    }

    return result[0]
}

function newException(mess) {
    function Exception(...args) {
        this.args = args
        this.message = mess(...args)
    }

    Exception.prototype = new Error

    return Exception
}

const ParenthesesException = newException(pos => 'Wrong number of brackets at ' + pos)
const UnexpectedSymbol = newException(pos => 'Unexpected symbol at ' + pos)
const ExpectedArg = newException(pos => 'Expected operand at ' + pos)
const ExpectedOperation = newException(pos => 'Expected operation at ' + pos)
const TooMuchArgs = newException(pos => 'Too much args at ' + pos)

function ExpressionParser(expression, expressionSize) {
    this.idx = 0
    this.expression = expression
    this.expressionSize = expressionSize
    this.nextArg = function () {
        const firstIndex = this.idx
        if (this.expression[this.idx] !== '(' && this.expression[this.idx] !== ')') {
            while (this.expression[this.idx] !== '(' && this.expression[this.idx] !== ')'
            && this.expression[this.idx] !== ' ' && this.idx < this.expressionSize) {
                this.idx++
            }
            this.arg = this.expression.slice(firstIndex, this.idx)
        } else {
            this.arg = this.expression.slice(firstIndex, ++this.idx)
        }
        while (this.expression[this.idx] === ' ') {
            this.idx++
        }
    }
    this.parse = function () {
        if (this.arg === '(') {
            this.nextArg()
            if (this.arg in operationBank2) {
                let args = []
                let curr = this.arg
                this.nextArg()
                while (this.idx < this.expressionSize && this.arg !== ')') {
                    args.push(this.parse())
                    this.nextArg()
                }

                if (operationBank2[curr][0] < args.length) {
                    throw new TooMuchArgs(this.idx)
                } else if (operationBank2[curr][0] > args.length) {
                    throw new ExpectedArg(this.idx)
                } else if (this.arg !== ')') {
                    throw new ParenthesesException(this.idx)
                } else {
                    return new operationBank2[curr][1](...args)
                }
            } else {
                throw new ExpectedOperation(this.idx)
            }
        }
        if (['x', 'y', 'z'].includes(this.arg)) {
            return new Variable(this.arg)
        } else {
            const argSize = this.arg.length
            let idx = 0
            let checkNumber = (argSize === 0) ? 0 : 1
            if (this.arg[idx] === '-') {
                checkNumber = (argSize === 1) ? 0 : 1
                idx++
            }
            for (; idx < argSize; idx++) {
                checkNumber = (this.arg[idx] < '0' || this.arg[idx] > '9') ? 0 : 1
            }
            if (checkNumber) {
                return new Const(parseInt(this.arg))
            } else {
                throw new UnexpectedSymbol(this.idx)
            }
        }
    }

    while (this.expression[this.idx] === ' ') {
        this.idx++
    }
    this.nextArg()
}

const parsePrefix = expression => {
    const expressionSize = expression.length
    const result = new ExpressionParser(expression, expressionSize)
    const ans = result.parse()
    if (result.idx < expressionSize) {
        throw new ExpectedArg(result.idx)
    }
    return ans
}

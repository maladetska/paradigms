:- load_library('alice.tuprolog.lib.DCGLibrary').


% [3_3_grammar.pl] Grammar for operation symbols [modified: + operation_negate]
operation_p(op_add) --> ['+'].
operation_p(op_subtract) --> ['-'].
operation_p(op_multiply) --> ['*'].
operation_p(op_divide) --> ['/'].
operation_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].


% Grammar for whitespaces
make_whitespace_p --> [' '].
skip_whitespaces_p --> [].
skip_whitespaces_p --> make_whitespace_p, skip_whitespaces_p.
skip_whitespaces_around_p(A) --> skip_whitespaces_p, A, skip_whitespaces_p.


% Grammar for parentheses
parentheses_p(Expression) --> ['('], Expression, [')'].


% [3_3_grammar.pl] Grammar for constants and variables (symbols only) [modified]
digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-']) },
  [H],
  digits_p(T).

variables_p([]) --> [].
variables_p([H | T]) -->
  { member(H, ['X', 'Y', 'Z', 'x', 'y', 'z']) },
  [H],
  variables_p(T).


% [3_3_grammar.pl] Grammar for constants and variables [modified]
expr_p(const(Value)) -->
    { nonvar(Value, number_chars(Value, Chars)) },
    skip_whitespaces_p,
    digits_p(Chars),
    skip_whitespaces_p,
    { Chars = [_ | _], \= (Chars, ['-']) -> number_chars(Value, Chars) }.

expr_p(variable(Name)) -->
    { nonvar(Name, atom_chars(Name, Chars)) },
    skip_whitespaces_p,
    variables_p(Chars),
    skip_whitespaces_p,
    { Chars = [_ | _], atom_chars(Name, Chars) }.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%% INFIX %%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% [3_3_grammar.pl] Grammar for constants and variables [modified]
infix_expr_p(const(Value)) --> expr_p(const(Value)).
infix_expr_p(variable(Name)) --> expr_p(variable(Name)) .


% Grammar for binary operations [modified]
infix_expr_p(operation(Op, Item1, Item2)) -->
    skip_whitespaces_around_p((
        parentheses_p((
            infix_expr_p(Item1),
            make_whitespace_p,
            operation_p(Op),
            make_whitespace_p,
            infix_expr_p(Item2))))).


% Grammar for unary operations
infix_expr_p(operation(Op, Item)) -->
    skip_whitespaces_around_p((
        operation_p(Op),
        skip_whitespaces_p,
        parentheses_p(infix_expr_p(Item)))).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%% SUFFIX %%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% [3_3_grammar.pl] Grammar for constants and variables[modified]
suffix_expr_p(const(Value)) --> expr_p(const(Value)).
suffix_expr_p(variable(Name)) --> expr_p(variable(Name)).


% Grammar for binary operations [modified]
suffix_expr_p(operation(Op, Item1, Item2)) -->
    skip_whitespaces_around_p((
        parentheses_p((
            suffix_expr_p(Item1),
            make_whitespace_p,
            suffix_expr_p(Item2),
            make_whitespace_p,
            operation_p(Op),
            skip_whitespaces_p)))).

% Grammar for unary operations
suffix_expr_p(operation(Op, Item)) -->
    skip_whitespaces_around_p((
        parentheses_p((
            suffix_expr_p(Item),
            make_whitespace_p,
            operation_p(Op),
            skip_whitespaces_p)))).
:- consult('grammar.pl').


% [1_3_lists.pl] Поиск в "ассоциативном массиве"
search(Key, [(Key, Value) | Tail], Value) :- !.
search(Key, [Head | Tail], Result) :- search(Key, Tail, Result).


% [3_3_grammar.pl]
nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).


% operation(Operation, A, B, Result).
operation(op_add, Item1, Item2, Result) :- Result is Item1 + Item2, !.
operation(op_subtract, Item1, Item2, Result) :- Result is Item1 - Item2, !.
operation(op_multiply, Item1, Item2, Result) :- Result is * (Item1, Item2), !.
operation(op_divide, Item1, Item2, Result) :- Result is / (Item1, Item2), !.
operation(op_negate, Item, Result) :- Result is -Item, !.


% evaluate(Expression, Variables, Result).
evaluate(operation(BinOperation, Item1, Item2), Variables, Result) :-
   evaluate(Item1, Variables, R1),
   evaluate(Item2, Variables, R2),
   operation(BinOperation, R1, R2, Result), !.

evaluate(operation(UnOperation, Item), Variables, Result) :-
   evaluate(Item, Variables, R),
   operation(UnOperation, R, Result), !.

evaluate(variable(Name), Variables, Result) :-
   atom_chars(Name, [Head | Tail]),
   search(Head, Variables, Result), !.

evaluate(const(Number), Variables, Number) :-
    Result is Number, !.


% [3_3_grammar.pl] Convert to string
infix_str(Expression, Atom) :-   infix_expr_str(Expression, Atom), !.
suffix_str(Expression, Atom) :- suffix_expr_str(Expression, Atom), !.


% Convert suffix form to string
suffix_expr_str(Expression, Atom) :- ground(Expression), phrase(suffix_expr_p(Expression), Chars), atom_chars(Atom, Chars).
suffix_expr_str(Expression, Atom) :-         atom(Atom), atom_chars(Atom, Chars), phrase(suffix_expr_p(Expression), Chars).


% Convert infix form to string
infix_expr_str(Expression, Atom) :- ground(Expression), phrase(infix_expr_p(Expression), Chars), atom_chars(Atom, Chars).
infix_expr_str(Expression, Atom) :-         atom(Atom), atom_chars(Atom, Chars), phrase(infix_expr_p(Expression), Chars).
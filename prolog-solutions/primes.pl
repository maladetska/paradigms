init(MAX_N) :-
        sieve(3, MAX_N), !.

prime(2) :- !.
composite(N) :-
        \+prime(N), !.

sieve(Curr, MAX_N) :-
        >= (MAX_N, Curr),
        \+compose_table(Curr),
        assert(prime(Curr)),
        (CurrCvadro is Curr * Curr, >= (MAX_N, CurrCvadro), is_composite(Curr, CurrCvadro, MAX_N)), !.

sieve(Curr, MAX_N) :-
        >= (MAX_N, Curr),
        (CurrPlus2 is Curr + 2, sieve(CurrPlus2, MAX_N)).

is_composite(Prev, Curr, MAX_N) :-
        assert(compose_table(Curr)),
        (CurrPlusPrev is Curr + Prev, >= (MAX_N, CurrPlusPrev), is_composite(Prev, CurrPlusPrev, MAX_N)), !.

search_divisors(Curr, Divisor, Divisor) :-
        A is mod(Curr, Divisor), = (0, A).
search_divisors(Curr, Divisor, Tail) :-
        A is mod(Curr, Divisor), \= (0, A),
        (DivisorPlus1 is Divisor + 1, search_divisors(Curr, DivisorPlus1, Tail)), !.

prime_divisors(Curr, []) :-
        = (Curr, 1), !.

prime_divisors(Curr, [Curr]) :-
        prime(Curr), !.

prime_divisors(Curr, []) :-
        \= (Curr, 1),
        prime(Curr), !.

prime_divisors(Curr, Curr, []) :-
        prime(Curr), !.
prime_divisors(Curr, [Curr], []) :-
        prime(Curr), !.
prime_divisors(Curr, [Curr], _) :-
        prime(Curr), !.

prime_divisors(Curr, [Head | Tail]) :-
        var(Curr),
        integer(Head),
        prime_divisors(Curr, Head, Tail), !.
prime_divisors(Curr, [Head | Tail]) :-
        integer(Curr),
        search_divisors(Curr, 2, Head),
        (CurrDevTail is Curr / Head, prime_divisors(CurrDevTail, Tail)), !.

prime_divisors(Curr, Divisor, [Head | Tail]) :-
        >= (Head, Divisor),
        prime(Divisor),
        prime_divisors(Curr1, Head, Tail), Curr is Curr1 * Divisor, !.



prime_palindrome(Curr, K) :-
        prime(Curr),
        transfer_to_kth_num_system(Curr, K, NewCurr),
        reverse(NewCurr, NewCurr).

transfer_to_kth_num_system(Curr, K, []) :-
        = (Curr, 0).
transfer_to_kth_num_system(Curr, K, [Head | Tail]) :-
        Head is mod(Curr, K),
        CurrDivK is div(Curr, K),
        transfer_to_kth_num_system(CurrDivK, K, Tail), !.



next(Prev, Curr) :-
        A is Prev + 1,
        prime(A),
        = (A, Curr), !.
next(Prev, Curr) :-
        A is Prev + 1,
        next(A, Curr), !.

nth_prime(Ind, Curr) :-
        nth_prime(Ind, Curr, 1, 2), !.
nth_prime(Ind, Curr, Ind, Prev) :-
         = (Curr, Prev), !.
nth_prime(Ind, Curr, Pos, Prev) :-
        Pos1 is Pos + 1,
        next(Prev, A),
        nth_prime(Ind, Curr, Pos1, A), !.
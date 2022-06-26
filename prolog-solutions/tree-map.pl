treap(Key, Value, Priority, Left, Right, treap(Key, Value, Priority, Left, Right)) :- !.


get_Key(treap(Key, _, _, _, _), Key) :- !.
get_Left(treap(_, _, _, Left, _), Left) :- !.
get_Right(treap(_, _, _, _, Right), Right) :- !.


map_merge(TreeMap, undefined, TreeMap) :- !.
map_merge(undefined, TreeMap, TreeMap) :- !.
map_merge(treap(Key1, Value1, Priority1, Left1, Right1),
          treap(Key2, Value2, Priority2, Left2, Right2),
          treap(Key1, Value1, Priority1, Left1, Right)) :-
                > (Priority1, Priority2),
                map_merge(Right1, treap(Key2, Value2, Priority2, Left2, Right2), Right), !.
map_merge(TreeMap,
          treap(Key2, Value2, Priority2, Left2, Right2),
          treap(Key2, Value2, Priority2, Left, Right2)) :-
                map_merge(TreeMap, Left2, Left), !.


map_split(undefined, _, undefined, undefined) :- !.
map_split(undefined, _, _, _) :- !.
map_split(treap(Key, Value, Priority, Left1, Right),
          SeparatingKey,
          TreeMap,
          treap(Key, Value, Priority, Left2, Right)) :-
                < (SeparatingKey, Key),
                map_split(Left1, SeparatingKey, TreeMap, Left2), !.
map_split(treap(Key, Value, Priority, Left, Right1),
          SeparatingKey,
          treap(Key, Value, Priority, Left, Right2),
          TreeMap) :-
                map_split(Right1, SeparatingKey, Right2, TreeMap), !.


map_get(treap(Key, Value, _, _, _), Key, Value) :- !.
map_get(TreeMap, TargetKey, Value) :-
        get_Key(TreeMap, Key), (< (TargetKey, Key) ->
        get_Left(TreeMap, Left), map_get(Left, TargetKey, Value), !;
        get_Right(TreeMap, Right), map_get(Right, TargetKey, Value), !).


map_floorKey(TreeMap, Key, FloorKey) :- find_floorKey(TreeMap, Key, undefined, FloorKey), !.
find_floorKey(TreeMap, K, End, FloorKey) :-
	    get_Key(TreeMap, Key), (< (K, Key) ->
        get_Left(TreeMap, Left), find_floorKey(Left, K, End, FloorKey), !;
        get_Right(TreeMap, Right), find_floorKey(Right, K, Key, FloorKey), !).
find_floorKey(undefined, _, FloorKey, FloorKey) :- \= (FloorKey, undefined), !.


map_put(TreeMap, NewKey, NewValue, Result) :-
        rand_float(Priority),
        treap(NewKey, NewValue, Priority, undefined, undefined, NewVertex),

        map_remove(TreeMap, NewKey, Key),
        map_split(Key, NewKey, TreeMap1, TreeMap2),
        map_merge(TreeMap1, NewVertex, TreeMap3),
        map_merge(TreeMap3, TreeMap2, Result), !.
map_put(TreeMap, (NewKey, NewValue), Result) :-
        map_put(TreeMap, NewKey, NewValue, Result), !.


map_remove(undefined, _, undefined) :- !.
map_remove(treap(DeletedKey, _, _, Left, Right), DeletedKey, Result) :-
        map_merge(Left, Right, Result), !.
map_remove(treap(Key, Value, Priority, Left1, Right1), DeletedKey, treap(Key, Value, Priority, Left2, Right2)) :-
        < (DeletedKey, Key) ->
        = (Right1, Right2), map_remove(Left1, DeletedKey, Left2), !;
        = (Left1, Left2), map_remove(Right1, DeletedKey, Right2), !.


map_build([], undefined) :- !.
map_build([], TreeMap, TreeMap) :- !.
map_build([Head | Tail], TreeMap) :-
        map_build(Tail, NextTail),
        map_put(NextTail, Head, TreeMap), !.


map_replace(TreeMap, NewKey, NewValue, Result) :- 
        map_put(TreeMap, NewKey, NewValue, Result), !.
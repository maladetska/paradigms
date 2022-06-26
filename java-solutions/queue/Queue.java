package queue;

/*
    Model q[head] ... q[tail - 1]
    Inv: ∀ i ∈ [head, tail): q[i] ≠ null && size == tail - head && size ⩾ 0
    Immutability: ∀ i ∈ [head, tail): q'[i] = q[i] && size' = size
 */
public interface Queue {

    // Pred: element ≠ null
    // Post: q[tail' = tail + 1] = element && size' = size + 1
    void enqueue(Object element);

    // Pred: size > 0
    // Post: return q[head] && Immutability
    Object element();

    // Pred: size > 0
    // Post: q'[head] == q[head' = head + 1] && size' = size - 1
    Object dequeue();

    // Pred: true
    // Post: return size && Immutability
    int size();

    // Pred: true
    // Post: size == 0 && Immutability
    boolean isEmpty();

    // Pred: true
    // Post: size == 0
    void clear();
    // :NOTE:/2 no countIf
}
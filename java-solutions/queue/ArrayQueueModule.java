package queue;

/*
    Model q[head] ... q[tail - 1]
    Inv: ∀ i ∈ [head, tail): q[i] ≠ null && size == tail - head && size ⩾ 0
    Immutability: ∀ i ∈ [head, tail): q'[i] = q[i] && size' = size
 */
public class ArrayQueueModule {
    private static final int CAPACITY = 5;

    private static int size = 0;
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[CAPACITY];

    private static void ensureCapacity() {
        if (size == elements.length) {
            final Object[] temp = new Object[2 * size];
            if (head != 0) {
                System.arraycopy(elements, 0, temp, size - head, head);
            }
            if (head < size) {
                System.arraycopy(elements, head, temp, 0, size - head);
            }
            head = 0;
            elements = temp;
        }
    }

    // Pred: element ≠ null
    // Post: q[tail = tail + 1] = element && size' = size + 1
    public static void enqueue(Object element) {
        assert element != null;

        ensureCapacity();
        tail = (head + size) % elements.length;
        elements[tail] = element;
        size++;
    }

    // Pred: size > 0
    // Post: return q[head] && Immutability
    public static Object element() {
        assert size > 0;

        return elements[head];
    }

    // Pred: size > 0
    // Post: q'[head] == q[head = head + 1] && size' = size - 1
    public static Object dequeue() {
        assert size > 0;

        final Object res = element();
        size--;
        elements[head] = null;
        head = (head + 1) % elements.length;
        return res;
    }

    // Pred: true
    // Post: return size && Immutability
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: size == 0 && Immutability
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: size == 0
    public static void clear() {
        size = head = tail = 0;
        elements = new Object[CAPACITY];
    }

    // Pred: element ≠ null
    // Post: count all q[i] : q[i] == element
    public static int count(Object element) {
        assert element != null;

        int res = 0;
        for (Object e : elements) {
            if (e != null && e.equals(element)) {
                res++;
            }
        }
        return res;
    }
}

package queue;

/*
    Model q[head] ... q[tail - 1]
    Inv: ∀ i ∈ [head, tail): q[i] ≠ null && size == tail - head && size ⩾ 0
    Immutability: ∀ i ∈ [head, tail): q'[i] = q[i] && size' = size
 */
public class ArrayQueueADT {
    private static final int CAPACITY = 2;

    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[CAPACITY];

    private static void ensureCapacity(ArrayQueueADT queue, int size) {
        if (size == queue.elements.length) {
            final Object[] temp = new Object[2 * size];
            if (queue.head != 0) {
                System.arraycopy(queue.elements, 0, temp, size - queue.head, queue.head);
            }
            if (queue.head < size) {
                System.arraycopy(queue.elements, queue.head, temp, 0, size - queue.head);
            }
            queue.head = 0;
            queue.elements = temp;
        }
    }

    // Pred: q != null && element ≠ null
    // Post: q[tail = tail + 1] = element && size' = size + 1
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert queue != null && element != null;

        ensureCapacity(queue, queue.size);
        queue.tail = (queue.head + queue.size) % queue.elements.length;
        queue.elements[queue.tail] = element;
        queue.size++;
    }

    // Pred: q != null && size > 0
    // Post: return q[head] && Immutability
    public static Object element(ArrayQueueADT queue) {
        assert queue != null && queue.size > 0;

        return queue.elements[queue.head];
    }

    // Pred: q != null && size > 0
    // Post: q'[head] == q[head = head + 1] && size' = size - 1
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue != null && queue.size > 0;

        final Object res = element(queue);
        queue.size--;
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return res;
    }

    // Pred: true
    // Post: return size && Immutability
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: true
    // Post: size == 0 && Immutability
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: true
    // Post: size == 0
    public static void clear(ArrayQueueADT queue) {
        queue.size = queue.head = queue.tail = 0;
        queue.elements = new Object[CAPACITY];
    }

    // Pred: q ≠ null && element ≠ null
    // Post: count all q[i] : q[i] == element
    public static int count(ArrayQueueADT queue, Object element) {
        assert queue != null && element != null;

        int res = 0;
        for (Object e : queue.elements) {
            if (e != null && e.equals(element)) {
                res++;
            }
        }
        return res;
    }
}

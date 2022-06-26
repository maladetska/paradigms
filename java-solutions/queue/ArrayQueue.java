package queue;

public class ArrayQueue extends AbstractQueue {
    private static final int CAPACITY = 2;

    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[CAPACITY];

    private void ensureCapacity(int size) {
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

    protected void enqueueImpl(Object element) {
        ensureCapacity(size);
        elements[tail = (head + size) % elements.length] = element;
        size++;
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected Object dequeueImpl() {
        final Object res = elementImpl();
        size--;
        elements[head] = null;
        head = (head + 1) % elements.length;
        return res;
    }

    protected void clearImpl() {
        size = head = tail = 0;
        elements = new Object[CAPACITY];
    }

    // Pred: element ≠ null
    // Post: count all q[i] : q[i] == element
    // :NOTE: copypaste
    public int count(Object element) {
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
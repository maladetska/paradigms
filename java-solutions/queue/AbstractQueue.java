package queue;

import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void enqueueImpl(final Object element);
    protected abstract Object elementImpl();
    protected abstract Object dequeueImpl();
    protected abstract void clearImpl();

    public final void enqueue(Object element) {
        assert element != null;

        size++;
        enqueueImpl(element);
    }

    public final Object element() {
        assert size > 0;

        return elementImpl();
    }

    public final Object dequeue() {
        assert size > 0;

        final Object res = element();
        size--;
        dequeueImpl();
        return res;
    }

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }


    public final void clear() {
        if (!isEmpty()) {
            clearImpl();
            size = 0;
        }
    }

    // Pred: pred ≠ null
    // Post: count all q[i] : q[i] == pred
    // :NOTE: inefficient without a possibiblity of overriding with a more efficient implementation
    public final int countIf(Predicate<Object> pred) {
        // :NOTE:/2 should be Objects.requireNotNull
        assert pred != null;

        int res = 0;
        for (int i = 0; i < size; i++) {
            final Object temp = dequeue();
            if (pred.test(temp)) {
                res++;
            }
            enqueue(temp);
        }
        return res;
    }
}



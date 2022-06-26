package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    protected void enqueueImpl(Object element) {
        if (tail != null) {
            tail = tail.next = new Node(element, null);
        } else {
            tail = head = new Node(element, null);
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected Object dequeueImpl() {
        final Object res = elementImpl();
        tail = ((head = head.next) != null) ? tail : null;
        return res;
    }

    protected void clearImpl() {
        head = tail = null;
    }

    private static class Node {
        private final Object value;
        private Node next;

        private Node(final Object value,final Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }
}

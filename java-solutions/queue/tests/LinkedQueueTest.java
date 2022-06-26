package queue.tests;

import queue.ArrayQueue;
import queue.LinkedQueue;

public class LinkedQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue("al" + i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element());
            queue.dequeue();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        fill(queue1);
        queue1.dequeue();
        queue1.enqueue("new0");
        queue1.dequeue();
        queue1.enqueue("new1");
        dump(queue1);

        ArrayQueue queue2 = new ArrayQueue();
        fill(queue2);
        queue2.clear();
        System.out.println(queue2.element());
    }
}

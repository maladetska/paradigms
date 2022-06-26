package queue.tests;

import queue.ArrayQueue;

public class ArrayQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue("elem" + i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element());
            queue.dequeue();
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        fill(queue1);
        System.out.println(queue1.count("elem" + 9));
        System.out.println(queue1.count("elem" + 8));
        queue1.dequeue();
        System.out.println(queue1.count("elem" + 9));
        System.out.println(queue1.count("elem" + 8));
        queue1.dequeue();
        dump(queue1);

        ArrayQueue queue2 = new ArrayQueue();
        fill(queue2);
        queue2.clear();
        System.out.println(queue2.element());
    }
}

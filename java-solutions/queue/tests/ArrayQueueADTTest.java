package queue.tests;

import queue.ArrayQueueADT;

public class ArrayQueueADTTest {
    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, "e" + i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " " + ArrayQueueADT.element(queue));
            ArrayQueueADT.dequeue(queue);
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        fill(queue1);
        System.out.println(ArrayQueueADT.count(queue1, "e" + 9));
        System.out.println(ArrayQueueADT.count(queue1, "e" + 10));
        System.out.println(ArrayQueueADT.count(queue1, "e" + 11));
        dump(queue1);

        ArrayQueueADT queue2 = new ArrayQueueADT();
        fill(queue2);
        ArrayQueueADT.clear(queue2);
        System.out.println(ArrayQueueADT.element(queue2));
    }
}

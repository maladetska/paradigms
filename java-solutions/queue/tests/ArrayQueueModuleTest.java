package queue.tests;

import queue.ArrayQueueModule;

public class ArrayQueueModuleTest {
    public static void fill() {
        ArrayQueueModule.enqueue("e" + 77);
        ArrayQueueModule.enqueue("e" + 66);
        ArrayQueueModule.enqueue("e" + 0);
        ArrayQueueModule.enqueue("e" + 1);
        ArrayQueueModule.dequeue();
        ArrayQueueModule.dequeue();
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("e" + 77);
        ArrayQueueModule.enqueue("e" + 88);
        ArrayQueueModule.enqueue("e" + 77);
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() +
                    " " + ArrayQueueModule.element());
            ArrayQueueModule.dequeue();
        }
    }

    public static void main(String[] args) {
        fill();
        System.out.println(ArrayQueueModule.count("elem" + 77));
        dump();
    }
}

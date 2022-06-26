package search;

public class BinarySearch {
    //  Pred (P1): args ≠ null &&
    //      args[i] ≠ null &&
    //      args.length > 0 &&
    //      ∀ i ∈ [0, args.length) : args[i] is an integer represented as a string &&
    //      ∀ i ∈ [2, args.length) : args[i] ⩾ args[i + 1] as integers
    //  Post: print result: min {i : array[i] ⩽ x}
    public static void main(final String[] args) {
        //  Pred: P1 is true
        //  Post: P1 && x == args[0] as Integer
        final int x = Integer.parseInt(args[0]);
        //  Pred: P1 is true
        //  Post: ∀ i ∈ [0, args.length) : array[i]  as Integer
        final int[] array = new int[args.length - 1];
        //  x == (args[0] as Integer) && array == int[args.length - 1]
        //  Inv: ∀ i ∈ [0, args.length)
        for (int i = 0; i < array.length; i++) {
            //  Pred: args ≠ null
            //  Post: array[i] == args[i + 1] as Integer && i' = i + 1
            array[i] = Integer.parseInt(args[i + 1]);
        }
        //  Pred (P2): array ≠ null &&
        //      array[i] ≠ null &&
        //      array.length > 0 &&
        //      (left == -1 && right == array.length) =>(left < right)
        //      ∀ i ∈ [0, array.length) : array[i] ⩾ array[i + 1]
        //  Post: print result: min {i : array[i] ⩽ x}
        System.out.println(recursiveBinarySearch(array, x, -1, array.length));
    }

    //  Pred: P2 is true
    //  Post: min {i : array[i] ⩽ x}
    private static int iterativeBinarySearch(final int[] array, final int x, int left, int right) {
        int mid;
        //  Inv: (x ∈ (array[left], array[right]]) && (left + 1 ⩽ right)
        while (left + 1 != right) {
            //  Post: left + 1 ≠ right
            mid = (left + right) / 2;
            //  Pred: left + 1 ≠ right
            //  Post: mid == (left + right) / 2
            if (x < array[mid]) {
                //  Pred: x < array[mid]
                //  Post: (left' == mid && right' == right) &&
                //      left < mid &&
                //      (right' - left' > right - left) &&
                //      x < array[left]
                left = mid;
            } else {
                //  Pred: x ⩾ array[mid]
                //  Post: (left' == left && right' == mid) &&
                //      mid ⩽ right &&
                //      (right' - left' > right - left) &&
                //      x ⩾ array[right]
                right = mid;
            }
            // Inv && (right' - left' > right - left)
        }
        //  x ∈ [array[right], array[left]) => i = right
        //  Pred: Inv &&
        //      left + 1 == right &&
        //      i = right
        //  Post: min {i : array[i] ⩽ x}
        return right;
    }

    //  Pred: P2 is true
    //  Post: min {i : array[i] ⩽ x}
    private static int recursiveBinarySearch(final int[] array, final int x, int left, int right) {
        int mid = (left + right) / 2;
        //  mid == (left + right) / 2
        if (left + 1 == right) {
            //  Pred: left + 1 == right
            //  Post: min {i : array[i] ⩽ x}
            return right;
        } else if (x < array[mid]) {
            //  Pred: P1 &&
            //  left + 1 < right &&
            //  left < mid &&
            //  (left' == mid && right' == right) &&
            //  (right' - left' > right - left) &&
            //  array[left] > x
            //  Post: min {i | array[i] <= x}
            return recursiveBinarySearch(array, x, mid, right);
        } else {
            //  Pred: P1 &&
            //  left + 1 < right &&
            //  mid < right &&
            //  (left' == left && right' == mid) &&
            //  (right' - left' > right - left) &&
            //  array[right] > x
            //  Post: min {i | array[i] <= x}
            return recursiveBinarySearch(array, x, left, mid);
        }
    }
}
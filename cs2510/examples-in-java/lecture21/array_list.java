import java.util.ArrayList;
import tester.*;

interface IFunc<T, R> {
    R apply(T t);
}

interface IFunc2<T, R> {
    R apply(T t1, T t2);
}

interface IPred<T> {
    boolean apply(T t);
}

interface IComparator<T> {
    int compare(T t1, T t2);
}


class ArrayUtils {
    // Returns a new array containing the elements of the given array
    <T> void swap(ArrayList<T> arr, int index1, int index2) {
        T oldValueAtIndex1 = arr.get(index1);
        T oldValueAtIndex2 = arr.get(index2);

        arr.set(index2, oldValueAtIndex1);
        arr.set(index1, oldValueAtIndex2);
    }

    <T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
        ArrayList<U> result = new ArrayList<U>();
        for (T t : arr) {
            result.add(func.apply(t));
        }
        return result;
    }

    <T> ArrayList<T> filter(ArrayList<T> arr, IPred<T> pred) {
        ArrayList<T> result = new ArrayList<T>();
        for (T t : arr) {
            if (pred.apply(t)) {
                result.add(t);
            }
        }
        return result;
    }

    <T> T foldr(ArrayList<T> arr, IFunc2<T, T> func, T base) {
        T result = base;
        for (T t : arr) {
            result = func.apply(result, t);
        }
        return result;
    }

    <T> T foldl(ArrayList<T> arr, IFunc2<T, T> func, T base) {
        T result = base;
        for (T t : arr) {
            result = func.apply(t, result);
        }
        return result;
    }

    <T> int findMin(ArrayList<T> arr, IComparator<T> comparator) {
        if (arr.size() == 0) {
            throw new RuntimeException("Cannot find the minimum of an empty list");
        }
        T min = arr.get(0);
        int minIdx = 0;
        for (int i = 1; i < arr.size(); i++) {
            T current = arr.get(i);
            if (comparator.compare(current, min) < 0) {
                min = current;
                minIdx = i;
            }
        }
        return minIdx;
    }

    void sort(ArrayList<Integer> arr, IComparator<Integer> comparator) {

        for (int i = 0; i < arr.size() - 1; i++) {
            int minIdx = this.findMin(arr, comparator);
            this.swap(arr, i, minIdx);
        }
    }
}


class ExampleArrayUtils {
    ArrayList<Integer> initData() {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        return arr;
    }

    void testSwap(Tester t) {
        ArrayList<Integer> arr = this.initData();

        ArrayUtils utils = new ArrayUtils();
        utils.swap(arr, 0, 4);

        t.checkExpect(arr.get(0), 5);
        t.checkExpect(arr.get(4), 1);
    }

    void testMap(Tester t) {
        ArrayList<Integer> arr = this.initData();

        ArrayUtils utils = new ArrayUtils();
        ArrayList<Integer> result = utils.map(arr, new IFunc<Integer, Integer>() {
            public Integer apply(Integer t) {
                return t * 2;
            }
        });

        t.checkExpect(result.get(0), 2);
        t.checkExpect(result.get(1), 4);
        t.checkExpect(result.get(2), 6);
        t.checkExpect(result.get(3), 8);
        t.checkExpect(result.get(4), 10);
    }

    void testFolr(Tester t) {
        ArrayList<Integer> arr = this.initData();

        ArrayUtils utils = new ArrayUtils();
        Integer result = utils.foldr(arr, new IFunc2<Integer, Integer>() {
            public Integer apply(Integer t1, Integer t2) {
                return t1 + t2;
            }
        }, 0);

        t.checkExpect(result, 15);
    }

    void testFindMin(Tester t) {
        ArrayList<Integer> arr = this.initData();

        ArrayUtils utils = new ArrayUtils();
        int result = utils.findMin(arr, new IComparator<Integer>() {
            public int compare(Integer t1, Integer t2) {
                return t1 - t2;
            }
        });

        t.checkExpect(result, 0);
    }

    void testSort(Tester t) {
        ArrayList<Integer> arr = this.initData();

        ArrayUtils utils = new ArrayUtils();
        utils.sort(arr, new IComparator<Integer>() {
            public int compare(Integer t1, Integer t2) {
                return t1 - t2;
            }
        });

        t.checkExpect(arr.get(0), 1);
        t.checkExpect(arr.get(1), 2);
        t.checkExpect(arr.get(2), 3);
        t.checkExpect(arr.get(3), 4);
        t.checkExpect(arr.get(4), 5);
    }
}

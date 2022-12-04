import java.util.ArrayList;
import tester.Tester;

class ArrayListUtils {
    boolean containsSequence(ArrayList<Integer> source, ArrayList<Integer> sequence) {
        if (sequence.size() == 0) {  // if sequence is empty, it is contained in source
            return true;
        }
        else if (source.size() == 0) {  // if source is empty, sequence is not contained in source
            return false;
        }
        else {
            boolean mathFound = false;
            int i = 0;
            while (!mathFound && i < source.size()) {
                mathFound = containsSequenceHelp(source, sequence, i);
                i += 1;
            }
            return mathFound;
        }
    }

    boolean containsSequenceHelp(ArrayList<Integer> source, ArrayList<Integer> sequence, int start) {
        if (sequence.size() > source.size() - start) {
            return false;
        }
        for (int i = 0; i < sequence.size(); i++) {
            if (!sequence.get(i).equals(source.get(start + i))) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> makeList(Integer... items) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Integer item : items) {
            result.add(item);
        }
        return result;
    }
}

class Example2ArrayListUtils {
    Example2ArrayListUtils() {
    }

    ArrayListUtils utils = new ArrayListUtils();

    void testContainsSequence(Tester t) {
        ArrayList<Integer> arr = utils.makeList(1, 2, 3, 4, 5);
        ArrayList<Integer> sec = utils.makeList( 3, 4, 5);

        t.checkExpect(utils.containsSequence(arr, sec), true);
        // test it works from the beginning
        sec = utils.makeList(1, 2, 3);
        t.checkExpect(utils.containsSequence(arr, sec), true);

        // test it works from the end
        sec = utils.makeList(3, 4, 5);
        t.checkExpect(utils.containsSequence(arr, sec), true);
    }
}
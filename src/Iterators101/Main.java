package Iterators101;

import java.util.Iterator;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create a new list
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }

        StepIterator<Integer> stepIterator = new StepIterator<Integer>(list.iterator(), 2);
        stepIterator.printAll();

    }
}

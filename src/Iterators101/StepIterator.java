package Iterators101;

import java.util.Iterator;

public class StepIterator<T> implements Iterator<T> {
    private Iterator<T> it;
    private int step;


    public StepIterator(Iterator<T> it, int step) {
        try {
            this.it = it;
            if (step <= 0) {
                throw new ExceptionClass("Step must be greater than 0");
            }
            this.step = step;
        } catch (ExceptionClass e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean hasNext() {

        int i = 0;
        for (; i < step && it.hasNext(); i++) {
            it.next();
        }
        return i == step;
    }

    @Override
    public T next() {
        T result = it.next();
        for (int i = 1; i < step && it.hasNext(); i++) {
            it.next();
        }
        return result;
    }

    @Override
    public void remove() {
        it.remove();
    }

    public void printAll() {
        while (hasNext()) {
            System.out.println(this.next());
        }
    }

}

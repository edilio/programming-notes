interface Iterator<T> {
    // Does this sequence have at least one more value?
    boolean hasNext();
    // Get the next value in this sequence
    // EFFECT: Advance the iterator to the subsequent value
    T next();
}


class ExamIterator<T> implements Iterator<T> {
    Iterator<T> iter;

    ExamIterator(Iterator<T> iter) {
        this.iter = iter;
    }

    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public T next() {
        return this.iter.next();
    }
}

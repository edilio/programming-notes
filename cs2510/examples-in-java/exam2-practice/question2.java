import tester.Tester;

interface IList<T>{
    //EFFECT: this list has the items of that list added to the end
    void append(IList<T> that);
    //helps append by returning a new rest of this list
    IList<T> appendHelp(IList<T> that);
    //computes the size of this list
    int length();
}

class ConsList<T> implements IList<T> { T first;
    IList<T> rest;
    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest; }
    //EFFECT: this list has the items of that list added to the end
    public void append(IList<T> that) { this.rest = this.rest.appendHelp(that);
    }
    //helps append by returning a new rest of this non-empty list
    public IList<T> appendHelp(IList<T> that) { this.rest = this.rest.appendHelp(that); return this;
    }
    //computes the size of this non-empty list
    public int length() {
        return 1 + this.rest.length();
    }
}

class MtList<T> implements IList<T> {
    // does not make any changes to the empty list
    public void append(IList<T> that) { return ; }
    //helps append by returning a new rest
    public IList<T> appendHelp(IList<T> that) { return that; } //computes the length of this empty list
    public int length() { return 0; }
}

class Examples {
    IList<Integer> mt = new MtList<Integer>();
    IList<Integer> ints1 = new ConsList<Integer>(1, mt);
    IList<Integer> ints2 = new ConsList<Integer>(2, new ConsList<Integer>(3, mt));
    IList<Integer> ints3 = new ConsList<Integer>(4, new ConsList<Integer>(5, mt));
    IList<Integer> ints4 = new ConsList<Integer>(6, new ConsList<Integer>(7, mt));

    // Fill in the blanks
    void testAppend(Tester t) {
        t.checkExpect(ints1.length(), 1);
        t.checkExpect(ints2.length(), 2);
        t.checkExpect(ints3.length(), 2);
        ints1.append(ints2);
        t.checkExpect(ints1.length(), 3);
        ints2.append(ints3);
        t.checkExpect(ints1.length(), 5);
        ints2.append(ints4);
        t.checkExpect(ints2.length(), 6);
        t.checkExpect(ints3.length(), 4);  // 2 from initial, 2 from append inst2
//        ints4.append(ints4);  broken because of infinite recursion
//        t.checkExpect(ints4.length(), 4);
        }
}
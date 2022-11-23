/*
       +-----------+
       | ILoRunner |<-------------------+
       +-----------+                    |
       +-----------+                    |
             / \                        |
             ---                        |
              |                         |
    ----------------------              |
    |                    |              |
+------------+    +----------------+    |
| MtLoRunner |    | ConsLoR        |    |
+------------+    +----------------+    |
+------------+  +-| Runner first   |    |
                | | ILoRunner rest |----+
                | +----------------+
                |
                v
      +----------------+
      | Runner         |
      +----------------+
      | String name    |
      | int age        |
      | int bib        |
      | boolean isMale |
      | int pos        |
      | int time       |
      +----------------+
 */

import tester.*;
import java.util.function.*;
import java.util.*;

interface IList<T> {
    <R> IList<R> map(Function<T, R> f);
    <R> R foldr(BiFunction<T, R, R> f, R base);
    <R> R foldl(BiFunction<T, R, R> f, R base);
    IList<T> filter(Predicate<T> p);
    IList<T> reverse();
    IList<T> append(IList<T> that);
    IList<T> insert(Comparator<T> comp, T t);
    IList<T> sort(Comparator<T> c);

    int length();
}

class MtList<T> implements IList<T> {
    MtList() {}

    public <R> IList<R> map(Function<T, R> f) {
        return new MtList<R>();
    }

    public <R> R foldr(BiFunction<T, R, R> f, R base) {
        return base;
    }

    public <R> R foldl(BiFunction<T, R, R> f, R base) {
        return base;
    }

    public IList<T> filter(Predicate<T> p) {
        return this;
    }

    public IList<T> reverse() {
        return this;
    }

    public IList<T> append(IList<T> that) {
        return that;
    }

    public IList<T> insert(Comparator<T> comp, T t) {
        return new ConsList<T>(t, this);
    }

    public IList<T> sort(Comparator<T> c) {
        return this;
    }

    public int length() {
        return 0;
    }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public <R> IList<R> map(Function<T, R> f) {
        return new ConsList<R>(f.apply(this.first), this.rest.map(f));
    }

    public <R> R foldr(BiFunction<T, R, R> f, R base) {
        return f.apply(this.first, this.rest.foldr(f, base));
    }

    public <R> R foldl(BiFunction<T, R, R> f, R base) {
        return this.rest.foldl(f, f.apply(this.first, base));
    }

    public IList<T> filter(Predicate<T> p) {
        if (p.test(this.first)) {
            return new ConsList<T>(this.first, this.rest.filter(p));
        }
        else {
            return this.rest.filter(p);
        }
    }

    public IList<T> reverse() {
        return this.rest.reverse().append(new ConsList<T>(this.first, new MtList<T>()));
    }

    public IList<T> append(IList<T> that) {
        return new ConsList<T>(this.first, this.rest.append(that));
    }

    public IList<T> insert(Comparator<T> comp, T t) {
        if (comp.compare(this.first, t) < 0) {
            return new ConsList<T>(this.first, this.rest.insert(comp, t));
        }
        else {
            return new ConsList<T>(t, this);
        }

    }

    public IList<T> sort(Comparator<T> c) {
        return this.rest.sort(c).insert(c, this.first);
    }

    public int length() {
        return 1 + this.rest.length();
    }
}

class RunnerIsMale implements Predicate<Runner> {
    public boolean test(Runner r) { return r.isMale; }
}

class RunnerIsFemale implements Predicate<Runner> {
    public boolean test(Runner r) { return !r.isMale; }
}

class RunnerIsInFirst50 implements Predicate<Runner> {
    public boolean test(Runner r) { return r.pos <= 50; }
}

class RunnerIsUnder40YearsOld implements Predicate<Runner> {
    public boolean test(Runner r) { return r.age < 40; }
}

class RunnerTimeComparator implements Comparator<Runner> {
    public int compare(Runner r1, Runner r2) {
        return r1.time - r2.time;
    }
}

class TotalRunnerAge implements BiFunction<Runner, Integer, Integer> {
    public Integer apply(Runner r, Integer sum) {
        return r.age + sum;
    }
}
class LUtils {
    /*
    - all the runners who are male
    - all the runners who are female
    - all the runners who start in the pack of the first 50 runners
    - all runners who finish the race in under four hours
    - all runners younger than age 40
     */
    IList<Runner> findAllMaleRunners(IList<Runner> marathon) {
        return marathon.filter(new RunnerIsMale());
    }

    IList<Runner> findAllFemaleRunners(IList<Runner> marathon) {
        return marathon.filter(new RunnerIsFemale());
    }

    IList<Runner> FindAllInFirst50(IList<Runner> marathon) {
        return marathon.filter(new RunnerIsInFirst50());
    }

    IList<Runner> findAllRunnersUnder40YearsOld(IList<Runner> marathon) {
        return marathon.filter(new RunnerIsUnder40YearsOld());
    }

    double avgAgeOfRunners(IList<Runner> marathon) {
        int totalAge = marathon.foldl(new TotalRunnerAge(), 0);
        int length = marathon.length();
        if (length == 0) {
            return 0;
        }
        else {
            return (double) totalAge / length;
        }
    }

    IList<Runner> sortRunnersByTime(IList<Runner> marathon) {
        return marathon.sort(new RunnerTimeComparator());
    }
}

class Runner {
    String name;
    int age;
    int bib;
    boolean isMale;
    int pos;
    int time;

    Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
        this.name = name;
        this.age = age;
        this.bib = bib;
        this.isMale = isMale;
        this.pos = pos;
        this.time = time;
    }

}


class ExamplesRunners {
    Runner bob = new Runner("Bob", 30, 1, true, 1, 100);
    Runner sue = new Runner("Sue", 25, 2, false, 2, 200);
    Runner joe = new Runner("Joe", 45, 3, true, 3, 300);
    Runner ann = new Runner("Ann", 30, 4, false, 4, 400);
    Runner jim = new Runner("Jim", 50, 5, true, 55, 500);

    LUtils utils = new LUtils();
    IList<Runner> mt = new MtList<Runner>();
    IList<Runner> marathon = new ConsList<Runner>(this.bob,
            new ConsList<Runner>(this.sue,
                    new ConsList<Runner>(this.joe,
                            new ConsList<Runner>(this.ann,
                                    new ConsList<Runner>(this.jim, this.mt)))));


    boolean testFindAllMaleRunners(Tester t) {
        return t.checkExpect(this.utils.findAllFemaleRunners(this.mt), this.mt) &&
                t.checkExpect(this.utils.findAllMaleRunners(this.marathon),
                        new ConsList<Runner>(this.bob,
                                new ConsList<Runner>(this.joe,
                                        new ConsList<Runner>(this.jim, this.mt))));
    }

    boolean testFindAllFemaleRunners(Tester t) {
        return t.checkExpect(this.utils.findAllFemaleRunners(this.mt), this.mt) &&
                t.checkExpect(this.utils.findAllFemaleRunners(this.marathon),
                        new ConsList<Runner>(this.sue,
                                new ConsList<Runner>(this.ann, this.mt)));
    }

    boolean testFindAllInFirst50(Tester t) {
        return t.checkExpect(this.utils.FindAllInFirst50(this.mt), this.mt) &&
                t.checkExpect(this.utils.FindAllInFirst50(this.marathon),
                        new ConsList<Runner>(this.bob,
                                new ConsList<Runner>(this.sue,
                                        new ConsList<Runner>(this.joe,
                                                new ConsList<Runner>(this.ann, this.mt)))));
    }

    boolean testFindAllRunnersUnder40YearsOld(Tester t) {
        return t.checkExpect(this.utils.findAllRunnersUnder40YearsOld(this.mt), this.mt) &&
                t.checkExpect(this.utils.findAllRunnersUnder40YearsOld(this.marathon),
                        new ConsList<Runner>(this.bob,
                                new ConsList<Runner>(this.sue,
                                        new ConsList<Runner>(this.ann, this.mt))));
    }

    boolean testSortRunnersByTime(Tester t) {
        return t.checkExpect(this.utils.sortRunnersByTime(this.mt), this.mt) &&
                t.checkExpect(this.utils.sortRunnersByTime(this.marathon),
                        new ConsList<Runner>(this.bob,
                                new ConsList<Runner>(this.sue,
                                        new ConsList<Runner>(this.joe,
                                                new ConsList<Runner>(this.ann,
                                                        new ConsList<Runner>(this.jim, this.mt))))));
    }

    boolean testLength(Tester t) {
        return t.checkExpect(this.mt.length(), 0) &&
                t.checkExpect(this.marathon.length(), 5);
    }

    boolean testTotalRunnerAge(Tester t) {
        TotalRunnerAge totalRunnerAge = new TotalRunnerAge();
        return t.checkExpect(totalRunnerAge.apply(this.bob, 0), 30) &&
                t.checkExpect(totalRunnerAge.apply(this.sue, 0), 25) &&
                t.checkExpect(totalRunnerAge.apply(this.joe, 0), 45) &&
                t.checkExpect(totalRunnerAge.apply(this.ann, 0), 30) &&
                t.checkExpect(totalRunnerAge.apply(this.jim, 0), 50) &&
                t.checkExpect(totalRunnerAge.apply(this.bob, 20), 50);
    }

    boolean testAvgAgeOfRunners(Tester t) {
        // 30 + 25 + 45 + 30 + 50 = 180
        // 180 / 5 = 36
        return t.checkInexact(this.utils.avgAgeOfRunners(this.mt), 0.0, 0.01) &&
                t.checkInexact(this.utils.avgAgeOfRunners(this.marathon), 36.0, 0.01);
    }
}

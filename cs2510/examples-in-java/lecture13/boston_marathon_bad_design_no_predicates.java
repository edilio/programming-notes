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

interface ILoRunner {
    /*
    - all the runners who are male
    - all the runners who are female
    - all the runners who start in the pack of the first 50 runners
    - all runners who finish the race in under four hours
    - all runners younger than age 40
     */
    ILoRunner findAllMaleRunners();
    ILoRunner findAllFemaleRunners();
    ILoRunner FindAllInFirst50();
    ILoRunner findAllRunnersUnder40YearsOld();
}

class MtLoRunner implements ILoRunner {
    public ILoRunner findAllMaleRunners() { return this; }
    public ILoRunner findAllFemaleRunners() { return this; }

    public ILoRunner FindAllInFirst50() { return this; }

    public ILoRunner findAllRunnersUnder40YearsOld() { return this; }
}

class ConsLoRunner implements ILoRunner {
    Runner first;
    ILoRunner rest;

    ConsLoRunner(Runner first, ILoRunner rest) {
        this.first = first;
        this.rest = rest;
    }

    public ILoRunner findAllMaleRunners() {
        if (this.first.isMaleRunner()) {
            return new ConsLoRunner(this.first, this.rest.findAllMaleRunners());
        }
        else {
            return this.rest.findAllMaleRunners();
        }
    }
    public ILoRunner findAllFemaleRunners() {
        if (this.first.isFemaleRunner()) {
            return new ConsLoRunner(this.first, this.rest.findAllFemaleRunners());
        }
        else {
            return this.rest.findAllFemaleRunners();
        }
    }

    public ILoRunner FindAllInFirst50() {
        if (this.first.isInFirst50()) {
            return new ConsLoRunner(this.first, this.rest.FindAllInFirst50());
        }
        else {
            return this.rest.FindAllInFirst50();
        }
    }

    public ILoRunner findAllRunnersUnder40YearsOld() {
        if (this.first.isUnder40YearsOld()) {
            return new ConsLoRunner(this.first, this.rest.findAllRunnersUnder40YearsOld());
        }
        else {
            return this.rest.findAllRunnersUnder40YearsOld();
        }
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

    public boolean isMaleRunner() {
        return this.isMale;
    }

    public boolean isFemaleRunner() {
        return !this.isMale;
    }

    public boolean isInFirst50() {
        return this.pos <= 50;
    }

    public boolean isUnder40YearsOld() {
        return this.age <= 40;
    }
}


class ExamplesRunners {
    Runner bob = new Runner("Bob", 30, 1, true, 1, 100);
    Runner sue = new Runner("Sue", 25, 2, false, 2, 200);
    Runner joe = new Runner("Joe", 45, 3, true, 3, 300);
    Runner ann = new Runner("Ann", 30, 4, false, 4, 400);
    Runner jim = new Runner("Jim", 50, 5, true, 55, 500);

    ILoRunner mt = new MtLoRunner();
    ILoRunner marathon = new ConsLoRunner(this.bob,
            new ConsLoRunner(this.sue,
                    new ConsLoRunner(this.joe,
                            new ConsLoRunner(this.ann,
                                    new ConsLoRunner(this.jim, this.mt)))));


    boolean testFindAllMaleRunners(Tester t) {
        return t.checkExpect(this.mt.findAllMaleRunners(), this.mt) &&
                t.checkExpect(this.marathon.findAllMaleRunners(),
                        new ConsLoRunner(this.bob,
                                new ConsLoRunner(this.joe,
                                        new ConsLoRunner(this.jim, this.mt))));
    }

    boolean testFindAllFemaleRunners(Tester t) {
        return t.checkExpect(this.mt.findAllFemaleRunners(), this.mt) &&
                t.checkExpect(this.marathon.findAllFemaleRunners(),
                        new ConsLoRunner(this.sue,
                                new ConsLoRunner(this.ann, this.mt)));
    }

    boolean testFindAllInFirst50(Tester t) {
        return t.checkExpect(this.mt.FindAllInFirst50(), this.mt) &&
                t.checkExpect(this.marathon.FindAllInFirst50(),
                        new ConsLoRunner(this.bob,
                                new ConsLoRunner(this.sue,
                                        new ConsLoRunner(this.joe,
                                                new ConsLoRunner(this.ann, this.mt)))));
    }

    boolean testFindAllRunnersUnder40YearsOld(Tester t) {
        return t.checkExpect(this.mt.findAllRunnersUnder40YearsOld(), this.mt) &&
                t.checkExpect(this.marathon.findAllRunnersUnder40YearsOld(),
                        new ConsLoRunner(this.bob,
                                new ConsLoRunner(this.sue,
                                        new ConsLoRunner(this.ann, this.mt))));
    }
}

// this is the `bad` design.  It is not extensible very well.

import tester.*;

interface IAT {
    int count();

    int countHelper();

    // To compute how many ancestors of this ancestor tree (excluding this
    // ancestor tree itself) are women older than 40 (in the current year)?
    int femaleAncOver40();

    int femaleAncOver40Helper();

    // To compute whether this ancestor tree is well-formed: are all known
    // people younger than their parents?
    boolean wellFormed();
    
    boolean wellFormedHelper(int age);

    IAT youngerIAT(IAT other);

    IAT youngerIATHelper(IAT other, int otherYOB);

    IAT youngestParent();

    IAT youngestGrandparent();

    IAT youngestAncInGen(int gen);

}
class Unknown implements IAT {
    Unknown() { }

    public int count(){
        return 0;
    }

    public int femaleAncOver40(){
        return 0;
    }

    public int countHelper() {
        return 0;
    }

    public int femaleAncOver40Helper(){
        return 0;
    }

    public boolean wellFormed() {
        return true;
    }
    public boolean wellFormedHelper(int age) {
        return true;
    }

    public IAT youngerIAT(IAT other) {
        return other;
    }

    public IAT youngerIATHelper(IAT other, int otherYOB){
        return other;
    }

    public IAT youngestParent() {
        return new Unknown();
    }

    public IAT youngestGrandparent() {
        return new Unknown();
    }

    // To compute the youngest ancestor in the given generation of this Unknown
    public IAT youngestAncInGen(int gen) {
        if (gen == 0) {
            return this;
        }
        else {
            return new Unknown();
        }
    }

}
class Person implements IAT {
    String name;
    int yob;
    boolean isMale;
    IAT mom;
    IAT dad;
    Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }

    public int count(){
        return this.mom.countHelper() + this.dad.countHelper();
    }

    public int countHelper(){
        return 1 + this.mom.countHelper() + this.dad.countHelper();
    }

    public int femaleAncOver40(){
        return this.mom.femaleAncOver40Helper() + this.dad.femaleAncOver40Helper();
    }

    boolean isFemaleOver40(){
        return !this.isMale && (2015 - this.yob) > 40;
    }

    public int femaleAncOver40Helper(){
        int fo40Count = this.mom.femaleAncOver40Helper() + this.dad.femaleAncOver40Helper();
        return this.isFemaleOver40() ? fo40Count + 1 : fo40Count;
    }

    public boolean wellFormed() {
        return this.mom.wellFormedHelper(this.age()) && this.dad.wellFormedHelper(this.age());
    }

    public boolean wellFormedHelper(int age) {
        return (this.age() > age) && this.mom.wellFormedHelper(this.age()) && this.dad.wellFormedHelper(this.age());
    }

    int age() {
        return 2015 - this.yob;
    }

    public IAT youngerIAT(IAT other) {
        return other.youngerIATHelper(this, this.yob);
    }

    public IAT youngerIATHelper(IAT other, int otherYOB) {
        return this.yob > otherYOB ? this : other;

    }

    public IAT youngestParent() {
        return this.youngestAncInGen(1);
//        return this.mom.youngerIAT(this.dad);
    }

    public IAT youngestGrandparent() {
        return this.youngestAncInGen(2);
//        return this.mom.youngestParent().youngerIAT(this.dad.youngestParent());
    }

    public IAT youngestAncInGen(int gen){
         if (gen == 0)
             return this;
         else return this.mom.youngestAncInGen(gen-1).youngerIAT(this.dad.youngestAncInGen(gen-1));

    }

    }

interface ILoString {

}
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }
}
class MtLoString implements ILoString {
    MtLoString() {
    }
}

class ExamplesAccumulator {
    ExamplesAccumulator() {}

    IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
    IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
    IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
    IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());

    IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
    IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
    IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
    IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
    IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
    IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());

    IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
    IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
    IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
    IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());

    IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
    IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);

    IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);

    boolean testCount(Tester t) {
        return
                t.checkExpect(this.andrew.count(), 16) &&
                        t.checkExpect(this.david.count(), 1) &&
                        t.checkExpect(this.enid.count(), 0) &&
                        t.checkExpect(new Unknown().count(), 0);
    }

    boolean testFemaleAncOver40(Tester t) {
        return
                t.checkExpect(this.andrew.femaleAncOver40(), 7) &&
                        t.checkExpect(this.bree.femaleAncOver40(), 3) &&
                        t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
                        t.checkExpect(this.enid.femaleAncOver40(), 0) &&
                        t.checkExpect(new Unknown().femaleAncOver40(), 0);
    }

    boolean testWellFormed(Tester t) {
        return
                t.checkExpect(this.andrew.wellFormed(), true) &&
                        t.checkExpect(new Unknown().wellFormed(), true) &&
                        t.checkExpect(
                                new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(),
                                false);
    }

//    boolean testAncNames(Tester t) {
//        return
//                t.checkExpect(this.david.ancNames(),
//                        new ConsLoString("David",
//                                new ConsLoString("Edward", new MtLoString()))) &&
//                        t.checkExpect(this.eustace.ancNames(),
//                                new ConsLoString("Eustace", new MtLoString())) &&
//                        t.checkExpect(new Unknown().ancNames(), new MtLoString());
//    }

    boolean testYoungestGrandparent(Tester t) {
        return
                t.checkExpect(this.emma.youngestGrandparent(), new Unknown()) &&
                        t.checkExpect(this.david.youngestGrandparent(), new Unknown()) &&
                        t.checkExpect(this.claire.youngestGrandparent(), this.eustace) &&
                        t.checkExpect(this.bree.youngestGrandparent(), this.dixon) &&
                        t.checkExpect(this.andrew.youngestGrandparent(), this.candace) &&
                        t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
    }
}

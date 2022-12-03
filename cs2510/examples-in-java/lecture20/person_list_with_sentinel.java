import tester.*;

class Person {
    String name;
    int phone;
    
    Person(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }
    // Returns true when the given person has the same name and phone number as this person
    boolean samePerson(Person that) {
        return this.name.equals(that.name) && this.phone == that.phone;
    }
    // Returns true when this person has the same name as a given String
    boolean sameName(String name) {
        return this.name.equals(name);
    }
    // Returns the number of this person when they have the same name as a given String
    int phoneOf(String name) {
        if (this.name.equals(name)) {
            return this.phone;
        }
        else {
            throw new RuntimeException("The given name does not match this person's name");
        }
    }
}

interface ILoPerson {
    void removePerson(String name);
    void removePersonHelp(String name, ConsLoPerson prev);
    // Returns true if this list contains a person with the given name
    boolean contains(String name);
}

// Represents a sentinel at the start, a node in the middle,
// or the empty end of a list
abstract class APersonList {
    abstract void removePersonHelp(String name, ANode prev);
    abstract boolean contains(String name);
    APersonList() { } // nothing to do here
}
// Represents a node in a list that has some list after it
abstract class ANode extends APersonList {
    APersonList rest;
    ANode(APersonList rest) {
        this.rest = rest;
    }

}

class MtLoPerson extends APersonList {
    MtLoPerson() {}

    public void removePersonHelp(String name, ANode prev) {
        // do nothing
    }
    public boolean contains(String name) {
        return false;
    }
}

class ConsLoPerson extends ANode {
    // Represents a data node in the list
    Person data;
    ConsLoPerson(Person data, APersonList rest) {
        super(rest);
        this.data = data;
    }
    void removePersonHelp(String name, ANode prev) {
        if (this.data.sameName(name)) {
            prev.rest = this.rest;
        }
        else {
            this.rest.removePersonHelp(name, this);
        }
    }

    public boolean contains(String name) {
        return this.data.sameName(name) || this.rest.contains(name);
    }
}

// Represents the dummy node before the first actual node of the list
class Sentinel extends ANode {
    Sentinel(APersonList rest) {
        super(rest);
    }
    void removePersonHelp(String name, ANode prev) {
        throw new RuntimeException("Can't try to remove on a Sentinel!");
    }

    public boolean contains(String name) {
        return this.rest.contains(name);
    }
}

class MutablePersonList {
    Sentinel sentinel;
    MutablePersonList() {
        this.sentinel = new Sentinel(new MtLoPerson());
    }

    void removePerson(String name) {
        this.sentinel.rest.removePersonHelp(name, this.sentinel);
    }

    boolean contains(String name) {
        return this.sentinel.contains(name);
    }
}

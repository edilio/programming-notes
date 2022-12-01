# Lecture's Summary

## Lecture 1: Data Definitions in Java
- Representing Data in java will be using classes
- Java doesn't java Symbols and Images like Racket.  It has `boolean`, `strings`, `numbers(int, double)`
- Classes should be title case

Naming convention: class names in Java always are written in `TitleCase`, and field names are always written in `camelCase`. Primitive type names, like int and boolean, are lowercase.

In Java, we don’t define “struct”s to describe compound data; we define classes.

1.3 Examples of Data(introducing examples data)

1.4 Anatomy of the Class Definition
(how a class is defined. Note: order matters!)

They also introduce `has a` relationship when a book has an author example(be clear of the diagram representation)

Code here is Book + Author

## Lecture 2: Data definitions: Unions

Just interfaces to group data functionality

2.1 Unions

They introduced also here how to represent that in a diagram(ex. `IStation`/`TStop`/`CommStation`)

```java
interface IStation {
}
 
// to represent a subway station
class TStop implements IStation {
  String name;
  String line;
  double price;
 
  TStop(String name, String line, double price) {
    this.name = name;
    this.line = line;
    this.price = price;
  }
}
 
// to represent a stop on a commuter line
class CommStation implements IStation {
  String name;
  String line;
  boolean express;
 
  CommStation(String name, String line, boolean express) {
    this.name = name;
    this.line = line;
    this.express = express;
  }
}
```

2.2 Defining Examples of Data(just defining examples in the examplesX class)

2.3 Enter the Tester(introducing the tester library)

2.4 Self-referential unions: Ancestor trees

```java
interface IAT{ }
 
// to represent an unknown member of an ancestor tree
class Unknown implements IAT {
  Unknown() {}
}
 
// to represent a person with the person's ancestor tree
class Person implements IAT {
  String name;
  IAT mom;
  IAT dad;
 
  Person(String name, IAT mom, IAT dad) {
    this.name = name;
    this.mom = mom;
    this.dad = dad;
  }
}
```

As you can see how to define an interface in order to then, treat the unkown, null, or the empty like in lists in next chapters


## Lecture 3: Methods for simple classes

3.1 Designing methods for simple classes
methods are like functions but for classes, so they can take parameters like funtions in racket, but also work with fields. They can consume and produce objects too

They come back with the example of books in a book store

In the process, they introduce the organization:

- The purpose statement for the class precedes the class definition, just as it would precede a struct definition in Racket.
- The class definition starts with the field declarations, followed by the constructor.
- After the constructor comes the template (described in more detail below).
- After the template comes each method definition for the class.

3.1.1 Signature and purpose
Every method definition consists of the following parts:

- A purpose statement, much as we wrote in Racket, except we will be very careful to use the pronoun this
- The type of the value returned from the function, known as the return type
- The method name, where the standard naming convention starts with a lowercase letter and uses “camelCase” to distinguish words within the name
- A parenthesized argument list, consisting of the type and name of each argument, separated by commas
- The method body, surrounded by braces; this is the code to execute when the method is invoked

3.1.2 Template

3.1.3 Method Body
(java uses infix instead of prefix operations like racket)

3.1.4 Tests(the normal checkexpect)

3.2 Aside: Evaluation of arithmetic expressions
some words of caution due to integer arithmetic + order of operations

original formula:

```java
this.price - (this.price * discount) / 100
```

They present other expressions that should be equivalent but fail for using integer.

In particular this one that is the one I use:
- this.price * (1 - discount / 100) (if discount is int => 30 / 100 = 0 so the whole formula is wrong.)

3.3 Methods for classes with containment: Designing method templates

3.3.1 Signature and purpose
```java
boolean sameAuthor(Author that) {
    return this.name.equals(that.name) &&
        this.yob == that.yob;
}
```

3.4 Methods that produce objects

They again insist in the template part also.

```java
// In Book
/* TEMPLATE:
   Fields:
   ... this.title ...         -- String
   ... this.author ...        -- String
   ... this.price ...         -- int
 
   Methods: 
   ... this.salePrice(int) ... -- int
   ... this.reducePrice() ...  -- Book
*/
Conveniently, we can reuse the method defined earlier in the body of our method as follows:

// In Book
// produce a book like this one, but with the price reduced by 20%
Book reducePrice() {
  return new Book(this.title, this.author, this.salePrice(20));
}
```


## Lecture 4: Methods for unions

4.1 Designing Methods for Simple Shapes

```java
interface IShape {
    double area();
    double distanceToOrigin();
    IShape grow(int inc);
    boolean isBiggerThan(IShape that);
}

class Circle implements IShape {
    int x;
    int y;
    int radius;
    String color;

    Circle(int x, int y, int radius, String color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public double area() {
        return Math.PI * this.radius * this.radius;
    }

    public double distanceToOrigin() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public IShape grow(int inc) {
        return new Circle(this.x, this.y, this.radius + inc, this.color);
    }

    public boolean isBiggerThan(IShape that) {
        return this.area() > that.area();
    }
}

class Square implements IShape {
    int x;
    int y;
    int size;
    String color;

    Square(int x, int y, int size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public double area() {
        return this.size * this.size;
    }

    public double distanceToOrigin() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public IShape grow(int inc) {
        return new Square(this.x, this.y, this.size + inc, this.color);
    }

    public boolean isBiggerThan(IShape that) {
        return this.area() > that.area();
    }
}
```

4.2 Designing the area method: dynamic dispatch

4.3 Dynamic dispatch

4.4 Designing the distanceToOrigin method

4.5 What’s so special about Cartesian coordinates, anyway?

4.6 Designing the isBiggerThan method

4.7 Designing the contains method

4.8 Putting it all together: Lots of Examples

The lecture is based on `Shape` examples. Starting with the simple code shown before, so they can introduce the concepts described;

## Lecture 5: Methods for self-referential lists(They introduced here the self-referential list(first, rest))

5.1 Representing lists

```java
interface ILoBook {
    int count();
    double salePrice(int discount);
    ILoBook allBefore(int y);
    ILoBook sortByPrice();

    ILoBook insert(Book b);
}

class MtLoBook implements ILoBook {
    MtLoBook() {}

    public int count() {
        return 0;
    }

    public double salePrice(int discount) {
        return 0;
    }

    public ILoBook allBefore(int y) {
        return this;
    }

    public ILoBook sortByPrice() {
        return this;
    }

    // insert the given book into this empty list of books already sorted by price
    public ILoBook insert(Book b) {
        return new ConsLoBook(b, this);
    }
}

class ConsLoBook implements ILoBook {
    Book first;
    ILoBook rest;

    ConsLoBook(Book first, ILoBook rest) {
        this.first = first;
        this.rest = rest;
    }

    public int count() {
        return 1 + this.rest.count();
    }

    public double salePrice(int discount) {
        return this.first.salePrice(discount) + this.rest.salePrice(discount);
    }

    public ILoBook allBefore(int y) {
        if (this.first.year < y) {
            return new ConsLoBook(this.first, this.rest.allBefore(y));
        }
        else {
            return this.rest.allBefore(y);
        }
    }

    public ILoBook sortByPrice() {
        return this.rest.sortByPrice().insert(this.first);
    }

    public ILoBook insert(Book b) {
        if (this.first.price < b.price) {
            return new ConsLoBook(this.first, this.rest.insert(b));
        }
        else {
            return new ConsLoBook(b, this);
        }
    }
}
```

5.2 Basic list computations

5.3 Sorting(the insertion algorithm just to illustrate the problem of sorting and self-referential algos)

Notes: Check how the code improve when they introduced `publishedBefore` and `cheaperThan`, delegating this responsibility to the `Book` class. It improves readability and testability too.

```java
    // was this book published before the given year?
    boolean publishedBefore(int year) {
        return this.year < year;
    }

    // is the price of this book cheaper than the price of the given book?
    boolean cheaperThan(Book that) {
        return this.price < that.price;
    }
```

## Lectures 6 and 7. Accumulators

When using a recursive structure, if the method we need to implement recursively have a different treatment for first or root, we need a helper function that will be recursively as expected but the real function will just call the helper with a particular value

Whenever we reach a situation where some object does not have enough information (between anything available in the template, or anything available via method parameters) to complete its task, it must delegate to a helper method — and sometimes, that helper method must be invoked on a different object. Here, some Person cannot determine if it is younger than its parents. What to do?

Try to somehow pass a different object as the accumulator, in that case the yob. Also, the best solutions was asking the parent about its child instead of the other way around


All the examples are using the ancestor tree(IAT) and the self-referential list


## Lecture 8: Practice Design

## Lecture 9: Abstract classes and inheritance(Shapes again)

9.1 Design recipe for abstractions

9.2 Lifting fields

9.3 Lifting methods: abstract methods

9.4 Concrete methods in the abstract class

9.5 Abstraction by defining a subclass()

9.6 Common interface - yes or no(we inherited from an interface, pros/cons)


## Lecture 10: Customizing constructors for correctness and convenience

10.1 Constructors with default options

Interlude 1: invoking one constructor from another(this(...), supper(...))

10.2 Interlude 2: defining constants in Java
```java
interface ITetrisPiece {
  int SCREEN_HEIGHT = 30;
}
```

10.3 Constructors that enforce data integrity: Exceptions

10.4 Interlude 3: removing redundancy with a utility class

10.5 Testing exceptions in constructors

10.6 Combining convenience with correctness

ex. 
```java 
new IllegalArgumentException("Invalid year: 53000")
```

```java
t.checkConstructorException(
     // the expected exception
     new IllegalArgumentException("Invalid year: 53000"),
     
     // the *name* of the class (as a String) whose constructor we invoke
     "Date",
 
     // the arguments for the constructor
     53000, 12, 30);
```

## Lecture 11/12: Defining sameness for complex data

Casting, type-testing, and “customized" type-testing mechanisms for checking sameness

This properties must uphold:
- *Reflexivity:* every object should be the same as itself.
- *Symmetry:* if object x is the same as object y, then y is the same as x.
- *Transitivity:* if two objects are both the same as a third object, then they are the same as each other.
- *Totality:* we can compare any two objects of the same type, and obtain a correct answer.

11.2 Review: sameness for built-in types

`==` and `.equals()`

11.3 Review: sameness of structured data

11.4 Sameness of union data: Warmup

defining `sameCircle`, `sameRectangle` instead of `sameShape`

11.5 Sameness of union data: flawed attempt #1 using “casting” and type-testing

- really trying to implement `sameShape`

11.5.1 Casting

A type cast, or just a cast, is written as ((SomeTypeName)someValue)

11.5.2 Type-testing using instanceof

## Lecture 12: Defining sameness for complex data, part 2

Using double-dispatch to test for sameness

12.1 Sameness for unions: Successful attempt using double-dispatch

```java
interface IShape {
  boolean sameShape(IShape that);
  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
  boolean sameSquare(Square that);
}
```

Every class that implements `IShape` must implement all the methods, but the default implementation of the methods is to return false.

12.2 Summarizing the double-dispatch pattern for sameness testing

```java
interface IFoo {
  boolean sameFoo(IFoo that);
 
  boolean sameX(X that);
  boolean sameY(Y that);
  boolean sameZ(Z that);
}
```

12.3 Cleaning up the code

Using a class that implements the interface and the default methods

```java
abstract class AFoo implements IFoo {
  public boolean sameX(X that) { return false; }
  public boolean sameY(Y that) { return false; }
  public boolean sameZ(Z that) { return false; }
}
class X extends AFoo {
  public boolean sameFoo(IFoo that) { return that.sameX(this); }
  public boolean sameX(X that) { ... compares two X values ... }
}
class Y extends AFoo {
  public boolean sameFoo(IFoo that) { return that.sameY(this); }
  public boolean sameY(Y that) { ... compares two Y values ... }
}
class Z extends AFoo {
  public boolean sameFoo(IFoo that) { return that.sameZ(this); }
  public boolean sameZ(Z that) { ... compares two Z values ... }
}
```

12.4 Adding more variants

Now we add a `Combo Shape` and see how easy should be to add a new variant

```java
class Combo extends AShape {
  IShape left;
  IShape right;
  
  Combo(IShape left, IShape right) {
    this.left = left;
    this.right = right;
  }
  public boolean sameCombo(Combo that) {
    return this.left.sameShape(that.left) &&
           this.right.sameShape(that.right);
  }
}
```

and add the method `sameCombo` to the interface and the default implementation to the abstract class(`AShape`)

## Lecture 13: Abstracting over behavior(Function objects)

```java
interface ILoRunner {}

class MtLoRunner implements ILoRunner {}

class ConsLoRunner implements ILoRunner {
    Runner first;
    ILoRunner rest;

    ConsLoRunner(Runner first, ILoRunner rest) {
        this.first = first;
        this.rest = rest;
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
```

For today, we are simply going to look at various groups of runners. We’d like to find out:
- all the runners who are male
- all the runners who are female
- all the runners who start in the pack of the first 50 runners
- all runners who finish the race in under four hours
- all runners younger than age 40

Later we’ll ask more complicated questions, too.

13.1 Warmup: answering the first few questions

Add functions to interface and classes.

```java
// In ILoRunner
ILoRunner findAllMaleRunners();
ILoRunner findAllFemaleRunners();

```

```java
// In MtLoRunner
public ILoRunner findAllMaleRunners() { return this; }
public ILoRunner findAllFemaleRunners() { return this; }
```

```java
// In ConsLoRunner
public ILoRunner findAllMaleRunners() {
  if (this.first.isMale) {
    return new ConsLoRunner(this.first, this.rest.findAllMaleRunners());
  }
  else {
    return this.rest.findAllMaleRunners();
  }
}
public ILoRunner findAllFemaleRunners() {
  if (!this.first.isMale) {
    return new ConsLoRunner(this.first, this.rest.findAllFemaleRunners());
  }
  else {
    return this.rest.findAllFemaleRunners();
  }
}
```

But, `this.first.isMale` is not allowed([field-of-a-field access](https://stackoverflow.com/questions/49479052/accessing-fields-of-a-class-in-java)).

Fix this again by delegation:

```java
// In Runner
public boolean isMaleRunner() { return this.isMale; }
```

And now we can rewrite our methods to use this helper instead.

```java
// In ConsLoRunner
public ILoRunner findAllMaleRunners() {
  if (this.first.isMaleRunner()) {
    return new ConsLoRunner(this.first, this.rest.findAllMaleRunners());
  }
  else {
    return this.rest.findAllMaleRunners();
  }
}
public ILoRunner findAllFemaleRunners() {
  if (!this.first.isMaleRunner()) {
    return new ConsLoRunner(this.first, this.rest.findAllFemaleRunners());
  }
  else {
    return this.rest.findAllFemaleRunners();
  }
}
```

13.2 Abstracting over behavior: Function objects

Looking at the definitions above, we can see a lot of repetitive code. Whenever we see such repetition, we know that the design recipe for abstraction tells us to find the parts of the code that differ, find the parts of the code that are the same, and separate the common parts of the code into a single shared implementation. Trying that here, we see the following common pattern:

```java
// In MtLoRunner
public ILoRunner find...() { return this; }
```

```java
// In ConsLoRunner
public ILoRunner find...() {
  if (this.first...) {
    return new ConsLoRunner(this.first, this.rest.find...());
  }
  else {
    return this.rest.find...();
  }
}
```

The only parts that differ are the precise names of the find... methods and the precise condition we test on this.first.

We want different behaviors for this test but what abstraction can we use?
- Abstract classes won’t help: they let us share field and method definitions
- Inheritance won’t help: we don’t want to define subtypes of lists that can each answer just one question, but rather one kind of list that can answer multiple questions.
- Delegation might help...but how? We’re already delegating to the Runner class, and cluttering its definition with lots of little helpers.

We need `higher-order functions`, where we can pass in the function to do the test on Runners for us. But Java doesn't have functions: it only has `classes` and `methods`.

Look at the signatures for the helper methods we defined in the Runner class: they all operate on a Runner and produce a boolean. Suppose instead of defining these helper methods as methods on the Runner class, we defined them individually as methods in helper classes. Instead of having this be the Runner, we’ll have these methods take a Runner as a parameter:

```java
class RunnerIsMale {
  boolean isMaleRunner(Runner r) { return r.isMale; }
}

class RunnerIsFemale {
  boolean isFemaleRunner(Runner r) { return !r.isMale; }
}

class RunnerIsInFirst50 {
  boolean isInFirst50(Runner r) { return r.pos <= 50; }
}
```

Not much improvement, but refactoring using an interface and changing the name if the function to apply...

```java
interface IRunnerPredicate {
  boolean apply(Runner r);
}

class RunnerIsMale implements IRunnerPredicate {
  public boolean apply(Runner r) { return r.isMale; }
}

class RunnerIsFemale implements IRunnerPredicate {
  public boolean apply(Runner r) { return !r.isMale; }
}

class RunnerIsInFirst50 implements IRunnerPredicate {
  public boolean apply(Runner r) { return r.pos <= 50; }
}
```

We name the interface `IRunnerPredicate` because it describes objects that can answer a boolean-valued question (i.e., a `predicate`) on Runners.

Refactoring the list to use `IRunnerPredicate` abstraction.

```java
// In ILoRunner
ILoRunner find(IRunnerPredicate pred);
```

```java
// In MtLoRunner
public ILoRunner find(IRunnerPredicate pred) { return this; }
```

```java
// In ConsLoRunner
public ILoRunner find(IRunnerPredicate pred) {
  if (pred.apply(this.first)) {
    return new ConsLoRunner(this.first, this.rest.find(pred));
  }
  else {
    return this.rest.find(pred);
  }
}
```

To use these new function objects, we rewrite our tests:

```java
// In Examples class
boolean testFindMethods(Tester t) {
  return
    t.checkExpect(this.list2.find(new RunnerIsFemale()),
                  new ConsLoRunner(this.joan, new MtLoRunner())) &&
    t.checkExpect(this.list2.find(new RunnerIsMale()),
                  new ConsLoRunner(this.frank,
                    new ConsLoRunner(this.bill,
                      new ConsLoRunner(this.johnny, new MtLoRunner()))));
}
```

So, in order to answer all the questions we just need to run find with a new class that implements the `IRunnerPredicate`.

If we want to answer questions we don't have a predicate yet, all we need do is define a new class implementing `IRunnerPredicate`:

```java
class FinishIn4Hours implements IRunnerPredicate {
  public boolean apply(Runner r) { return r.time < 240; }
}
```

And we just used as in the previous example.

We don’t have to modify the `Runner`, `MtLoRunner` and `ConsLoRunner` classes or the `ILoRunner` interface at all!

13.3 Compound questions

How might we find the list of all female runners younger than 40 who started in the first 50 starting positions? We could continue to define new IRunnerPredicate classes for each of these...but notice that we’ve already answered each of the component questions here. It would be a shame not to be able to reuse their implementations.

We can define a new class, AndPredicate, as follows:

```java
// Represents a predicate that is true whenever both of its component predicates are true
class AndPredicate implements IRunnerPredicate {
  IRunnerPredicate left, right;
  AndPredicate(IRunnerPredicate left, IRunnerPredicate right) {
    this.left = left;
    this.right = right;
  }
  public boolean apply(Runner r) {
    return this.left.apply(r) && this.right.apply(r);
  }
}
```

To use it:

```java
IRunnerPredicate pre = new AndPredicate(new RunnerIsMale(), new FinishIn4Hours())
```

It should be pretty easy create an or predicate too:

```java
// Represents a predicate that is true whenever one of its component predicates is true
class OrPredicate implements IRunnerPredicate {
  IRunnerPredicate left, right;
  OrPredicate(IRunnerPredicate left, IRunnerPredicate right) {
    this.left = left;
    this.right = right;
  }
  public boolean apply(Runner r) {
    return this.left.apply(r) || this.right.apply(r);
  }
}
```

## Lecture 14: Abstractions over more than one argument

14.1 Finding the final standings

Similar to Predicates, use Function objects to compare tow objects so they can be used in sorting algorithm.

```java
interface ICompareRunners {
  // Returns true if r1 comes before r2 according to this ordering
  boolean comesBefore(Runner r1, Runner r2);
}
```

```java
 class CompareByTime implements ICompareRunners {
  public boolean comesBefore(Runner r1, Runner r2) {
    return r1.time < r2.time;
  }
}
```

This wy seems very simple to sort by different attributes like age, position, etc.

Refactoring `ILoRunner`, `MtLoRunner` and `ConsLoRunner` to use the interface.

```java
interface ILoRunner {
  ILoRunner sortBy(ICompareRuners comp);
  ILoRunner insertBy(ICompareRunners comp, Runner r);
}
```

```java
// in MtLoRunner
public ILoRunner sortBy(ICompareRunners comp) { return this; }

public ILoRunner insertBy(ICompareRunners comp, Runner r) {
  return new ConsLoRunner(r, this);
}
```

```java
// in ConsLoRunner
public ILoRunner sortBy(ICompareRunners comp) {
  return this.rest.sortBy(comp).insertBy(comp, this.first);
}

public ILoRunner insertBy(ICompareRunners comp, Runner r) {
  if (comp.comesBefore(this.first, r)) {
    return new ConsLoRunner(this.first, this.rest.insertBy(comp, r));
  }
  else {
    return new ConsLoRunner(r, this);
  }
}
```

// in Runner

// No more method finishesBefore!

Now, instead of saying 
```java
marathon.sortByTime()
```
, we would instead write

```java
marathon.sortBy(new CompareByTime())
```

14.2 Three-valued comparisons

Instead of `comesBefore` returning a boolean, we want to change it to an integer so we can also know if they are tie.

```java
// To compute a three-way comparison between two Runners
interface IRunnerComparator {
  // Returns a negative number if r1 comes before r2 in this order
  // Returns zero              if r1 is tied with r2 in this order
  // Returns a positive number if r1 comes after  r2 in this order
  int compare(Runner r1, Runner r2);
}
```

(A mnemonic for the name: A comparator is something that can compare. A `IRunnerComparator` is therefore something that can compare two Runners.)
To adapt our sortBy method to use this new interface, we just need to change the use of comesBefore:

```java
// In ConsLoRunner
public ILoRunner insertBy(IRunnerComparator comp, Runner r) {
  // comp.compare will return a negative number if its first argument comes first
  if (comp.compare(this.first, r) < 0) {
    return new ConsLoRunner(this.first, this.rest.insertBy(comp, r));
  }
  else {
    return new ConsLoRunner(r, this);
  }
}
```

Modifying our CompareByTime class is also straightforward. The purpose statement for the compare method suggests we might want a three-way if-statement:

```java
class CompareByTime implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    if (r1.time < r2.time)       { return -1; }
    else if (r1.time == r2.time) { return 0;  }
    else                         { return 1;  }
  }
}
```

In this case specifically using `-1, 0 and 1` but can be also:

```java
class CompareByTime implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    return r1.time - r2.time;
  }
}
```
14.3 Finding the winner of the race, two ways

Show why `marathon.sortBy(new CompareByTime()).first` is not good.

And its variant using the interface is not so good neither.

14.3.1 The smarter way

It seems a wasteful to find the minimum of list, sorting an entire list of Runners and produce an entirely new sorted list, only to take the first item and throw the rest of the list away! If most of that information is unneeded, could we be cleverer and avoid constructing the entire list?

More generally, we might want to find the minimum runner according to any comparison, by keeping track of the minimum seen so far.

```java
// In ILoRunner
Runner findMin(IRunnerComparator comp);
```

```java
// In ConsLoRunner
public Runner findWinner() { return this.findMin(new CompareByTime()); }
```

```java
// In MtLoRunner
public Runner findMin(IRunnerComparator comp) {
  throw new RuntimeException("No minimum runner available in this list!");
}
```

When we realize that `findMin` in `ConsLoRunner` will give us problems we realized we need a helper function `findMinHelp` which will accept an accumulator too.

```java
// In ConsLoRunner
public Runner findMinHelp(IRunnerComparator comp, Runner acc) {
  if (comp.compare(acc, this.first) < 0) {
    // The accumulator is still the minimum
    return this.rest.findMinHelp(comp, acc);
  }
  else {
    // this.first comes before the accumulator
    return this.rest.findMinHelp(comp, this.first);
  }
}

public Runner findMin(IRunnerComparator comp) {
  return this.rest.findMinHelp(comp, this.first);
}
```

14.4 Computing the registration roster: sorting alphabetically

How to sort the marathon alphabetically by name?

Easy now:

```java
class CompareByName implements IRunnerComparator {
  public int compare(Runner r1, Runner r2) {
    return r1.name.compareTo(r2.name);
  }
}
```

So, `marathon.sortBy(new CompareByName())` will do it.

## Lecture 15: Abstracting over types(Generics)

15.1 The need for more abstraction

Because we need predicates and comparator for books, and runners, etc we need another abstraction. Generics(abstraction over types)

15.2 Introducing generics

The only differences between an IRunnerPredicate and an IBookPredicate are their `names`, and the `type` of the argument supplied to the apply methods:

```java
interface IBookPredicate {
  boolean apply(Book b);
}

interface IRunnerPredicate {
  boolean apply(Runner r);
}
```

Here is using generics:

```java
interface IPred<T> {
  boolean apply(T t);
}
```

Typical Java convention is to use T to be the name of an arbitrary type, and if additional type parameters are needed, they are often named U, V, or S (simply because those letters are near T in the alphabet).

We read this declaration in words as “the interface IPred of T”. The syntax <T> states that this interface is parameterized by a type, which we will name T within the definition of this interface.

15.3 Implementing generic interfaces: specialization

```java
class BookByAuthor implements IPred<Book> {
  public boolean apply(Book b) { ... }
  ...
}
```

15.4 Instantiating generic interfaces

Before it was:

```java
IBookPredicate byAuthor = new BookByAuthor(...);
```

Now:

```java
IPred<Book> byAuthor = new BookByAuthor(...);
```

So, all the refactoring should be easy to do.

15.5 Generic classes: implementing lists

The common features of all of these interfaces are pretty clear: they all represent lists of something. So we’ll define a generic interface IList<T> as follows:

```java
interface IList<T> {
  IList<T> filter(IPred<T> pred);
  IList<T> sort(IComparator<T> comp);
  int length();
  ...
}
```

How can we implement the classes? If we just write

```java
java
class MtList<T> implements IList<T> {
  public IList<T> filter(IPred<T> pred) { return this; }
  public IList<T> sort(IComparator<T> comp) { return this; }
  public int length() { return 0; }
  ...
}
```

And:

```java
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  ...
}
```

Defining methods such as filter for ConsList<T> is straightforward, as long as we remember to specify the type of the new ConsList being constructed:

```java
// In ConsList<T>
public IList<T> filter(IPred<T> pred) {
  if (pred.apply(this.first)) {
    return new ConsList<T>(this.first, this.rest.filter(pred));
  }
  else {
    return this.rest.filter(pred);
  }
}
```

It is pretty easy to refactor sort now to use the generics form too.

15.6 Generic interfaces with more than one parameter

Suppose we wanted to produce the list of all Runners’ names. Without generics, we might come up with an interface definition like this:

```java
interface IRunner2String {
  String apply(Runner r);
}
```

Obviously this only works for results being of type string. If we want the list of ages, authors in a bookstore?

Instead of creating one by one all the combinations, let’s define the following interface, that’s generic in two type parameters:

```java
interface IFunc<A, R> {
  R apply(A arg);
}
```

This interface describes function objects that take an argument of type A and return a value of type R. In Fundies I notation, this describes functions with the signature A -> R. Now we can define a class that implements this interface:

```java
class RunnerName implements IFunc<Runner, String> {
  public String apply(Runner r) { return r.name; }
}
```

As you can see, all this is for creating a way to do a map function in lists, let's try it.

```java
// In IList<T>:
<U> IList<U> map(IFunc<T, U> f);
```

The function will return `U`, the list will be of type `U` but because `U` is not being defined yet, we need to add `<U>` before the method.

```java
// In MtList<T>
public <U> IList<U> map(IFunc<T, U> f) { return new MtList<U>(); }
```

and in ConsList:

```java
// In ConsList<T>
public <U> IList<U> map(IFunc<T, U> f) {
  return new ConsList<U>(f.apply(this.first), this.rest.map(f));
}
```

So far, we successfully have created a generic list with `filter`, `sort` and `map` functions.

15.7 Digression: lists of numbers and booleans
You cannot use a list of primitive types, but you can use the wrappers `String`, `Boolean` and `Double`.

Ex.

```java
IList<Integer> ints = new ConsList<Integer>(1,
                        new ConsList<Integer>(4, new MtList<Integer>()));
```

15.8 Subtleties and challenges with generic types

They also introduced foldr as a way to calculate the result of an operation to the entire list but without being specific.

For ex. for calculate `totalPrice` for books, will be adhoc for without this approach.

This is the good implementation:

```java
// Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);  // given two arguments returns a result
}
```

```java
// In IList<T>
<U> U foldr(IFunc2<T, U, U> func, U base);
```

```java
// In MtList<T>
public <U> U foldr(IFunc2<T, U, U> func, U base) {
  return base;
}
 ```

```java
// In ConsList<T>
public <U> U foldr(IFunc2<T, U, U> func, U base) {
  return func.apply(this.first,
                    this.rest.foldr(func, base));
}
```

As you can see, pretty trivial.

now `totalPrice` is trivial too.

```java
class SumPricesOfBooks implements IFunc2<Book, Integer, Integer> {
  public Integer apply(Book b, Integer sum) {
    return b.price() + sum;
  }
}
```

and in a function object helper:

```java
// Example of using foldr and the function object to obtain the total price
class Utils {
  Integer totalPrice(IList<Book> books) {
    return books.foldr(new SumPricesOfBooks(), 0);
  }
}
```

15.8.4 Digression: Function objects in recent Java

These interfaces for `IFunc`, `IFunc2` and `IPred` are so helpful that they are already defined on `java.util.function` package but under another names.

15.9 Summary

`Predicate`, `IFunc`, `IFunc2` will help you remove a lot of boilerplate designing your code.

In next lecture, we’ll combine the material of these past four lectures to answer a seemingly simple question: How can I take an IList<IShape> and get a list of the perimeters of the shapes?

## Lecture 16: Visitors(Double dispatch)

Visitors as generic function objects over union data

Starting with:

```java
interface IShape {
    // To return the result of applying the given visitor to this IShape
    <R> R accept(IFShapeVisitor<R> visitor);
}

class Circle implements IShape {
  int x, y;
  int radius;
  String color;
  Circle(int x, int y, int r, String color) {
    this.x = x;
    this.y = y;
    this.radius = r;
    this.color = color;
  }
  <R> R accept(IFShapeVisitor<R> visitor) {
    return visitor.visitCircle(this);
  }
}

class Rect implements IShape {
  int x, y, w, h;
  String color;
  Rect(int x, int y, int w, int h, String color) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.color = color;
  }
  <R> R accept(IFShapeVisitor<R> visitor) {
    return visitor.visitRect(this);
  }
}
```

and an `IList<IShape>`

We want to obtain the list of areas of all the shapes in the list.

Obviously we could have area in every class, that is way to do it but the visitor pattern will show you another way. At first, it will seem more complicated but at the end you will notice the good parts.

You will be tented to use the `instanceof` operator and casting but that is not the way to go.

The idea is two use double dispatch to call the right method on the right object. Designing this way we will be able to add new shapes without changing the code but also design new operations without changing the code.

Let's start with the visitor interface that from a shape will return a double. It will be useful for area but also for other calculations like perimeter.

```java
// An IShapeVisitor is a function over IShapes
interface IShapeVisitor<R> extends IFunc<IShape, R> {
  R visitCircle(Circle c);
  R visitSquare(Square s);
  R visitRect(Rect r);
}
```

```java
// ShapeArea is a function object over IShapes that computes their area
class ShapeArea implements IShapeVisitor<Double> {
  // Everything from the IShapeVisitor interface:
  public Double visitCircle(Circle c) { return Math.PI * c.radius * c.radius; }
  public Double visitSquare(Square s) { return s.side * s.side; }
  public Double visitRect(Rect r) { return r.w * r.h; }
 
  // Everything from the IFunc interface:
  public Double apply(IShape s) { return s.accept(this); }
}
```

The double dispatch is done by the `accept` method in the `IShape` interface.

## Lecture 17: Mutation

Creating cyclic data, hazards of working with mutation

Books and authors example.(a book has an author and an author has a list of books, or a book in this case)

```java
// Represents authors of books
class Author {
  String first;
  String last;
  int yob;
  Book book;
  Author(String fst, String lst, int yob, Book bk) {
    this.first = fst;
    this.last = lst;
    this.yob = yob;
    this.book = bk;
  }
}
 
// Represent books
class Book {
  String title;
  int price;
  int quantity;
  Author author;
  Book(String title, int price, int quantity, Author ath) {
    this.title = title;
    this.price = price;
    this.quantity = quantity;
    this.author = ath;
  }
}
```


One way to create a cyclic data is to create an author with no book, then create a book with that author, and then set the book to the author.

```java
Author knuth = new Author("Donald", "Knuth", 1938, null);
Book artOfComputerProgramming = new Book("The Art of Computer Programming", 100, 10, knuth);
knuth.book = artOfComputerProgramming;
```

Syntactically, this is very similar to how we initialize fields in the constructors of objects. But don’t be fooled: assignment statements are very different! Initializing fields lets us “define the field” to be equal to the given value, and in that sense initializations are at least somewhat like mathematical equations that assert two things to be equal. But assignment statements do not assert such an equality — in the assignment statement above, we know that knuth.book is equal to null! Assignments change the meaning of the variable on the left hand side, for the rest of the program...or at least until the next assignment to that same variable changes its meaning again.

17.5 Interlude: local variables

What are local variables?

17.6 Interlude: Statements versus expressions

17.7 Warning: Side effects may vary

The net effect of an assignment statement, namely a change to a variable, is known as `mutation`. Since statements on their own do not evaluate to values, the only way we can observe what they’ve done is by their `side effects`. This has some fairly drastic consequences for our programs.

17.7.3 Non-testable code

Example of mutation creating a code difficult to test because of the side effects.

```java
class Counter {
  int val;
  Counter() {
    this(0);
  }
  Counter(int initialVal) {
    this.val = initialVal;
  }
  int get() {
    int ans = this.val;
    this.val = this.val + 1;
    return ans;
  }
}
```

17.8 Discussion

With all these potential hazards, what are mutation and side effects actually good for? Let’s not forget that it is essential for creating these cyclic data structures, and we’ll see that cyclic data comes up naturally over and over again.

Additionally, side effects are very useful when interacting with the rest of the world (i.e., the part outside the computer). Once words appear on the screen and the user has read them, they can’t be un-read; once a user has sent an email, it can’t be un-sent. Side effects let us describe these changes to the state of the world. We’ll see more examples of dealing with these kinds of side effects later.


## Lecture 18: Mutation inside structures

They introduced `void` and the idea that all mutations are done through `void` methods.

Instead of having a `set` method, we have a `void` method that changes the state of the object.

They also refactored the constructor to avoid passing `null` to the constructor.

The method allows is not only do a mutation but also check if the mutation is valid, raising an `Exception` if it is not.

```java 
// In Author
// EFFECT: modifies this Author's book field to refer to the given Book
void updateBook(Book b) {
  if (this.book != null) {
    throw new RuntimeException("trying to add second book to an author");
  }
  else {
    this.book = b;
  }
}
```
 
Finally, they added a list of books to the author so finally we have a cyclic(indirect) data that represents better the real world.

```java
class Book {
  String title;
  int price;
  int quantity;
  Author author;
  Book(String title, int price, int quantity, Author ath) {
    this.title = title;
    this.price = price;
    this.quantity = quantity;
    this.author = ath;
    // NEW! Fix up the author for us, using *this* newly-constructed Book
    this.author.updateBook(this);
  }
}
```

```java
class Author {
  String first;
  String last;
  int yob;
  IList<Book> books;
  Author(String fst, String lst, int yob) {
    this.first = fst;
    this.last = lst;
    this.yob = yob;
    this.books = new MtList<Book>();
  }
}

// EFFECT: modifies this Author's book field to refer to the given Book
void addBook(Book b) {
  if (!b.author.sameAuthor(this)) {
    throw new RuntimeException("book was not written by this author");
  }
  else {
    this.books = new ConsList<Book>(b, this.books);
  }
}
```

Now that they taught us `void` they say there is no need for test functions to return boolean values.

## Lecture 19: Mutation, aliasing and testing

Sharing objects between multiple data structures, modifying fields of aliased objects, modifying list structures.

19.1 Introduction

Phone books: were used to maintain lists of people and their phone numbers. Often, people maintained several such phone books
- one for family
- one for friends
- one for colleagues, etc.

Any given contact might appear in multiple lists.

19.2 Phone lists, take 1

```java
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
```

Examples: Construct a few examples of people and phone lists.

```java

class ExamplePhoneLists {
  Person anne = new Person("Anne", 1234);
  Person bob = new Person("Bob", 3456);
  Person clyde = new Person("Clyde", 6789);
  Person dana = new Person("Dana", 1357);
  Person eric = new Person("Eric", 12469);
  Person frank = new Person("Frank", 7294);
  Person gail = new Person("Gail", 9345);
  Person henry = new Person("Henry", 8602);
  Person irene = new Person("Irene", 91302);
  Person jenny = new Person("Jenny", 8675309);
 
  ILoPerson friends, family, work;
  void initData() {
    this.friends =
      new ConsLoPerson(this.anne, new ConsLoPerson(this.clyde,
        new ConsLoPerson(this.gail, new ConsLoPerson(this.frank,
          new ConsLoPerson(this.jenny, new MtLoPerson())))));
    this.family =
      new ConsLoPerson(this.anne, new ConsLoPerson(this.dana,
        new ConsLoPerson(this.frank, new MtLoPerson())));
    this.work =
      new ConsLoPerson(this.bob, new ConsLoPerson(this.clyde,
        new ConsLoPerson(this.dana, new ConsLoPerson(this.eric,
          new ConsLoPerson(this.henry, new ConsLoPerson(this.irene,
            new MtLoPerson()))))));
  }
}
```

19.3 Phone lists, take 2

19.4 Aliasing, or, what’s in a name?

Aliasing: two different names for the same object. In Java, this is done by assigning one variable to another.

19.5 Aliasing, mutation and equality: two notions of equality

They show some other examples of aliasing.

## Lecture 20: Mutable data structures

Removing items from a `list`, `sentinels`, and `wrappers`

They continue with the phone book example.

Once again, we’ll start with the non-generic ILoPerson type to focus on the essential new aspects of the problem; generalizing to `IList<Person>` is straightforward.

20.1 Removing items from a list: the setup

They start with the test, basically, following the recipe of side effects methods.
- Check that list contains the item,
- Remove it
- Check that it is not there anymore.

20.2 Removing a person by name, part 1

Realising we need a helper function like the ones studied on accumulator chapters.

```java
// In ILoPerson
void removePerson(String name);
void removePersonHelp(String name, ConsLoPerson prev);
```

```java
// In MtLoPerson
void removePerson(String name) { return; }
void removePersonHelp(String name, ConsLoPerson prev) { return; }
```

```java
// In ConsLoPerson
void removePersonHelp(String name, ConsLoPerson prev) {
  if (this.first.name.equals(name)) {
    prev.rest = this.rest; // Modify the previous node to bypass this node
  }
  else {
    this.rest.removePersonHelp(name, this); // this is the previous node of this.rest
  }
}

void removePerson(String name) {
  this.rest.removePersonHelp(name, this);
}
```

20.3 Aliasing, again, and removing items from a list

20.5 Revising our data structure: Introducing sentinels

This particular technique of introducing an extra object between what we have (the variable friends) and what we want (the data in the list) is called adding a layer of indirection. In courses on algorithms and data structures, you will see many, many more examples of using indirection to solve problems that seem difficult or impossible, otherwise.

`Sentinels`: a special object that is used to mark the beginning or end of a list.
This leads to the following tentative class design:
```java
+------------------------------------------------+
| ILoPerson                                      |
+------------------------------------------------+
| void removePerson(String name)                 |
| void removePersonHelp(String name, ANode prev) |
+------------------------------------------------+
               /_\                     /_\
                |                       |
       +----------------+               |
       | ANode          |               |
       +----------------+               |
       | ILoPerson rest |               |
       +----------------+               |
         /_\       /_\                  |
          |         |                   |
     +----+         |                   |
     |              |                   |
+----------+    +--------------+  +------------+
| Sentinel |    | ConsLoPerson |  | MtLoPerson |
+----------+    +--------------+  +------------+
+----------+    | Person data  |  +------------+
                +--------------+
```

20.6 Revising our data structure: Introducing wrappers

Introducing the idea of a wrapper is the second time we have encountered a data definition where not every part of the data definition is dedicated to the purpose of holding data.
So our new, final class design for mutable person lists is:

```java
         +---------------------------------------+
         | MutablePersonList                     |
         +---------------------------------------+
         | void removePerson(String name)        |
         | void addPerson(String name, int num)  |
 +-------- Sentinel sentinel                     |
 |       +---------------------------------------+
 |
 |      +------------------------------------------------+
 |      | APersonList                                    |
 |      +------------------------------------------------+
 |      | void removePersonHelp(String name, ANode prev) |
 |      +------------------------------------------------+
 |                  /_\              /_\
 |                   |                |
 |         +------------------+       |
 |         | ANode            |       |
 |         +------------------+       |
 |         | APersonList rest |       |
 |         +------------------+       |
 |           /_\   /_\                |
 |            |     |                 |
 |   +--------+     |                 |
 V   |              |                 |
+----------+    +--------------+  +------------+
| Sentinel |    | ConsLoPerson |  | MtLoPerson |
+----------+    +--------------+  +------------+
+----------+    | Person data  |  +------------+
                +--------------+
```

20.6.1 Implementing the nodes of the list

20.8 Generalizing from MutablePersonLists to mutable lists of arbitrary data

```java
interface IMutableList<T> {
  // adds an item to the (front of) the list
  void addToFront(T t);
  // adds an item to the end of the list
  void addToEnd(T t);
  // removes an item from list (uses intensional equality)
  void remove(T t);
  // removes the first item from the list that passes the predicate
  void remove(IPred<T> whichOne);
  // gets the numbered item (starting at index 0) of the list
  T get(int index);
  // sets (i.e. replaces) the numbered item (starting at index 0) with the given item
  void set(int index, T t);
  // inserts the given item at the numbered position
  void insert(int index, T t);
  // returns the length of the list
  int size();
}
```

## Lecture 21: ArrayLists

Working with Java’s built-in mutable lists

21.1 Introducing ArrayLists

```java
interface IMutableList<T> {
  // adds an item to the (front of) the list
  void addToFront(T t);
  // adds an item to the end of the list
  void addToEnd(T t);
  // removes an item from list (uses intensional equality)
  void remove(T t);
  // removes the first item from the list that passes the predicate
  void remove(IPred<T> whichOne);
  // gets the numbered item (starting at index 0) of the list
  T get(int index);
  // sets (i.e. replaces) the numbered item (starting at index 0) with the given item
  void set(int index, T t);
  // inserts the given item at the numbered position
  void insert(int index, T t);
  // returns the length of the list
  int size();
}
```

This interface does not actually exist, instead, Java defines a class for us that provides essentially these (and other) methods anyway: the `ArrayList<T>` class.

```java
import java.util.ArrayList;
```

Unlike our `IList<T>` and its attendant `ConsList<T>` and `MtList<T>` classes:
- we do not control the implementation of `ArrayList<T>`
- we cannot add methods to it
- we cannot use dynamic dispatch to recur over its structure
- we actually have no visibility into how the class is implemented at all. And we shouldn’t have to!

It suffices to know that we can manipulate an `ArrayList<T>` using its methods, rather than knowing how those methods are implemented.

21.2 Obtaining items from an ArrayList

Use `get` method but remember that the index starts at 0 and if you try to get a value that is not in the list, you will get an `IndexOutOfBoundsException`.

21.3 Manipulating items via indices: moving two items

Example of how to swap two items in an `ArrayList<T>`.

```java
class ArrayUtils {
    // In ArrayUtils
    <T> void swap(ArrayList<T> arr, int index1, int index2) {
      T oldValueAtIndex1 = arr.get(index1);
      T oldValueAtIndex2 = arr.get(index2);
      
      arr.set(index2, oldValueAtIndex1);
      arr.set(index1, oldValueAtIndex2);
    }
}
```

21.4 Transforming ArrayLists with map: introducing for-each loops

21.4.1 Mapping via recursion first

```java
// In ArrayUtils
// Computes the result of mapping the given function over the source list
// from the given current index to the end of the list, and returns the
// given destination list
// EFFECT: modifies the destination list to contain the mapped results
<T, U> ArrayList<U> mapHelp(ArrayList<T> source, IFunc<T, U> func,
                            int curIdx, ArrayList<U> dest) {
  if (curIdx >= source.size()) {
    return dest;
  }
  else {
    dest.add(func.apply(source.get(curIdx)));
    return this.mapHelp(source, func, curIdx + 1, dest);
  }
}

<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
  ArrayList<U> result = new ArrayList<U>();
  return this.mapHelp(arr, func, 0, result);
}
```

21.4.2 Using for-each loops

```java
// In ArrayUtils
<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
  ArrayList<U> result = new ArrayList<U>();
  for (T t : arr) {
    result.add(func.apply(t));
  }
  return result;
}
```

Exercise

Implement `foldr` and `foldl` for ArrayLists, first using recursion and then again using for-each loops. (Hint: one of these will be much harder than the other, when using for-each loops.)

## Lecture 22: ArrayLists

Binary search over sorted ArrayLists, sorting ArrayLists

22.1 Finding an item in an arbitrary ArrayList

```java
// In ArrayUtils
// Returns the index of the first item passing the predicate,
// or -1 if no such item was found
<T> int find(ArrayList<T> arr, IPred<T> whichOne) {
  return findHelp(arr, whichOne, 0);
}

// Returns the index of the first item passing the predicate at or after the
// given index, or -1 if no such such item was found
<T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
  if (index >= arr.size()) {
    return -1;
  }
  else if (whichOne.apply(arr.get(index)) {
    return index;
  }
  else {
    return findHelp(arr, whichOne, index + 1);
  }
}
```

Not sure why they don't use the for each loop here returning once `whichOne.apply` returns `true`.

This one in particular:
```java
<T> int find(ArrayList<T> arr, IPred<T> whichOne) {
  int index = 0;
  for (T t : arr) {
    if (whichOne.apply(t)) {
      return index;
    }
    index = index + 1;
  }
  return index;
}
```

22.2 Finding an item in a sorted ArrayList – version 1

Assuming String list for simplicity, so we can relate the sorting to sorting a dictionary.

```java
/ Returns the index of the target string in the given ArrayList, or -1 if the string is not found
// Assumes that the given ArrayList is sorted aphabetically
int binarySearch(ArrayList<String> strings, String target) {
  return binarySearchHelp(strings, target, 0, strings.size() - 1);
}

// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
// Assumes that the given ArrayList is sorted aphabetically
int binarySearchHelp_v1(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
  int midIdx = (lowIdx + highIdx) / 2;
  if (lowIdx > highIdx) {
    return -1;                                                           // not found
  }
  else if (target.compareTo(strings.get(midIdx)) == 0) {
    return midIdx;                                                       // found it!
  }
  else if (target.compareTo(strings.get(midIdx)) > 0) {
    return this.binarySearchHelp_v1(strings, target, midIdx + 1, highIdx); // too low
  }
  else {
    return this.binarySearchHelp_v1(strings, target, lowIdx, midIdx - 1); // too high
  }
}
```


22.3 Finding an item in a sorted ArrayList – version 2

All those adding and subtracting 1s from the indices are tricky to get right, and if we miss even one of them, our code could loop indefinitely. Perhaps there’s a cleaner, less brittle way we could organize our code to avoid these.

Because lowIdx and highIdx are inclusive bounds, they represent a closed interval. It is difficult to split `closed intervals`.

It’s easy to split a `semi-open interval` in two: we can split an interval [𝑙,ℎ) into [𝑙,𝑚) and [𝑚,ℎ), for any 𝑙≤𝑚≤ℎ, and it’s straightforward to check that both smaller intervals contain all the values of the original interval, and that the smaller intervals do not overlap.

```java
// In ArrayUtils
// Returns the index of the target string in the given ArrayList, or -1 if the string is not found
// Assumes that the given ArrayList is sorted aphabetically
// Assumes that [lowIdx, highIdx) is a semi-open interval of indices
int binarySearchHelp_v2(ArrayList<String> strings, String target, int lowIdx, int highIdx) {
  int midIdx = (lowIdx + highIdx) / 2;
  if (lowIdx >= highIdx) {
    return -1;                                                           // not found
  }
  else if (target.compareTo(strings.get(midIdx)) == 0) {
    return midIdx;                                                       // found it!
  }
  else if (target.compareTo(strings.get(midIdx)) > 0) {
    return this.binarySearchHelp_v2(strings, target, midIdx + 1, highIdx); // too low
  }
  else {
    return this.binarySearchHelp_v2(strings, target, lowIdx, midIdx);     // too high
  }
}

int binarySearch_v2(ArrayList<String> strings, String target) {
  return this.binarySearchHelp_v2(strings, target, 0, strings.size());
}
```

22.4 Generalizing to arbitrary element types

For completeness, here is the version of binarySearch that works for arbitrary element types. Our signature gets slightly more complicated, but the logic behind the index computations and comparisons remains the same:

```java
// In ArrayUtils
<T> int gen_binarySearch_v2(ArrayList<T> arr, T target, IComparator<T> comp) {
  return this.gen_binarySearchHelp_v2(arr, target, comp, 0, arr.size());
}
<T> int gen_binarySearchHelp_v2(ArrayList<T> arr, T target, IComparator<T> comp,
                                int lowIdx, int highIdx) {
  int midIdx = (lowIdx + highIdx) / 2;
  if (lowIdx >= highIdx) {
    return -1;
  }
  else if (comp.compare(target, arr.get(midIdx)) == 0) {
    return midIdx;
  }
  else if (comp.compare(target, arr.get(midIdx)) > 0) {
    return this.gen_binarySearchHelp_v2(arr, target, comp, midIdx + 1, highIdx);
  }
  else {
    return this.gen_binarySearchHelp_v2(arr, target, comp, lowIdx, midIdx);
  }
}
```

22.5 Sorting an ArrayList

They introduced the `counted-for loop`

Exercise

Practice using the counted-for loop: design a method

```java
<T> ArrayList<T> interleave(ArrayList<T> arr1, ArrayList<T> arr2) {
  ArrayList<T> result = new ArrayList<T>();
  for (int i = 0; i < arr1.size(); i = i + 1) {
    result.add(arr1.get(i));
    result.add(arr2.get(i));  
  }
  return result;
}

```

that takes two ArrayLists of the same size, and produces an output ArrayList consisting of one item from arr1, then one from arr2, then another from arr1, etc.

Design a method

```
java<T> ArrayList<T> unshuffle(ArrayList<T> arr)
```

that takes an input ArrayList and produces a new list containing the first, third, fifth ... items of the list, followed by the second, fourth, sixth ... items.

22.6 Finding the minimum value in an ArrayList

```java
<T> T min(ArrayList<T> arr, IComparator<T> comp) {
    if arr.size() == 0 {
        throw new IllegalArgumentException("min: empty list");
    }
    T minSoFar = arr.get(0);
    for (T t : arr) {
        if (comp.compare(t, minSoFar) < 0) {
            minSoFar = t;
        }
    }
    return minSoFar;
}
```

## Lecture 23: For-each loops and Counted-for loops

Comparing and contrasting for-each loops and counted-for loops; a note about aliasing, parameters, and local variables

23.1 Warmup: build-list

Suppose we want to implement a method akin to Racket’s build-list function: it should take a number 𝑛 and a function object, and produce an ArrayList that results from invoking that function object on all numbers from 0 to 𝑛−1.

```java
// In ArrayUtils
// Returns an ArrayList of size n, where the ith element is f(i)
<U> ArrayList<U> buildList(int n, IFunc<Integer, U> func) {
  ArrayList<U> result = new ArrayList<U>();
  for (int i = 0; i < n; i = i + 1) {
    result.add(func.apply(i));
  }
  return result;
}
```

```java
(Note how similar this code is to our implementation of map:
// In ArrayUtils
<T, U> ArrayList<U> map(ArrayList<T> arr, IFunc<T, U> func) {
  ArrayList<U> result = new ArrayList<U>();
  for (T t : arr) {
    result.add(func.apply(t));
  }
  return result;
}
```

Why use counted-for loops instead of for-each loops?

23.2 Loops, Aliasing and Variables

First, let’s consider modifying each book in the array-list so that its title is entirely in uppercase letters. We’ll want to use the toUpperCase() method on Strings to accomplish this.

```java
// In ArrayUtils
// EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles
void capitalizeTitles_bad(ArrayList<Book> books) {
  for (Book b : books) {
    b = new Book(b.title.toUpperCase(), b.author);
  }
}
```

What goes wrong with this approach? 

The problem is that the variable b is an alias for the element of the ArrayList. When we assign b to a new Book, we’re not modifying the element of the ArrayList; we’re just changing the value of the local variable b. The element of the ArrayList is still the same old Book, with the same old title.

The good solution would be using delegation:

```java
// In ArrayUtils
// EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles
void capitalizeTitles_good(ArrayList<Book> books) {
  for (Book b : books) {
    b.capitalizeTitle();
  }
}
 
// In Book
// EFFECT: Capitalizes this book's title
void capitalizeTitle() {
  this.title = this.title.toUpperCase();
}
```

The moral of this example is a subtle but important lesson in the differences between `references` and `variables`: when we “pass a variable to a method”, we actually do no such thing at all! Instead, we pass the value of that variable to the method, and that value just might be a reference to an object. If so, inside the method we bind that reference to the parameter of the method, and obtain an alias to the original object, completely independent of the reference that was in the original variable.

23.3 Adding and removing items from lists

Above we claimed that trying to remove an item from an ArrayList and add a new one, while iterating over that list, is a dangerous idea.

Bu here is another implementation of capitalizeTitles, which does not use delegation, but instead `insert` method to replace the new book with the title capitalized.

```java
// In ArrayUtils
// EFFECT: Modifies all the books in the given ArrayList, to capitalize their titles
void capitalizeTitles_ok(ArrayList<Book> books) {
  for (int i = 0; i < books.size(); i = i + 1) {
    // get the old book...
    Book oldB = books.get(i);
    // ... construct the new book ...
    Book newB = new Book(oldB.title.toUpperCase(), oldB.author);
    // and set it in place of the old book, at the current index
    books.set(i, newB);
  }
}
```

Because we are counting indices, rather than iterating over the contents of the ArrayList directly, we are manually managing the details of which item is “next”, rather than letting the for-each loop manage that for us.

## Lecture 24: While loops

While loops for unbounded iteration

24.1 While loops

A while loop simply repeats a block of code for as long as its termination condition remains true:

```java
while (someBooleanExpression()) {
  ...body of loop...
}
```

24.1.1 Translating a counted-for loop into a while loop

```java
int idxVar = start;
while (idxVar < end) {
  ...body...
  idxVar = idxVar + 1;
}
```

Consider the following mathematical function, defined on positive integers:
𝑓(𝑛)={
𝑛/2  when 𝑛 is even
3𝑛+1 when 𝑛 is odd
}

or any given number, say 9, compute 𝑓(9), 𝑓(𝑓(9)), etc. The puzzle is this: Will this process always reach 1 for every value of 𝑛? In this example.

```java
// In some Utils class
boolean getsToOne(int n) {
  while (n > 1) {
    if (n % 2 == 0) {
      n = n / 2;
    }
    else {
      n = 3 * n + 1;
    }
  }
  return true;
}
```

24.1.3 Big-bang

How does the big-bang library work? Conceptually, it takes an initial world and invokes the makeImage method to create an image from it, then invokes the onTick method to get the next work, and then repeats these two steps until the worldEnds method returns true. This sounds like looping behavior, but it cannot possibly be a counted-for loop, since the game can last an indefinite amount of time, and we’re not counting any particular index, but rather updating a world from one value to the next. This is a perfect use of a while loop. In fact, this style of using a while loop to repeatedly handle events (such as keypresses or mouse clicks) is at the core of almost every operating system, browser, game console, or other system that deals with interactivity.

24.2 Discussion

When should we use each of these three loop types? After all, if every for-each loop over ArrayLists can be rewritten to use a counted-for loop, and every counted-for loop can be rewritten to use while loops, why bother with the other two loops?

To a large extent, this is an aesthetic choice, and programmers largely have come to consensus on when it is appropriate to use each of these loops. The shortest guideline is, “use the simplest form loop that works”. If a problem can be expressed as a computation over the particular items of an ArrayList, use a for-each loop. If the problem requires specifically manipulating indices, or counting for whatever reason, use a counted-for loop. If the number of iterations of the loop is not known a priori, use a while loop.

Additionally, as we will see in the next lecture, for-each loops are in fact more general than just working with ArrayLists, and we will encounter additional reasons to use them.

## Lecture 25: Iterator and Iterable

How does a for-each loop actually work?

We’ve discussed how the three loop forms in Java work to repeatedly execute some computation either:
- once for each item in an ArrayList, 
- once for every value of an integer variable as it counts
- or simply indefinitely many times while some condition remains true.

But for-each loops seem to only work on ArrayLists. What makes ArrayList so special that it deserves its own special syntax in the language? No other class we have seen gets such special treatment!

In fact, ArrayList is not special, and does not get its own syntax.

Instead, it is simply one particular example of a far more general notion, one that we can take advantage of for our own classes, too.

25.1 How do for-each loops actually work?

A for-each loop looks like this:

```java
for (T t : tList) {
  ...body...
}
```

25.1.1 Introducing Iterators

The while loop sketch above will work with any object that exposes these two functions as methods. This is a promise to provide a certain set of behaviors, so we should accordingly define a new interface. This interface is called an Iterator, and it is provided for us by Java itself. Its methods are slightly renamed from the sketch above:
According to our naming conventions, it really ought to be called IIterator, but that’s a clumsy name. At least it does start with a capital I!


```java
// Represents the ability to produce a sequence of values
// of type T, one at a time
interface Iterator<T> {
  // Does this sequence have at least one more value?
  boolean hasNext();
  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  T next();
  // EFFECT: Remove the item just returned by next()
  // NOTE: This method may not be supported by every iterator; ignore it for now
  void remove();
}
```

We can refine our while loop sketch above as follows:

```java
// Second attempt
Iterator<T> listIter = new ArrayListIterator<T>(tList);
while (listIter.hasNext()) {
  T t = listIter.next();
  ...body...
}
```

25.1.2 Introducing Iterables

Let’s say that an object is iterable if there exists some Iterator implementation for it. That sounds like another interface, which is also defined for us by Java:

```java
// Represents anything that can be iterated over
interface Iterable<T> {
  // Returns an iterator over this collection
  Iterator<T> iterator();
}
```

In the actual Java implementation of `ArrayList`, we see something like this:

```java
class ArrayList<T> implements Iterable<T> {
  ... lots of other details ...
  //
  public Iterator<T> iterator() {
    return new ArrayListIterator<T>(this);
  }
}
```

25.2 Examples of Iterators for different data types

25.2.1 Iterators for ArrayLists — counting indices

```java
class ArrayListIterator<T> implements Iterator<T> {
  // the list of items that this iterator iterates over
  ArrayList<T> items;
  // the index of the next item to be returned
  int nextIdx;
  // Construct an iterator for a given ArrayList
  ArrayListIterator(ArrayList<T> items) {
    this.items = items;
    this.nextIdx = 0;
  }
 
  // Does this sequence (of items in the array list) have at least one more value?
  public boolean hasNext() {
    return this.nextIdx < this.items.size();
  }
 
  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    T answer = this.items.get(this.nextIdx);
    this.nextIdx = this.nextIdx + 1;
    return answer;
  }
 
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
```

25.2.2 Iterators for ILists(Self-Referential) — following links

```java
class IListIterator<T> implements Iterator<T> {
  IList<T> items;
  IListIterator(IList<T> items) {
    this.items = items;
  }
  public boolean hasNext() {
    return this.items.isCons();
  }
  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    ConsList<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
```

### Define the isCons() and asCons() methods to complete this code.

Note: Yes, isCons and asCons aren’t great methods from an object-oriented standpoint. We know that double-dispatch would be a better design here. In this case, double-dispatch is overkill, as we’d need two visitor classes — one to implement isCons and one for asCons — which seems painfully heavyweight.

We can make our ILists be Iterable, too:

```java
// Declare that every IList is an Iterable:
interface IList<T> extends Iterable<T> {
  ... everything as before ...
}
class ConsList<T> implements IList<T> {
  ... everything as before ...
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}
class MtList<T> implements IList<T> {
  ... everything as before ...
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
  
```

And with those last few definitions, we can now use ILists in `for-each` loops, exactly as we could with `ArrayLists`.

```java
for (T item : myList) {
  // iterates forward through myList
  ...
}
```

25.2.3 Iteration in multiple directions

Some data structures can meaningfully support iteration in multiple orders. However, if we choose to make those data structures Iterable, then we have to choose a default iteration order to be used with for-each loops, and construct that particular iterator in the iterator() method. For Deques, we probably would choose the forward iteration direction, as it is the “most natural”. If we want to use the reverse iterator, we’d have to explicitly write the while-loop version ourselves

Note, if we create an iterator in other direction we should use the while loop version in order to iterate over all data in that specific direction.

```java
Iterator<T> revIter = myDeque.reverseIterator();
while (revIter.hasNext()) {
  // iterates backward through myDeque
  T item = revIter.next();
  ...
}
```

25.2.4 Iterators for Fibonacci numbers — computing items on demand

Not every iterator needs to store an actual object from which it derives its data. Iterators simply represent sequences of values, and those values might just be computed on demand. Consider the following iterator for calculating Fibonacci numbers:

```java
class FibonacciIterator implements Iterator<Integer> {
  int prevVal = 0;
  int curVal = 1;
  // There are always more Fibonacci numbers
  boolean hasNext() { return true; }
  // Compute the next Fibonacci number
  Integer next() {
    int answer = this.prevVal + this.curVal;
    this.prevVal = this.curVal;
    this.curVal = answer;
    return answer;
  }
  public void remove() {
    throw new UnsupportedOperationException("Don't do this!");
  }
}
```

Other sequences could be prime numbers, or powers of 2, or any other sequence that can be computed on demand.

25.2.5 Higher-order Iterators

Given an iterator we can create a new iterator that iterates over the results of applying a given function to each item in the original iterator. This is called a higher-order iterator.

Also, we can skip some items in the original iterator by using a higher-order iterator.

```java
// Represents the subsequence of the first, third, fifth, etc. items from a given sequence
class EveryOtherIter<T> implements Iterator<T> {
  Iterator<T> source;
  EveryOtherIter(Iterator<T> source) {
    this.source = source;
  }
  public boolean hasNext() {
    // this sequence has a next item if the source does
    return this.source.hasNext();
  }
  public T next() {
    if (!this.hasNext()) {  // defensively check whether any elements remain
      throw new NoSuchElementException(); // and fail if there aren't any
    }
    T answer = this.source.next(); // gets the answer, and advances the source
    // We need to have the source "skip" the next value
    if (this.source.hasNext() {
      this.source.next(); // get the next value and ignore it
    }
    return answer;
  }
  public void remove() {
    // We can remove an item if our source can remove the item
    this.source.remove(); // so just delegate to the source
  }
}
```

25.2.6 Iterators over tree-shaped data

We can even define iterators over tree-shaped data. Let’s consider binary trees. There are many plausible orders for traversing a tree. For the following example tree (with data at the nodes, and nothing at the leaves):
```
      A
     / \
    /   \
   B     C
  / \   / \
 D   E F   G
/ \ /\/\  / \
```

we have (at least) the following standard orders:
- A breadth-first traversal, which walks through each level of the tree from top to bottom, left to right:
A, B, C, D, E, F, G

- A post-order traversal, which recursively produces all the children of a node before producing the node itself:
D, E, B, F, G, C, A

- An in-order traversal, which recursively produces the left subtree of a node, then the node, then recursively produces the right subtree:
D, B, E, A, F, C, G

- A pre-order traversal (or a depth-first traversal), which produces the node, then recursively produces the left subtree of the node, then the right subtree:
A, B, D, E, C, F, G

We can create iterators for each of these orders.

## Lecture 26: Hashing and Equality

A fast data structure for finding data by a key; the full rules for equality in Java

26.1.1 Dictionaries

If we want to represent an English dictionary we might start by:

```java
// Represents one word in a dictionary, together with its definition
class DictEntry {
  String word;
  String meaning;
}
```

We then need to represent a collection of these entries. We have several choices of data structures to use to represent this collection:
- IList<DictEntry>, with entries in no particular order
- IList<DictEntry> that is sorted alphabetically by word
- Deque<DictEntry>, with entries in no particular order
- Deque<DictEntry> that is sorted alphabetically by word
- ArrayList<DictEntry>, with entries in no particular order
- ArrayList<DictEntry> that is sorted alphabetically by word
- Binary trees of DictEntry
- Binary search trees of DictEntry, ordered alphabetically by word

Let’s consider what operations we’ll typically perform on such a dictionary. The single most important operation on a dictionary of words is to lookup a word to find its definition, so ideally we want that operation to be as fast as possible.

Right away, we can see that the unsorted IList, Deque, ArrayList and binary tree options are woefully outmatched by their sorted versions: at the cost of maintaining the sort order, we get far better algorithms to access the items.

Some slightly more careful thought shows that both ILists and Deques are less effective than ArrayLists, since we can (as we saw in Lecture 22) use binary search over indices to quickly narrow in on a word, rather than plod our way through the list from front to back.

26.1.2 Wikis

A wiki is like a dictionary that anyone can edit. Here, instead of words and meanings, we would have

```java
// Represents a Wiki entry
class WikiEntry {
  String url;
  String contents;
}
```

Here the performance tradeoffs are a bit less clear: we need the ability to quickly access an entry by its URL, and we need the ability to quickly edit an entry, but we also need the ability to create or delete entries quickly, too. Further, URLs don’t have an obvious ordering: technically we could sort them alphabetically, but that ordering is artificial. Users of a wiki don’t scan through the entire wiki looking for the article they want; they search for the term or just type in the URL directly.

With this in mind, our best contenders above (the sorted ArrayList and the binary search trees) are looking less ideal. Further, because we are now worried about creating and deleting entries, we have to worry about the cost of maintaining the sort order.

26.2 Introducing Hash-tables

A hash-table is a data structure that allows us to quickly find data by a key. It is a very fast data structure, and it is the basis of many other data structures.

Similar implementation to ArrayList but with a key instead of an index. The key is generated by a summarized function called a hash function.

26.3 Introducing HashMaps

This data structure is so widely used that, like ArrayLists, Java provides an implementation for us. It’s called a HashMap<K, V>, and it’s a generic data type that is parameterized by the type K of keys and the type V of values.

```java
import java.util.HashMap;
```

In Java, every class implicitly extends a common base class called Object. (If we define a class that explicitly extends another class, then that base class eventually extends Object.) 

Because of this, every object type inherits a method called hashCode, which simply returns an integer. (This is also why every class has a toString method, and various other methods we haven’t needed to examine in this course.) Java even provides a default implementation of this method for us. A hash code is simply a summarization of a piece of data as a number, and the hashCode method is a hash function that computes this hash code for us.

```java
class ExampleHashMaps {
  void testHashMaps(Tester t) {
    HashMap<String, String> rooms = new HashMap<String, String>();
    // Put all the data into the hashtable
    rooms.put("Ben Lerner", "WVH314");
    rooms.put("Leena Razzaq", "WVH310B");
    rooms.put("Olin Shivers", "WVH308");
    rooms.put("Matthias Felleisen", "WVH308B");
    // Get the data
    t.checkExpect(rooms.get("Ben Lerner"), "WVH314");
    t.checkExpect(rooms.get("Olin Shivers"), "WVH310B");
    // Check that some data is present
    t.checkExpect(rooms.containsKey("Leena Razzaq"), true);
    t.checkExpect(rooms.containsKey("Amal Ahmed"), false);
    // Data that isn't present will return null
    t.checkExpect(rooms.get("Amal Ahmed"), null);
  }
}
```

26.4 Hash collisions

The hash function is a summarization of the data, and it is possible for two different pieces of data to have the same hash code. This is called a hash collision. When this happens, the HashMap will store the data in a linked list at that location. This is why we need to override the equals method in our data types.

26.5 Hash codes and equality

Collision: two different pieces of data have the same hash code

Ways to avoid collisions:
- better hash function
- more space(But this may be wasteful, if most of those locations are empty)
- probing(which says “Compute the hashCode of the key. If that index is already used by some other data, then look at the next index, and keep going until you find a free index.”)
- chaining, and replaces our ArrayList of values with an ArrayList of ArrayLists of values. Each inner list, or bucket, can hold all the values it needs to. Of course, this eventually defeats the purpose of having a hash table in the first place: we have to scan that inner list)
- rehashing(essentially uses that inner ArrayList as another hash table, using a different hash function so that the inner table doesn’t immediately fill with collisions)

26.5.1 Defining custom hashCodes

```java
class Book {
  Author author;
  String title;
  int year;
  public int hashCode() {
    return this.author.hashCode() * 10000 + this.year;
  }
}
class Author {
  Book book;
  String name;
  int yob;
  public int hashCode() {
    return this.name.hashCode() * 10000 + this.yob;
  }
}
```

26.5.2 Defining custom equals methods for simple classes

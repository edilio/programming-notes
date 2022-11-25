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

## Lecture 13: Abstracting over behavior

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

It should be pretty easy create an or predicate:

```java
// Represents a predicate that is true whenever one of its component predicates is true
class AndPredicate implements IRunnerPredicate {
  IRunnerPredicate left, right;
  AndPredicate(IRunnerPredicate left, IRunnerPredicate right) {
    this.left = left;
    this.right = right;
  }
  public boolean apply(Runner r) {
    return this.left.apply(r) || this.right.apply(r);
  }
}
```

Lecture 14: Abstractions over more than one argument

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

Instead of `comesBefore` returning a boolean, we want to change ti to an integer so we can also know if they are tie.

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

Lecture 15: Abstracting over types(Generics)

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

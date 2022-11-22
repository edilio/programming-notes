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

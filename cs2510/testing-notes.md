# Testing notes

Required:

Add `import tester.*;`

## Basic Example:

```java
boolean testBookAuthors(Tester t) {
  this.initTestConditions();
  // Test 1: check that knuth hasn't written any books yet
  boolean test1 = t.checkExpect(this.knuth.book, null);
  // Modify knuth to refer to volume 1
  this.knuth.updateBook(this.taocp1);
  // Test 2: check that knuth's book was written by knuth
  boolean test2 = t.checkExpect(this.knuth.book.author, this.knuth);
  // Try to modify knuth to refer to volume 2
  this.knuth.updateBook(this.taocp2); // Crashes with an exception
}
```

So, we need some conditions to test. We can use the `initTestConditions` method to set up the conditions we need. We can then test that the conditions are as expected. We can then modify the conditions to test that the modifications are as expected. We can then try to modify the conditions in a way that we know will fail, and check that the failure is as expected.

## Important functions:

- checkExpect

```java
void checkExpect(T actual, T expected)
t.checkExpect(shakespeare.book, null);
```
- checkInexact

```java
void checkInexact(double actual, double expected, double tolerance)

t.checkInexact(area, expectedArea, 0.01);
```

- checkException

```java
t.checkException(
    new RuntimeException("book was not written by this author"),
    shakespeare, "updateBook", this.taocp1);
```

- checkConstructorException

```java
boolean checkConstructorException(Exception e,
                                    String className,
                                    ... constr args ...);
                                    
t.checkConstructorException(
     // the expected exception
     new IllegalArgumentException("Invalid year: 53000"),
     // the *name* of the class (as a String) whose constructor we invoke
     "Date",
     // the arguments for the constructor
     53000, 12, 30);              
```

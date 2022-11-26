# Testing notes

Required:

Add `import tester.*;`

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

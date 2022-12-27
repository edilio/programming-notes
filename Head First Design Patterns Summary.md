# Head First Design Pattern

## Design Principles
- Identify the aspects of your application that vary and separate then from what stays the same
- Favor composition over inheritance(has-a vs is-a)
- Program to an interface(subtype), not an implementation
- Strive for loosely coupled designs between objects that interact
- Open-Closed principle(Classes should be open for extension, but closed for modification)
- Dependency Inversion Principle(Depend upon abstractions. Do not depend upon concrete classes)
- Principle of Least Knowledge - talk only to your immediate friends(Law of Demeter)
- The Hollywood Principle(Don’t call us, we’ll call you)
- A class should have only one reason to change
- Single Responsibility(A class should have only one reason to change)

### Cohesion

It is a term you will her used as a measure of how closely a class or a module supports a single purpose or responsibility.

We say that a module or class has a `high cohesion` when it is designed around a set of related functions, and we say  it has `low cohesion` when it is designed around a set of unrelated functions.

Cohesion is a more general concept than `Single Responsibility Principle`, but the two are closely related. Classes that adhere to the principle tend to have high cohesion and are more maintainable than classes that take on multiple responsibilities and have low cohesion.

## Chapters

- Chapter 1(Strategy Pattern, also known as Policy Pattern)
- Chapter 2(Observer Pattern)
- Chapter 3(Decorator Pattern)
- Chapter 4(Factory Pattern)
- Chapter 5(Singleton Pattern)
- Chapter 6(Command Pattern)
- Chapter 7(Adapter and Facade Pattern)
- Chapter 8(Template Method Pattern)
- Chapter 9(Iterator and Composite Pattern)
- Chapter 10(State Pattern)
- Chapter 11(Proxy Pattern)
- Chapter 12(Compound Patterns - MVC Pattern)
- Chapter 13(Patterns in the Real World)
- Appendix 14(Leftover Patterns)
  - Bridge Pattern
  - Builder Pattern
  - Chain of Responsibility Pattern
  - Flyweight Pattern
  - Interpreter Pattern
  - Mediator Pattern
  - Memento Pattern
  - Prototype Pattern
  - Visitor Pattern

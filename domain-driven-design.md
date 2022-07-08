# Domain Driven Design Summary

[https://verraes.net/2021/09/what-is-domain-driven-design-ddd/](https://verraes.net/2021/09/what-is-domain-driven-design-ddd/)

### Principal Concepts

- Ubiquitous Language
- Bounded Context
- Context Mapping
- EventStorming(Not introduced in the first book)

### Definitions:

- **domain** A sphere of knowledge, influence, or activity. The subject area to which the user applies a program is the domain of the software.
- **model** A system of abstractions that describes selected aspects of a domain and can be used to solve problems related to that domain.
- **ubiquitous language** A language structured around the domain model and used by all team members within a bounded context to connect all the activities of the team with the software.
- **context** The setting in which	a word or statement appears that determines its meaning.	Statements about a model can only be understood in a context.
- **bounded context** A description of	a boundary(typically a subsystem, or the work of a particular team) within which a particular model is defined and applicable.

One important philosophy of **DDD** is that the model is the code

### Other Architectural techniques:

- Anticorruption Layer
- Branch by Abstraction([Sam Newman](https://www.youtube.com/results?search_query=Sam+Newman))

## Building Blocks of a Model-Driven Design

- Layered Architecture
- Entities
- Value Objects
- Domain Events *
- Services
- Modules
- Aggregates
- Repositories
- Factories

### Layered Architecture:

A common architectural solution for domain-driven designs contains four conceptual layers:

| Layer | Description |
| ----- | ----------- |
| User Interface | Responsible for presenting information to the user and interpreting user commands.|
| Domain Layer| This layer contains information about the domain. This is the heart of business software. The state of business objects is held here. Persistence of the business objects and possibly their state is delegated to the infrastructure layer. |
| Application Layer | This is a thin layer which coordinates the application activity. It does not contain business logic. It does not hold the state of the business objects, but it can hold the state of an application task progress.|
| Infrastructure Layer | This layer acts as a supporting library for all the other layers. It provides communication between layers, implements persistence for business objects, contains supporting libraries for the user interface layer, etc. |



### Entities:

There is a category of objects which seem to have an identity, which remains the same throughout the states of the software. For these objects it is not the attributes which matter, but a thread of continuity and identity, which spans the life of a system and can extend beyond it. Such objects are called Entities

**Conceptual qualities of entity objects:**

- Represent things distinguishable by identity - even if they are otherwise equivalent
- The identity is stable and unique in the model context
- Lifecycle is important, creation, evolution, deactivation
- Often mutable
- Composite - compose of other entities and value objects

### Value Objects:

They have no conceptual identity. They are equal because they have equal features(value).

Examples are:

- Price(amount, quantity)
- Temperature(grades, scale)
- Color(R, G, B)
- Balance(amount, currency)
- Address(address1, address2, city, state, zip, country)
- Point
- Date
- Date range
- Distance

They can also act as identifiers like **email**, **locale, currency, telephone number, IP address, URL, file path, and entity identifier**.

When you care only about the attributes and logic of an element of the model, classify it as a value object. Make it express the meaning of the attributes it conveys and give it related functionality. Treat the value object as **immutable**. Make all operations **side-effect-free functions** that don’t depend on any mutable state. Don’t give a value object any identity and avoid the design complexities necessary to maintain entities.

**Core principles:**

- Measures, quantifies or describes something in your domain
- Equivalent values are interchangeable
- Comparable by value
- Lack intrinsic identity(though they might represent an identity)
- Self-contained
- Inmutable, no shareable
- Encapsulated by Entities
- Side effect free functions
- Avoid primitive obsession

[https://en.wikipedia.org/wiki/Value_object](https://en.wikipedia.org/wiki/Value_object)

[https://dddinpython.com/index.php/2021/11/22/value-objects-in-python/](https://dddinpython.com/index.php/2021/11/22/value-objects-in-python/)

[https://medium.com/codex/you-should-be-using-value-objects-568b19b1df8d](https://medium.com/codex/you-should-be-using-value-objects-568b19b1df8d)

[https://martinfowler.com/bliki/ValueObject.html](https://martinfowler.com/bliki/ValueObject.html)

Value objects are also useful for reducing input parameters in functions and also they play a role to simplify or describe better **Entity** objects because they can be created using value objects.

Actually, it is recommended to select as entities only those objects which conform to the entity definition. And make the rest of the objects Value Objects

### Domain Events:

Model information about activity in the domain as a series of discrete events. Represent each event as a domain object. These are distinct from system events that reflect activity within the software itself, although often a system event is associated with a domain event, either as part of a response to the domain event or as a way of carrying information about the domain event into the system.

A domain event is a full-fledged part of the domain model, a representation of something that happened in the domain. Ignore irrelevant domain activity while making explicit the events that the domain experts want to track or be notified of, or which are associated with state change in the other model objects.

Domain events are ordinarily immutable, as they are a record of something in the past. In addition to a description of the event, a domain event typically contains a	timestamp for the time the event occurred and the identity of entities involved in the event. Also, a domain event often has a separate timestamp indicating when the event was entered into the system and the identity of the person who entered it. When useful, an identity for the domain event can be based on some set of these properties. So, for example, if two instances of the same event arrive at a node they can be recognized as the same.

### Services:

Services act as interfaces which provide operations. Services are common in technical frameworks, but they can be used in the domain layer too.

When a significant	process or transformation in the domain is not a natural responsibility of an	 entity or value object, add an operation to the model as a standalone interface declared as a service. Define a service contract, a set of assertions about interactions with the service.(See assertions.) State these assertions in the ubiquitous language of a specific bounded context. Give the service a name, which also becomes part of the ubiquitous language.

A Service should not replace the operation which normally belongs on domain objects. We should not create a Service for every operation needed. But when such an operation stands out as an important concept in the domain, a Service should be created for it.

There are three characteristics of a Service:

1. The operation performed by the Service refers to a domain concept which does not naturally belong to an Entity or Value Object.

2. The operation performed refers to other objects in the domain.

3. The operation is stateless.

### Modules:

For a large and complex application, the model tends to grow bigger and bigger. The model reaches a point where it is hard to talk about as a whole, and understanding the relationships and interactions between different parts becomes difficult. For that reason, it is necessary to organize the model into modules.

Modules are used as a method of organizing related concepts and tasks in order to reduce complexity. Modules are widely used in most projects. It is easier to get the picture of a large model if you look at the modules it contains, then at the relationships between those modules. After the interaction between modules is understood, one can start figuring out the details inside of a module. It’s a simple and efficient way to manage complexity.

### Aggregates:

Cluster the entities and value objects into aggregates and define boundaries around each. Choose one entity to be the root of each aggregate, and allow external objects to hold references to the root only (references to internal members passed out for use within a single operation only).

Define properties and invariants for the aggregate as a whole and give enforcement responsibility to the root or some designated framework mechanism.

### Repositories:

In a model-driven design, objects have a life cycle starting with creation and ending with deletion or archiving. A constructor or a Factory takes care of object creation and the repositories will take care of load/store/archive them

### Factories:

Factories are used to encapsulate the knowledge necessary for object creation, and they are especially useful to create Aggregates. When the root of the Aggregate is created, all the objects contained by the Aggregate are created along with it, and all the invariants are enforced.

## Other references:

### Videos:

[DDD Today - Modeling Uncertainty • Vaughn Vernon • GOTO 2017](https://www.youtube.com/watch?v=8Y-XPlXOWoA&t=271s)

[DDD and Microservices: At Last, Some Boundaries!](https://www.youtube.com/watch?v=sFCgXH7DwxM)

[What is DDD - Eric Evans - DDD Europe 2019](https://www.youtube.com/watch?v=pMuiVlnGqjk)

[Greg Young — A Decade of DDD, CQRS, Event Sourcing](https://www.youtube.com/watch?v=LDW0QWie21s)

[Design Patterns: Why Event Sourcing?](https://www.youtube.com/watch?v=rUDN40rdly8)

https://martinfowler.com/bliki/DomainDrivenDesign.html

[Domain-Driven Design Reference](https://www.domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)

[Domain-Driven Design Quickly](https://matfrs2.github.io/RS2/predavanja/literatura/Avram%20A,%20Marinescu%20F.%20-%20Domain%20Driven%20Design%20Quickly.pdf)

[Domain Driven Design - Eric Evans](https://github.com/ZilvinasKucinskas/FRP-EventSourcing/blob/master/sources/xx735.Eric.Evans.Domaindriven.Design.Tackling.Complexity.in.the.Heart.of.Software.pdf)

[Best DDD site so far](https://badia-kharroubi.gitbooks.io/microservices-architecture/content/patterns/tactical-patterns/domain-entity-pattern.html)

**Django** is using [Active Record Pattern](https://www.martinfowler.com/eaaCatalog/activeRecord.html)

### Design in general

[A Philosophy of Software Design | John Ousterhout | Talks at Google](https://youtu.be/bmSAYlu0NcY)

[Martin Fowler - Software Design in the 21st Century](https://www.youtube.com/watch?v=6wDoopbtEqk)

[The Many Meanings of Event-Driven Architecture • Martin Fowler • GOTO 2017](https://www.youtube.com/watch?v=STKCRSUsyP0)

[Five Things Every Developer Should Know about Software Architecture • Simon Brown • GOTO 2020](https://www.youtube.com/watch?v=9Az0q2XHtH8&t=714s)

Continuous Integration:

[Modern Continuous Delivery • Ken Mugrage • GOTO 2019](https://www.youtube.com/watch?v=wjF4X9t3FMk)

## Guys to follow:

- DDD - [Eric Evans](https://www.youtube.com/results?search_query=Eric+Evans)
- Continuous Integrations/Continuous Delivery ([Martin Fowler](https://www.youtube.com/results?search_query=Martin+Fowler), [Dave Farley](https://www.youtube.com/results?search_query=dave+farley), [Ken Mugrage](https://www.youtube.com/results?search_query=Ken+Mugrage))
- Event Sourcing([Greg Young](https://www.youtube.com/results?search_query=Greg+Young))
- Microservices([Sam Newman](https://www.youtube.com/results?search_query=Sam+Newman))

[https://www.martinfowler.com/eaaCatalog/activeRecord.html](https://www.martinfowler.com/eaaCatalog/activeRecord.html)

## Event Storming Definition

Event Storming is a collaborative process modeling workshop for modeling a business process typically with a goal of discovery and refinement of a domain model for business software.

**The Process**

A facilitator leads the workshop which is attended by various roles within a company including stakeholders, subject matter experts, and engineers wherein a business process is collaboratively and somewhat chaotically modeled with sticky notes on a work surface representing a timeline.

The modeling is led by the Facilitator in phases where different color sticky notes are placed on the timeline to flesh out the process, typically starting with Events in orange, Commands in blue, followed by other categories such as Users, External Systems, Aggregates, etc. It is often the case that participants have differing perspectives, views, gaps in understanding, and even language used in understanding the business process.

In the end, the goal should be to arrive at a single, cohesive model with a single domain language used as the foundation for business processes going forward.

**Event Storming Resources**

Quick Intro (2 minutes): [What is Event Storming? | Paul Rayner](https://www.youtube.com/watch?v=Y7NzXl-ahtU)

ThoughtWorks Intro (4 minutes): [Event Storming: the first step of a business modernization journey with Cassie Shum](https://www.youtube.com/watch?v=-RjsdYwsAac)

Presentation by originator, Alberto Brandolini (1 hour)- [Alberto Brandolini - 50,000 Orange Stickies Later](https://www.youtube.com/watch?v=1i6QYvYhlYQ)

Online Event Storming Session Example (1 hr, 45 minutes): [Trying out online EventStorming](https://www.youtube.com/watch?v=CbPEibNUe0s)

Facilitator’s Guide: [https://medium.com/@springdo/a-facilitators-recipe-for-event-storming-941dcb38db0d](https://medium.com/@springdo/a-facilitators-recipe-for-event-storming-941dcb38db0d)

## REST API

[Never RESTing – RESTful API Best Practices using ASP.NET Web API - Spencer Schneidenbach](https://youtu.be/x0yNKU-tz1Y)

[Oktane17: Designing Beautiful REST + JSON APIs](https://youtu.be/MiOSzpfP1Ww)

## Microservices

[Design Microservice Architectures the Right Way](https://www.youtube.com/watch?v=j6ow-UemzBc)

## TO WATCH AND SORT / PURGE

[YOW! 2011 Jim Webber - Domain-Driven Design for RESTful Systems #YOW](https://www.youtube.com/watch?v=aQVSzMV8DWc)

[A Path to Event Sourcing With Amazon MSK - Level 300 (United States)](https://youtu.be/Rr2TDLfvN1I)
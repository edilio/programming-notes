# Concepts

- Interfaces(union structures)
- Dynamic dispatch
- Self-referential structures(List, Tree)
- Generics(abstraction over types)
- Delegation
- Helper functions
- Function Objects
- Higher-order functions
- Recursion
- Lambda expressions
- Field of a field access(not allowed)
- Accumulators for recursive algorithms
- How to use interfaces to create `Predicates`, `Comparators` and `IFuncs` so we can see how to refactor `Generic` Lists to have `filter`, `sort`, `map` and `foldr`(reduce).
- Double dispatch(For SameXXX and for visitor pattern)
- Mutations(side effects)
- Aliasing(when two variables refer to the same object)
- Cycle data structures(direct and indirect)
- Iterators, Iterables and Higher-order Iterators
- Sentinels(a special object that is used to mark the beginning or end of a list)
- Wrapper classes for lists(wraps around the fiddly details of managing Sentinels and ConsLoPersons and such)


# Notes
- null
- void
- instanceof
- casting
- this
- super
- side effects

# Data Structures

- Lists(IList, ArrayList)
- Trees(BinaryTrees, BinarySearchTree)
- Deques(double-ended queues, allow insertion and deletion at both ends)

# Algorithms
- Binary Search
- Foldr, Foldl(Reduce)
- Map
- Filter
- Sort(insertion sort, merge sort, quick sort)

# Summary done by the teachers(lecture 27)
The data structures they introduced served a via to talk about `interfaces` and `linked lists`,
`indexed data structures`, `branching structures`, and `wrappers and sentinels`. 

They have also implemented many of the same algorithms for each data structure: 
- inserting items
- sorting items
- finding items
- mapping over items
- etc.

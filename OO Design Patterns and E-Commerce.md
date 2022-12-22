# How we can use OO Design Patterns to implement E-Commerce

## Strategy Pattern

The Strategy pattern can be useful for cases where you need to vary the behavior of a system based on different business rules or policies. For example, you might use the Strategy pattern to implement different pricing strategies for different products or to handle shipping in different ways for different regions.

Here's an example of how you might use the Strategy pattern in an e-commerce application:

- Define an interface for the algorithm you want to implement (e.g. `PricingStrategy`, `ShippingStrategy`).
- Create concrete implementations of the algorithm for each variation you want to support (e.g. `DiscountPricingStrategy`, `StandardPricingStrategy`, `LocalShippingStrategy`, `InternationalShippingStrategy`).
- In your e-commerce application, create a class (e.g. `Product`, `Order`) that needs to use one of the algorithms. This class should have a field for storing a reference to the algorithm and a method for setting the algorithm (e.g. `set_pricing_strategy`, `set_shipping_strategy`).
- To use a particular algorithm, create an instance of the desired concrete strategy and pass it to the object using the appropriate setter method.

You can also use the `Strategy` design pattern to handle different payment methods in an e-commerce application. The basic approach would be similar to the examples I provided earlier:

- Define an interface for the payment method algorithm (e.g. `PaymentMethod`, `PaymentProcessor`).
- Create concrete implementations of the algorithm for each payment method you want to support (e.g. `CreditCardPayment`, `PayPalPayment`, `BankTransferPayment`).
- In your e-commerce application, create a class (e.g. `Order`, `Transaction`) that needs to use a payment method. This class should have a field for storing a reference to the payment method algorithm and a method for setting the payment method (e.g. `set_payment_method`, `select_payment_processor`).
- To use a particular payment method, create an instance of the desired concrete strategy and pass it to the object using the appropriate setter method.

## Command Pattern

Command pattern can be useful for cases where you want to decouple the object that invokes an operation (the client) from the object that performs the operation (the receiver). For example, you might use the Command pattern to implement a shopping cart that allows a user to add, remove, and view the items in their cart, or to implement an order system that allows an administrator to cancel, modify, or view orders.

Here's an example of how you might use the `Command` pattern in an e-commerce application:

Define an interface for the command (e.g. `Command`, `Action`).
Create concrete implementations of the command for each operation you want to support (e.g. `AddToCartCommand`, `RemoveFromCartCommand`, `ViewCartCommand`, `CancelOrderCommand`, `ModifyOrderCommand`, `ViewOrderCommand`).
In your e-commerce application, create a class (e.g. `ShoppingCart`, `OrderSystem`) that needs to use the commands. This class should have a method for executing a command (e.g. `execute_command`, `perform_action`).
To invoke a particular command, create an instance of the desired concrete command and pass it to the object using the appropriate execution method.
Here's an example of how you might implement the Command pattern in Python:

```python
from abc import ABC, abstractmethod

class Command(ABC):
    @abstractmethod
    def execute(self):
        pass

class AddToCartCommand(Command):
    def __init__(self, shopping_cart, product):
        self.shopping_cart = shopping_cart
        self.product = product
        
    def execute(self):
        self.shopping_cart.add_to_cart(self.product)

class RemoveFromCartCommand(Command):
    def __init__(self, shopping_cart, product):
        self.shopping_cart = shopping_cart
        self.product = product
        
    def execute(self):
        self.shopping_cart.remove_from_cart(self.product)

class ViewCartCommand(Command):
    def __init__(self, shopping_cart):
        self.shopping_cart = shopping_cart
        
    def execute(self):
        self.shopping_cart.view_cart()

class ShoppingCart:
    def __init__(self):
        self.items = []
        
    def add_to_cart(self, product):
        self.items.append(product)
        
    def remove_from_cart(self, product):
        self.items.remove(product)
        
    def view_cart(self):
        for item in self.items:
            print(item)
```


## Memento Pattern?

Here's an example of how you might use the Memento pattern in an e-commerce application:

The user adds a product to their shopping cart.
- The system creates a Memento object that captures the state of the shopping cart before the product was added.
- If the user decides to remove the product from their cart, the system can use the Memento object to restore the cart to its previous state.
- To implement the Memento pattern, you'll need to define a Memento class that captures the internal state of the object you want to store. You'll also need to define a Caretaker class that is responsible for managing the Memento objects.

Here's an example of how you might implement the `Memento` and `Caretaker` classes in Python:
    
```python
class Memento:
    def __init__(self, state):
        self.state = state

class Caretaker:
    def __init__(self):
        self.mementos = []

    def add_memento(self, memento):
        self.mementos.append(memento)

    def get_memento(self, index):
        return self.mementos[index]
```

To use the `Memento` and `Caretaker` classes in your e-commerce application, you would first create an instance of the `Caretaker` class. Then, whenever a user performs an action that you want to allow them to undo, you would create a new `Memento` object and pass it to the `Caretaker` using the `add_memento` method. To revert to a previous state, you can retrieve the appropriate `Memento` object using the `get_memento` method and use it to restore the state of the relevant object.

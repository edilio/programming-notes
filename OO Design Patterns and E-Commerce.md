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

## Observer Pattern

The Observer pattern can be useful for cases where you want to notify multiple parties about changes to an object, such as updates to a product's availability or price. For example, you might use the Observer pattern to implement a notification system that sends email updates to customers when a product they are interested in becomes available, or to update the display of a product's availability on the website in real-time.

Here's an example of how you might use the Observer pattern in an e-commerce application:

- Define an interface for the observer (e.g. `Observer`, `Subscriber`).
- Create concrete implementations of the observer for each type of notification you want to support (e.g. `EmailSubscriber`, `SMSSubscriber`, `WebhookSubscriber`).
- Define a class for the subject (e.g. `Product`, `Inventory`) and give it methods for attaching and detaching observers, and for notifying observers when its state changes (e.g. attach, detach, notify).
- In your e-commerce application, create instances of the subject and observer classes and attach the observers to the subject using the appropriate method.
- When the state of the subject changes, call the notify method to update the observers.

Here's an example of how you might implement the Observer pattern in Python:

```python
from abc import ABC, abstractmethod

class Observer(ABC):
    @abstractmethod
    def update(self, subject):
        pass

class EmailSubscriber(Observer):
    def __init__(self, email):
        self.email = email
        
    def update(self, subject):
        print(f'Sending email to {self.email} about {subject.name}')

class SMSSubscriber(Observer):
    def __init__(self, phone_number):
        self.phone_number = phone_number
        
    def update(self, subject):
        print(f'Sending SMS to {self.phone_number} about {subject.name}')

class WebhookSubscriber(Observer):
    def __init__(self, url):
        self.url = url
        
    def update(self, subject):
        print(f'Sending webhook to {self.url} about {subject.name}')

class Subject:
    def __init__(self):
        self.observers = []
        self.name = None
        
    def attach(self, observer):
        self.observers.append(observer)
        
    def detach(self, observer):
        self.observers.remove(observer)
        
    def notify(self):
        for observer in self.observers:
            observer.update(self)

class Product(Subject):
    def __init__(self, name):
        super().__init__()
        self.name = name
        
    def set_name(self, name):
        self.name = name
        self.notify()
```

You could also use the Observer pattern to implement an event source inventory system in order to create different projections of the inventory. For example, you might want to create a projection of the inventory that is optimized for querying the availability of a product, and another projection that is optimized for querying the price of a product. You could use the Observer pattern to implement this system by creating a subject class for the inventory and an observer class for each projection.

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

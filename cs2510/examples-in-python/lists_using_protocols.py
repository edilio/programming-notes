"""
The following class diagram defines a class hierarchy that represents a list of books in a bookstore:

               +--------------------------------+
               | ILoBook                        |<----------------------+
               +--------------------------------+                       |
               +--------------------------------+                       |
               | int count()                    |                       |
               | double salePrice(int discount) |                       |
               | ILoBook allBefore(int y)       |                       |
               | ILoBook sortByPrice()          |                       |
               +--------------------------------+                       |
                                |                                       |
                               / \                                      |
                               ---                                      |
                                |                                       |
                  -----------------------------                         |
                  |                           |                         |
+--------------------------------+   +--------------------------------+ |
| MtLoBook                       |   | ConsLoBook                     | |
+--------------------------------+   +--------------------------------+ |
+--------------------------------+ +-| Book first                     | |
| int count()                    | | | ILoBook rest                   |-+
| double salePrice(int discount) | | +--------------------------------+
| ILoBook allBefore(int y)       | | | int count()                    |
| ILoBook sortByPrice()          | | | double salePrice(int discount) |
+--------------------------------+ | | ILoBook allBefore(int y)       |
                                   | | ILoBook sortByPrice()          |
                                   | +--------------------------------+
                                   v
                   +--------------------------------+
                   | Book                           |
                   +--------------------------------+
                   | String title                   |
                   | String author                  |
                   | int year                       |
                   | double price                   |
                   +--------------------------------+
                   | double salePrice(int discount) |
                   +--------------------------------+
"""


class Book:
    def __init__(self, title, author, year, price):
        self.title = title
        self.author = author
        self.year = year
        self.price = price

    def __str__(self):
        return self.title + " by " + self.author + " (" + str(self.year) + ")"

    def sale_price(self, discount):
        return self.price * (1 - discount / 100.0)

    def cheaper(self, other):
        return self.price < other.price

    def same_author(self, other):
        return self.author == other.author

    def same_year(self, other):
        return self.year == other.year


class ILoBook:
    def count(self):
        ...

    def sale_price(self, discount):
        ...

    def all_before(self, y):
        ...

    def sort_by_price(self):
        ...


class MtLoBook(ILoBook):
    def count(self):
        return 0

    def sale_price(self, discount):
        return 0

    def all_before(self, y):
        return MtLoBook()

    def sort_by_price(self):
        return MtLoBook()

    def __str__(self):
        return "MtLoBook()"


class ConsLoBook(ILoBook):
    def __init__(self, first, rest):
        self.first = first
        self.rest = rest

    def count(self):
        return 1 + self.rest.count()

    def sale_price(self, discount):
        return self.first.sale_price(discount) + self.rest.sale_price(discount)

    def all_before(self, y):
        if self.first.year < y:
            return ConsLoBook(self.first, self.rest.all_before(y))
        else:
            return MtLoBook()

    def sort_by_price(self):
        return self.rest.sort_by_price().insert(self.first)

    def insert(self, book):
        if self.first.cheaper(book):
            return ConsLoBook(self.first, self.rest.insert(book))
        else:
            return ConsLoBook(book, self)

    def __str__(self):
        return "ConsLoBook(" + str(self.first) + ", " + str(self.rest) + ")"


def main() -> None:
    b1 = Book("The Hobbit", "J.R.R. Tolkien", 1937, 10.00)
    b2 = Book("The Fellowship of the Ring", "J.R.R. Tolkien", 1954, 12.00)
    b3 = Book("The Two Towers", "J.R.R. Tolkien", 1954, 12.00)
    b4 = Book("The Return of the King", "J.R.R. Tolkien", 1955, 12.00)
    b5 = Book("The Silmarillion", "J.R.R. Tolkien", 1977, 15.00)

    books = ConsLoBook(b1, ConsLoBook(b2, ConsLoBook(b3, ConsLoBook(b4, ConsLoBook(b5, MtLoBook())))))
    print(books.count())
    print(books.sale_price(10))
    print(books.all_before(1955))


if __name__ == '__main__':
    main()

from typing import Protocol


class Visitor(Protocol):
    def visit_circle(self, circle: 'Circle') -> float:
        ...

    def visit_rectangle(self, rectangle: 'Rectangle') -> float:
        ...

    def visit_square(self, square: 'Square') -> float:
        ...


class IShape(Protocol):
    def accept(self, visitor: Visitor) -> float:
        ...

    def __str__(self) -> str:
        ...


class Circle(IShape):
    def __init__(self, radius: float) -> None:
        self.radius = radius

    def accept(self, visitor: Visitor) -> float:
        return visitor.visit_circle(self)

    def __str__(self) -> str:
        return f"Circle with radius {self.radius}"


class Rectangle(IShape):
    def __init__(self, length: float, width: float) -> None:
        self.length = length
        self.width = width

    def accept(self, visitor: Visitor) -> float:
        return visitor.visit_rectangle(self)

    def __str__(self) -> str:
        return f"Rectangle with length {self.length} and width {self.width}"


class Square(IShape):
    def __init__(self, length: float) -> None:
        self.length = length

    def accept(self, visitor: Visitor) -> float:
        return visitor.visit_square(self)

    def __str__(self) -> str:
        return f"Square with length {self.length}"


class AreaVisitor(Visitor):
    """
    Visitor for calculating the area of a shape.
    """
    def visit_circle(self, circle: Circle) -> float:
        return 3.14 * circle.radius ** 2

    def visit_rectangle(self, rectangle: Rectangle) -> float:
        return rectangle.length * rectangle.width

    def visit_square(self, square: Square) -> float:
        return square.length ** 2


class PerimeterVisitor(Visitor):
    """
    Visitor for calculating the perimeter of a shape.
    """
    def visit_circle(self, circle: Circle) -> float:
        return 2 * 3.14 * circle.radius

    def visit_rectangle(self, rectangle: Rectangle) -> float:
        return 2 * (rectangle.length + rectangle.width)

    def visit_square(self, square: Square) -> float:
        return 4 * square.length


class ShapePrinter:
    area_visitor = AreaVisitor()
    perimeter_visitor = PerimeterVisitor()

    @staticmethod
    def print_shape(shape: IShape) -> None:
        area = shape.accept(ShapePrinter.area_visitor)
        perimeter = shape.accept(ShapePrinter.perimeter_visitor)
        print(f"{shape} has area {area:.2f} and perimeter {perimeter:.2f}")


def main() -> None:
    circle = Circle(5)
    rectangle = Rectangle(5, 10)
    square = Square(5)
    #
    ShapePrinter.print_shape(circle)
    ShapePrinter.print_shape(rectangle)
    ShapePrinter.print_shape(square)


if __name__ == '__main__':
    main()

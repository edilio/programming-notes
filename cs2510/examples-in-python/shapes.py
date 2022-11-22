from typing import Protocol


class IShape(Protocol):
    def area(self) -> float:
        ...

    def perimeter(self) -> float:
        ...

    def __str__(self) -> str:
        ...


class Circle(IShape):
    def __init__(self, radius: float) -> None:
        self.radius = radius

    def area(self) -> float:
        return 3.14 * self.radius ** 2

    def perimeter(self) -> float:
        return 2 * 3.14 * self.radius

    def __str__(self) -> str:
        return f"Circle with radius {self.radius}"


class Rectangle(IShape):
    def __init__(self, length: float, width: float) -> None:
        self.length = length
        self.width = width

    def area(self) -> float:
        return self.length * self.width

    def perimeter(self) -> float:
        return 2 * (self.length + self.width)

    def __str__(self) -> str:
        return f"Rectangle with length {self.length} and width {self.width}"


class Square(IShape):
    def __init__(self, length: float) -> None:
        self.length = length

    def area(self) -> float:
        return self.length ** 2

    def perimeter(self) -> float:
        return 4 * self.length

    def __str__(self) -> str:
        return f"Square with length {self.length}"


def print_shape(shape: IShape) -> None:
    area = shape.area()
    perimeter = shape.perimeter()
    print(f"{shape} has area {area:.2f} and perimeter {perimeter:.2f}")


def main() -> None:
    circle = Circle(5)
    rectangle = Rectangle(5, 10)
    square = Square(5)
    #
    print_shape(circle)
    print_shape(rectangle)
    print_shape(square)


if __name__ == '__main__':
    main()

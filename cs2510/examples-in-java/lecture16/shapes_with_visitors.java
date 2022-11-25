import java.util.function.*;
import tester.*;

interface IShapeVisitor<R> extends Function<IShape, R> {
    R visitCircle(Circle c);
    R visitRect(Rect r);
    R visitSquare(Square s);
}
interface IShape {
    <R> R accept(IShapeVisitor<R> visitor);
}

class Circle implements IShape {
    int x, y;
    int radius;
    String color;
    Circle(int x, int y, int r, String color) {
        this.x = x;
        this.y = y;
        this.radius = r;
        this.color = color;
    }

    public <R> R accept(IShapeVisitor<R> visitor) {
        return visitor.visitCircle(this);
    }
}

class Rect implements IShape {
    int x, y, w, h;
    String color;
    Rect(int x, int y, int w, int h, String color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public <R> R accept(IShapeVisitor<R> visitor) {
        return visitor.visitRect(this);
    }
}

class Square implements IShape {
    int x, y, size;
    String color;
    Square(int x, int y, int size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public <R> R accept(IShapeVisitor<R> visitor) {
        return visitor.visitSquare(this);
    }
}


class ShapeToArea implements IShapeVisitor<Double> {
    public Double visitCircle(Circle c) { return Math.PI * c.radius * c.radius; }
    public Double visitRect(Rect r) { return (double)(r.w * r.h); }

    public Double visitSquare(Square s) { return (double)(s.size * s.size); }

    // Everything from the IFunc interface:
    public Double apply(IShape s) { return s.accept(this); }
}


class ShapeToPerimeter implements IShapeVisitor<Double> {
    // calculates the perimeter of a circle
    public Double visitCircle(Circle c) {
        return 2 * Math.PI * c.radius;
    }

    // calculates the perimeter of a rect
    public Double visitRect(Rect r){
        return 2.0 * (r.h + r.w);
    }

    // calculates the perimeter of a square
    public Double visitSquare(Square s){
        return 4.0 * s.size;
    }

    public Double apply(IShape s) { return s.accept(this); }
}

class ExamplesLecture16 {
    ExamplesLecture16() {}

    IShape c1 = new Circle(10, 10, 5, "red");
    IShape c2 = new Circle(20, 20, 10, "blue");
    IShape r1 = new Rect(10, 10, 5, 5, "red");
    IShape r2 = new Rect(20, 20, 10, 10, "blue");

    IList<IShape> shapes = new ConsList<IShape>(c1,
            new ConsList<IShape>(c2,
                    new ConsList<IShape>(r1,
                            new ConsList<IShape>(r2,
                                    new MtList<IShape>()))));

    boolean testShapeToArea(Tester t) {
        return t.checkExpect(shapes.map(new ShapeToArea()),
                new ConsList<Double>(78.53981633974483,
                        new ConsList<Double>(314.1592653589793,
                                new ConsList<Double>(25.0,
                                        new ConsList<Double>(100.0,
                                                new MtList<Double>())))));
    }

}

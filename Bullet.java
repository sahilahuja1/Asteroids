import java.awt.Polygon;
import java.awt.Color;

public class Bullet extends MoveableObject {

    public Bullet(double x, double y, double a) {
        super(createShape(), x, y, 0, 10, a, true, Color.YELLOW);

        a = a * Math.PI / 180;
        xSpeed = THRUST * Math.cos(a);
        ySpeed = THRUST * Math.sin(a);
    }

    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(10, 0);
        p.addPoint(0, 0);
        p.addPoint(0, 1);
        p.addPoint(10, 1);
        return p;
    }
}

import java.awt.Color;
import java.awt.Polygon;

public class Exhaust extends MoveableObject {

    public Exhaust(double x, double y, double a) {
        super(createShape(), x, y, 1, 1, a, true, Color.ORANGE);
    }
    
    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(3, 0);
        p.addPoint(3, 1);
        p.addPoint(-3, 1);
        p.addPoint(-3,0);
        return p;
    }
    
    public void shrink() {
        if (Math.abs(shape.xpoints[0]) > Math.abs(shape.xpoints[3])) {
            shape.xpoints[0]--;
            shape.xpoints[1]--;
            drawShape.xpoints[0]--;
            drawShape.xpoints[1]--;
        } else {
            shape.xpoints[3]++;
            shape.xpoints[2]++;
            drawShape.xpoints[3]++;
            drawShape.xpoints[2]++;
        }
    }

    public void updatePosition() {
        super.updatePosition();
        if (counter % 5 == 0) shrink();
    }
}

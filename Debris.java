import java.awt.Color;
import java.awt.Polygon;

public class Debris extends MoveableObject {

    public Debris(double x, double y) {
        super(createShape(),x,y,10,10,0,true, getRandomColor());
        
        double a = Math.random() * 2 * Math.PI;
        double h = (Math.random() + 2) * 2;

        xSpeed = h * Math.cos(a);
        ySpeed = h * Math.sin(a);

    }
    
    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(3, 1);
        p.addPoint(3, -1);
        p.addPoint(-3, 1);
        p.addPoint(-3,-1);
        return p;
    }
    
    public static Color getRandomColor() {
        Color color = null;
        switch ((int) Math.round(Math.random()*6)) {
        case 0: color = Color.BLUE; break;
        case 1: color = Color.WHITE; break;
        case 2: color = Color.PINK; break;
        case 3: color = Color.ORANGE; break;
        case 4: color = Color.RED; break;
        case 5: color = Color.LIGHT_GRAY; break;
        default: color = Color.BLACK;
        }
        return color;
    }

    public void updatePosition() {
        setAngle(getAngle() + ROTATION);
        super.updatePosition();
    }
}
import java.awt.Color;
import java.awt.Polygon;

public class Rock extends MoveableObject {
    int howBig;
    int health;

    static double a = Math.random() * 2 * Math.PI;
    static double h = Math.random() * 350 + 100;

    public Rock() { 
        super(createShape(), 
              Math.cos(a) * h + 450,
              Math.sin(a) * h + 300,
              30,
              2,
              0,
              true,
              Color.RED);

        health = -1;
        howBig = 5;

        double angle = Math.random() * 2 * Math.PI;
        double height = (Math.random() + 2) * 2;
        xSpeed = height * Math.cos(angle);
        ySpeed = height * Math.sin(angle);
    }

    public Rock(double x, double y, int size, Color c) { 
        super(createShape(size), x, y, 30, 2, 0, true, c);

        howBig = size;
        health = -1;
        
        double angle = Math.random() * 2 * Math.PI;
        double height = (Math.random() + 2) * 2;
        xSpeed = height * Math.cos(angle);
        ySpeed = height * Math.sin(angle);
    }

    public static Polygon createShape() {
        return createShape(5);
    }
    
    public static Polygon createShape(int howBig) {
        Polygon p = new Polygon();
        p.addPoint(howBig * 0,howBig * -8);
        p.addPoint(howBig * 2,howBig * -4);
        p.addPoint(howBig * 0,howBig * -2);
        p.addPoint(howBig * 4,howBig * 0);
        p.addPoint(howBig * 2,howBig * 4);
        p.addPoint(howBig * 0,howBig * 2);
        p.addPoint(howBig * -2,howBig * 4);
        p.addPoint(howBig * -6,howBig * 0);
        p.addPoint(howBig * -4,howBig * -4);
        p.addPoint(howBig * -2,howBig * -6);
        return p;
    }

    public void updatePosition() {
        super.updatePosition();
        setAngle(getAngle() + ROTATION);
    } 

    public void bounceOff() {
        xSpeed = -xSpeed;
        ySpeed = -ySpeed;
    }
}

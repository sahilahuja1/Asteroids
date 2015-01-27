import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class PowerUp extends MoveableObject {

    int type;
    String letter;

    public PowerUp (double x, double y) {
        super(createShape(), x, y, 5, 1, 0, true, chooseColor());

        if (getColor() == Color.PINK) {
            letter = "S"; //score
            type = 0;
        } else if (getColor() == Color.CYAN) {
            letter = "M"; // missile
            type = 1;
        } else if (getColor() == Color.ORANGE) {
            letter = "B"; // bullet
            type = 2;
        } else if (getColor() == Color.MAGENTA) {
            letter = "L"; // lives
            type = 3;
        } else {
            letter = "error";
            type = 4;
        }
        
    }
    
    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(10, 10);
        p.addPoint(10, -10);
        p.addPoint(-10, -10);
        p.addPoint(-10, 10);
        return p;
    }
    
    public static Color chooseColor() {
        int x = (int)(Math.random() * 4);

        if (x == 0) return Color.PINK;
        else if (x == 1) return Color.CYAN;
        else if (x == 2) return Color.ORANGE;
        else if (x == 3) return Color.MAGENTA;
        return null;
    }

    public void draw (Graphics g) {
        super.draw(g);
        g.drawString(letter, ((int)getX()) - 5, ((int)getY())+5);
    }

    public void updatePosition()
    {
        super.updatePosition();
        setAngle(getAngle() + ROTATION);
    }
}

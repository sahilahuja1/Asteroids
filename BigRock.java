import java.awt.Color;
import java.awt.Polygon;

public class BigRock extends Rock {

    public BigRock() {
        super(Math.cos(Rock.a) * Rock.h + 450,
              Math.sin(Rock.a) * Rock.h + 300,
              7,
              Color.PINK);

        health = 5;
    }
    
    public static Polygon createShape() {
        return createShape(7);
    }
    
    public static Polygon createShape(int howBig)
    {
        Polygon p = new Polygon();
        p.addPoint(howBig * 4,howBig * 0);
        p.addPoint(howBig * 4,howBig * 1);
        p.addPoint(howBig * 3,howBig * 1);
        p.addPoint(howBig * 3,howBig * 2);
        p.addPoint(howBig * 2,howBig * 2);
        p.addPoint(howBig * 2,howBig * 3);
        p.addPoint(howBig * 1,howBig * 3);
        p.addPoint(howBig * 1,howBig * 4);
        p.addPoint(howBig * 0,howBig * 4);
        p.addPoint(howBig * -1,howBig * 4);
        p.addPoint(howBig * -1,howBig * 3);
        p.addPoint(howBig * -2,howBig * 3);
        p.addPoint(howBig * -2,howBig * 2);
        p.addPoint(howBig * -3,howBig * 2);
        p.addPoint(howBig * -3,howBig * 1);
        p.addPoint(howBig * -4,howBig * 1);
        p.addPoint(howBig * -4,howBig * 0);
        p.addPoint(howBig * -4,howBig * -1);
        p.addPoint(howBig * -3,howBig * -1);
        p.addPoint(howBig * -3,howBig * -2);
        p.addPoint(howBig * -2,howBig * -2);
        p.addPoint(howBig * -2,howBig * -3);
        p.addPoint(howBig * -1,howBig * -3);
        p.addPoint(howBig * -1,howBig * -4);
        p.addPoint(howBig * 0,howBig * -4);
        p.addPoint(howBig * 1,howBig * -4);
        p.addPoint(howBig * 1,howBig * -3);
        p.addPoint(howBig * 2,howBig * -3);
        p.addPoint(howBig * 2,howBig * -2);
        p.addPoint(howBig * 3,howBig * -2);
        p.addPoint(howBig * 3,howBig * -1);
        p.addPoint(howBig * 4,howBig * -1);
        return p;
    }
}

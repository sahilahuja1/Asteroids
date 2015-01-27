import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public abstract class MoveableObject {
    
    private double xPosition;
    private double yPosition;
    private double angle;

    protected double xSpeed;
    protected double ySpeed;
    
    private Color color;
    
    protected Polygon shape, drawShape; 
        // shape stays at the origin so that it can rotate properly
        // data transferred to drawShape
    
    protected final double ROTATION;
    protected final double THRUST;
    
    boolean active;
    int counter;

    public MoveableObject(Polygon shape, 
                        double xPosition, 
                        double yPosition,
                        double rotation,
                        double thrust,
                        double angle,
                        boolean active,
                        Color color) {
        this.shape = shape;
        this.drawShape = new Polygon(shape.xpoints,shape.ypoints,shape.npoints);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ROTATION = rotation;
        this.THRUST = thrust;
        this.angle = angle;
        this.active = active;
        this.color = color;
    }  
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawPolygon(drawShape);
    }

    public void updatePosition() {
        counter ++;
        xPosition += xSpeed;
        yPosition += ySpeed;
        double aRadians = angle * Math.PI / 180;

        for (int i=0; i < shape.npoints; i++)
        {
            int x = (int)Math.round(shape.xpoints[i] * Math.cos(aRadians) - 
                                shape.ypoints[i] * Math.sin(aRadians));
            int y = (int)Math.round(shape.xpoints[i] * Math.sin(aRadians) + 
                                shape.ypoints[i] * Math.cos(aRadians));
            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }
        drawShape.invalidate(); // need to do after manipulation
        drawShape.translate((int)Math.round(xPosition), 
                            (int)Math.round(yPosition));
        wrapAround();
    }

    public void wrapAround() {
        if (xPosition > 900) xPosition = 0;
        else if (xPosition < 0) xPosition = 900;
        
        if (yPosition > 600) yPosition = 0;
        else if (yPosition < 0) yPosition = 600;
    }

    public double getX() {
        return xPosition;
    }
    
    public double getY() {
        return yPosition;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }
    
    public double getAngleInRadians() {
        return angle * Math.PI / 180;
    }
    
    public static double angleToDegrees(double angle) {
        return angle * 180 / Math.PI;
    }
    
    public void reset(double xPosition, double yPosition, double angle, boolean active) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.angle = angle;
        this.active = active;
    }
    
    public static boolean collision(MoveableObject thing1, MoveableObject thing2) {
        for (int i = 0; i < thing1.drawShape.npoints; i++) {
            int x = thing1.drawShape.xpoints[i];
            int y = thing1.drawShape.ypoints[i];
            if (thing2.drawShape.contains(x, y)) return true;
        }
        for (int i = 0; i < thing2.drawShape.npoints; i++) {
            int x = thing2.drawShape.xpoints[i];
            int y = thing2.drawShape.ypoints[i];
            if (thing1.drawShape.contains(x,y)) return true;
        }
        return false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void faceObjectTowardsPoint(int x, int y) {
        if (x > getX()) setAngle(angleToDegrees(Math.atan((y - getY())/(x-getX()))));
        else setAngle(180+angleToDegrees(Math.atan((y - getY())/(x-getX()))));

    }

}

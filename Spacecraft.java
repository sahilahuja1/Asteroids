import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Spacecraft extends MoveableObject {

    int maxSpeed;
    List<Bullet> bulletList;
    List<Exhaust> exhaustList;
    List<Missile> missileList;


    public Spacecraft() {
        super(createShape(), 450, 300, 10, 1.5, 0, true, Color.GREEN);

        maxSpeed = 7;
        bulletList = new ArrayList<Bullet>();
        exhaustList = new ArrayList<Exhaust>();
        missileList = new ArrayList<Missile>();
    }
    
    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(15, 0);
        p.addPoint(-15, -15);
        p.addPoint(-15, 15);
        return p;
    }

    public void resetBullet() {
        bulletList.clear();
        missileList.clear();
    }

    public void reset() {
        super.reset(450,300,0,true);
        xSpeed = 0;
        ySpeed = 0;
    }

    public void hit() {
        active = false;
        counter = 0;
    }

    public void accelerate() {
        if(xSpeed > maxSpeed) xSpeed = maxSpeed;
        else if (xSpeed < -maxSpeed) xSpeed = -maxSpeed;
        else xSpeed += THRUST * Math.cos(getAngleInRadians());

        if(ySpeed > maxSpeed) ySpeed = maxSpeed;
        else if (ySpeed < -maxSpeed) ySpeed = -maxSpeed;
        else ySpeed += THRUST * Math.sin(getAngleInRadians());
        
        double x = (drawShape.xpoints[1] + drawShape.xpoints[2]) / 2;
        double y = (drawShape.ypoints[1] + drawShape.ypoints[2]) / 2;
        exhaustList.add(new Exhaust(x,y,getAngle()));
    }

    public void slowDown() {
        if (xSpeed > 0.25) {
            xSpeed -= 0.25;
        } else if (xSpeed < -0.25) {
            xSpeed += 0.25;
        } else {
            xSpeed = 0;
        }
        if (ySpeed > 0.25) {
            ySpeed -= 0.25;
        } else if (ySpeed < -0.25) {
            ySpeed += 0.25;
        } else {
            ySpeed = 0;
        }
    }

    public void rotateLeft() {
        setAngle(getAngle() - ROTATION);
    }

    public void rotateRight() {
        setAngle(getAngle() + ROTATION);
    }

}
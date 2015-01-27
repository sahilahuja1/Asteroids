import java.util.ArrayList;
import java.util.List;
import java.awt.Polygon;

public class Missile extends Bullet {
    ArrayList<Exhaust> exhaustList;

    public Missile (double x, double y, double a) {
        super(x,y,a);
        exhaustList = new ArrayList<Exhaust>();
    }
    
    public static Polygon createShape() {
        Polygon p = new Polygon();
        p.addPoint(10, 2);
        p.addPoint(0, 2);
        p.addPoint(0, -2);
        p.addPoint(10, -2);
        return p;
    }

    public void accelerate(double a) {
        double maxSpeed = 9;
        if (xSpeed > maxSpeed) xSpeed = maxSpeed;
        else if (xSpeed < -maxSpeed) xSpeed = -maxSpeed;
        else xSpeed = THRUST * Math.cos(getAngleInRadians());

        if (ySpeed > maxSpeed) ySpeed = maxSpeed;
        else if (ySpeed < -maxSpeed) ySpeed = -maxSpeed;
        else ySpeed = THRUST * Math.sin(getAngleInRadians());

        double x = (drawShape.xpoints[1] + drawShape.xpoints[2]) / 2;
        double y = (drawShape.ypoints[1] + drawShape.ypoints[2]) / 2;

        exhaustList.add(new Exhaust(x,y,getAngle()));
    }

    public void updatePosition() {
        super.updatePosition();
        accelerate(getAngle());
        for (int i = 0; i < exhaustList.size(); i++) {
            exhaustList.get(i).updatePosition();
            if (exhaustList.get(i).counter > 5) exhaustList.remove(i);
        }
    }
    
    public double chooseTarget(List<Rock> rockList) {
        if (rockList.size() == 0) return 0.0;
        double h = 1000;
        Rock target = rockList.get(0);
        for (Rock r : rockList) {
            double x = r.getX() - getX();
            double y = r.getY() - getY();
            if (Math.sqrt(x*x+y*y) < h) {
                h = Math.sqrt(x*x+y*y);
                target = r;
            }
        }

        setAngle(MoveableObject.angleToDegrees(
                Math.acos((target.getX() - getX())/h)));
        return getAngle();
    }
}

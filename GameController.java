import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

public class GameController {

    public Timer timer;
    public List<Rock> rockList;
    public List<Debris> explosionList;
    public List<PowerUp> powerUpList;
    
    public Spacecraft ship;
    
    public int score, lives, numBullets, numMissiles, 
        level, levelScore, counter;
    
    public Map<Integer,List<Integer>> levels;
    
    public long startTime, time;

    public GameController(Asteroids asteroid) {
        timer = new Timer(33,asteroid);      
        ship = new Spacecraft();
        
        explosionList = new ArrayList<Debris>();
        rockList = new ArrayList<Rock>();
        powerUpList = new ArrayList<PowerUp>();

        score = 0;
        lives = 5;
        numMissiles = 0;
        level = 0;
        counter = 0;
    }
    
    public void resetLevel() {
        levelScore = score;
        level ++;
        ship.resetBullet();
        ship.reset();
        explosionList.clear();
        powerUpList.clear();

        if (levels == null) readLevelData();

        List<Integer> a = levels.get(level);
        numBullets = a.get(0);
        for (int i = 0; i < a.get(1); i++) rockList.add(new Rock());
        for (int i = 0; i < a.get(2); i++)  rockList.add(new BigRock());

        startTime = System.currentTimeMillis();
        ship.updatePosition();
        for (MoveableObject r : rockList) r.updatePosition();
        counter = 0;
    }
    
    public void readLevelData() {
        try {
            levels = new HashMap<Integer,List<Integer>>();
            BufferedReader br = new BufferedReader(
                    new FileReader("LevelData.txt"));
            String level = br.readLine();
            while (level != null) {
                String[] vals = level.split(" ");
                if (vals.length != 4) {
                    System.out.println("Invalid File");
                    break;
                }
                ArrayList<Integer> a = new ArrayList<Integer>();
                for (int i = 1; i < 4; i++) a.add(Integer.parseInt(vals[i]));
                levels.put(Integer.parseInt(vals[0]), a);
                level = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Unexpected IOException");
        }
    }

    public void shipAction() {
        ship.updatePosition();
        for (int i = 0; i < ship.bulletList.size(); i++) {
            ship.bulletList.get(i).updatePosition();
            if (ship.bulletList.get(i).counter > 75) 
                ship.bulletList.remove(i);
        }
        for (MoveableObject v : ship.missileList) {
            Missile m = (Missile)v;
            m.updatePosition();
            m.setAngle(m.chooseTarget(rockList));
            for (Exhaust e : m.exhaustList) e.updatePosition();
        }
    }

    public void rockAction() { 
        for (MoveableObject r : rockList) r.updatePosition();
    }

    public void powerUpAction() {
        for (int i=0; i < powerUpList.size(); i++) {
            powerUpList.get(i).updatePosition();
            if (powerUpList.get(i).counter > 200) powerUpList.remove(i);
        }
    }
    
    public void fire() {
        if (numMissiles > 0) fireMissile();
        else fireBullet();
    }

    public void fireBullet() {
        if (ship.counter > 5 && ship.active) {
            ship.bulletList.add (new Bullet(
                    ship.getX(),ship.getY(),ship.getAngle()));
            ship.counter = 0;
            numBullets --;
        }
    }

    public void fireMissile()  {
        if (ship.counter > 20 && ship.active) {
            ship.missileList.add (new Missile(
                    ship.getX(),ship.getY(),ship.getAngle()));
            ship.counter = 0;
            numMissiles --;
        }
    }

    public void fx() {
        for (int i = 0; i < explosionList.size(); i++) {
            explosionList.get(i).updatePosition();
            if (explosionList.get(i).counter > 25) explosionList.remove(i);
        }

        for (int i = 0; i < ship.exhaustList.size(); i++) {
            ship.exhaustList.get(i).updatePosition();
            if (ship.exhaustList.get(i).counter > 5) 
                ship.exhaustList.remove(i);
        }
    }
    
    public void checkCollisions() {
        for (MoveableObject r : rockList) 
            if (MoveableObject.collision(ship, r) && ship.active) {
            ship.hit();
            lives --;
            score -= 15;
        }

        for (int i = 0; i < rockList.size(); i++)
            for (int j = 0; j < rockList.size(); j++)
                if (i != j && MoveableObject.collision(
                        rockList.get(i), rockList.get(j))) {
                    rockList.get(i).bounceOff();
                    rockList.get(i).updatePosition();
                    rockList.get(j).bounceOff();
                    rockList.get(j).updatePosition();
                }

        for (Rock r : rockList) 
            for (int b = 0; b < ship.bulletList.size(); b++)
                if (MoveableObject.collision(r, ship.bulletList.get(b))) {
                   if (r.health < 1) r.active = false;
                   else r.health --;
                   ship.bulletList.remove(b);
                }

        for (Rock r : rockList)
            for (int m = 0; m < ship.missileList.size(); m++) 
                if (MoveableObject.collision(r, ship.missileList.get(m))) {
                   if (r.health < 1) r.active = false;
                   else r.health --;

                   for (int i = 0; i < 10; i++)
                       explosionList.add(new Debris(r.getX(), r.getY()));

                   ship.missileList.remove(m);
                }
        
        for (int i = 0; i < powerUpList.size(); i++)
            if (MoveableObject.collision(powerUpList.get(i), ship)) {
               if (powerUpList.get(i).letter.equals("S")) score += 200;
               else if (powerUpList.get(i).letter.equals("L")) lives += 5;
               else if (powerUpList.get(i).letter.equals("B")) numBullets += 150;
               else if (powerUpList.get(i).letter.equals("M")) numMissiles += 5;
               powerUpList.remove(i);
            }
    }

    public void checkDestruction() {
        for (int i = 0; i < rockList.size(); i++) if(!rockList.get(i).active) {
            int c = rockList.get(i).counter;
            if (c < 50) score += 100;
            else if (c < 100) score += 50;
            else if (c >= 100) score += 25;

            for (int j = 0; j < 10; j++)
                explosionList.add(new Debris(rockList.get(i).getX(), 
                                             rockList.get(i).getY()));

            if (rockList.get(i).health == -1 && rockList.get(i).howBig > 2.5) {
                rockList.add(new Rock(rockList.get(i).getX(),
                                      rockList.get(i).getY(),
                                      rockList.get(i).howBig-1, 
                                      rockList.get(i).getColor()));
                rockList.add(new Rock(rockList.get(i).getX(),
                                      rockList.get(i).getY(),
                                      rockList.get(i).howBig-1, 
                                      rockList.get(i).getColor()));
            }

            if ((int)(Math.random() * 2) < 1) 
                powerUpList.add(new PowerUp(
                        rockList.get(i).getX(),rockList.get(i).getY()));

            rockList.remove(i);
        }
    }

    public void respawnShip() {
        if (!ship.active && ship.counter > 20 && isRespawnSafe()) ship.reset();
    }

    public boolean isRespawnSafe() {
        for (Rock r : rockList) {
            double x = r.getX() - 450;
            double y = r.getY() - 300;
            double h = Math.sqrt(x*x+y*y);
            if(h<100) return false;
        }
        return true;
    }

    public void gameEnded() {
        if (numBullets > 25)  score += 95;
        if (time < 60) score += 115;
        timer.stop();
    }
}

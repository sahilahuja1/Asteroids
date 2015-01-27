import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Asteroids extends JPanel 
        implements KeyListener, ActionListener, 
                   MouseListener, MouseMotionListener { 
        
    private boolean upKey, leftKey, rightKey, spaceKey, resetKey, paused;
    
    private GameController gameController;
    
    public Asteroids() {
        super();
        setPreferredSize(new Dimension(900, 600));
        
        gameController = new GameController(this);

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.setFocusable(true);
        this.requestFocus();
        
        gameController.resetLevel();
        gameController.timer.start();
    }
   
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 900, 600);

        if (gameController.counter < 50) paintLevelDisplay(g);
        else {
            paintLevel(g);
            paintScoreInLevel(g);
            paintLevelEnd(g);
        }
    }

    public void paintLevelDisplay(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", 0, 30));
        g.drawString("Level: " + gameController.level, 400, 300);
    }

    public void paintLevel(Graphics g) {
        if (gameController.ship.active) gameController.ship.draw(g);
        for (Rock r : gameController.rockList) r.draw(g);
        for (PowerUp pu : gameController.powerUpList) pu.draw(g);
        for (Bullet b : gameController.ship.bulletList) b.draw(g);
        for (Exhaust e : gameController.ship.exhaustList) e.draw(g);
        for (Missile m : gameController.ship.missileList) {
            m.draw(g);
            for (Exhaust e : ((Missile) m).exhaustList) e.draw(g);
        }
        for (Debris d : gameController.explosionList) d.draw(g);
    }
 
    public void paintScoreInLevel(Graphics g) {
        g.setFont(new Font("Times New Roman", 0, 15));
        g.setColor(Color.white);
        g.drawString("Score " + gameController.score, 825,590);
        g.drawString("Lives " + gameController.lives, 825,570);
        if (gameController.numMissiles < 1) g.drawString("Bullets " + 
        gameController.numBullets, 825, 550);
        else g.drawString("Missiles" + gameController.numMissiles, 825, 550);
        g.drawString("Time " + gameController.time, 825, 530);
        g.drawString("Level " + gameController.level, 825,510);
    }

    public void paintLevelEnd(Graphics g) {
        if (gameController.level == 11) {
            g.setFont(new Font("Times New Roman", 0, 30));
            g.drawString("Congratulations! You have beat the game!", 
                         400, 300);
            gameController.timer.stop();
        } else if (gameController.rockList.isEmpty()) {
            gameController.timer.stop();
            gameController.ship.hit();
            g.setColor(Color.white);
            g.setFont(new Font("Times New Roman", 0, 30));
            g.drawString("YOU WIN! Press enter for next level.", 400, 300);
            g.setFont(new Font("Times New Roman", 0, 24));
            g.drawString("Level Score: " + 
                    ((Integer)(gameController.score - 
                      gameController.levelScore)).toString(), 400, 330);
            g.drawString("Total Score: " + 
                      (Integer)(gameController.score), 400, 360);
        } else if (gameController.lives == 0 || gameController.numBullets == 0) {
            gameController.ship.hit();
            g.setColor(Color.white);
            g.setFont(new Font("Times New Roman", 0, 30));
            g.drawString("GAME OVER! Press 'r' to reset.", 450, 300);
        }
    }
    
    public void update(Graphics g) {
        super.update(g);
        keyCheck();
        if (gameController.counter > 50){
            gameController.shipAction();
            gameController.fx();
            gameController.rockAction();
            gameController.powerUpAction();
        } else gameController.counter++;
        
        gameController.time = (System.currentTimeMillis() - 
                               gameController.startTime)/1000;
        gameController.respawnShip();
        gameController.checkCollisions();
        gameController.checkDestruction();
    }

    public void actionPerformed(ActionEvent e) {
        keyCheck();
        if (gameController.counter > 50){
            gameController.shipAction();
            gameController.fx();
            gameController.rockAction();
            gameController.powerUpAction();
        } else gameController.counter++;
        
        gameController.time = (System.currentTimeMillis() - 
                               gameController.startTime)/1000;
        
        repaint();
        gameController.respawnShip();
        gameController.checkCollisions();
        gameController.checkDestruction();
    }


    public void keyCheck() {
        if (upKey) gameController.ship.accelerate(); 
        else gameController.ship.slowDown();
        
        if (leftKey) gameController.ship.rotateLeft();
        if (rightKey) gameController.ship.rotateRight();
        if (spaceKey) gameController.fire();
        if (resetKey) {
            gameController.lives = 5;
            gameController.score = 0;
            gameController.level = 0;
            gameController.rockList.clear();
            gameController.resetLevel();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKey = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftKey = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) upKey = true;
        if(e.getKeyCode() == KeyEvent.VK_SPACE) spaceKey = true;
        if(e.getKeyCode() == KeyEvent.VK_R) resetKey = true;
        if (e.getKeyCode() == KeyEvent.VK_P)
            if (!paused) {
                paused = true;
                gameController.timer.stop();
            } else if (!gameController.timer.isRunning()) {
                gameController.timer.start();
                paused = false;
            }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            if (!gameController.timer.isRunning()) {
                gameController.resetLevel();
                gameController.timer.start();
            }
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKey = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) leftKey = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) upKey = false;
        if(e.getKeyCode() == KeyEvent.VK_SPACE) spaceKey = false;
        if(e.getKeyCode() == KeyEvent.VK_R) resetKey = false;
    }

    public void keyTyped(KeyEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e)  {}
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        gameController.ship.faceObjectTowardsPoint(e.getX(), e.getY());
        repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {        
        gameController.ship.accelerate();
        gameController.ship.faceObjectTowardsPoint(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) { upKey = true; }
    
    @Override
    public void mouseReleased(MouseEvent e) { upKey = false; }

}

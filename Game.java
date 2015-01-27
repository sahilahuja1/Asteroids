import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.JTextComponent;

public class Game extends JFrame implements Runnable {
    
    JPanel asteroids;
    JPanel startScreen;
    
    String instructionsText = "Instructions: Destroy all of the Asteroids "
            + "without loosing all of your bullets or all of your lives! "
            + "Catch as many power-ups as possible - they will help you "
            + "on your mission. Use the space bar to shoot and arrow keys "
            + "or mouse to move your spaceship. Good Luck! \n\n"
            + "Cool features: \n"
            + "- Level data read from files \n"
            + "- Check out the cool animations! \n\n";
    
    public Game() {
        super("Asteroids");
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());
        addEverything();
        this.pack();
    }
    
    public void addEverything() {
        JTextComponent title = new JTextField("Asteroids! By: Sahil Ahuja");
        title.setBackground(Color.BLACK);
        title.setFont(new Font("Times New Roman", 0, 60));
        title.setForeground(Color.WHITE);
        title.setBorder(new BasicBorders.MarginBorder());
        title.setSize(300, 100);
        title.setMargin(new Insets(10,30,10,30));
        title.setEditable(false);
        add(title,BorderLayout.NORTH);
        
        JTextArea instructions = new JTextArea(instructionsText,10,10);
        instructions.setBackground(Color.BLACK);
        instructions.setFont(new Font("Times New Roman", 0, 30));
        instructions.setForeground(Color.WHITE);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setSize(300,300);
        instructions.setEditable(false);
        instructions.setMargin(new Insets(10,30,10,30));
        add(instructions,BorderLayout.CENTER);

        JButton b = new JButton("Play");
        b.setBackground(Color.BLACK);
        b.setMnemonic(KeyEvent.VK_ENTER);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame playGame = new JFrame();
                playGame.add(new Asteroids());
                playGame.setSize(new Dimension(900,600));
                playGame.setVisible(true);
            }                
        });
        
        this.add(b,BorderLayout.SOUTH);
    }
            
    @Override
    public void run() {
        Game x = new Game();
        x.setVisible(true);  
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}

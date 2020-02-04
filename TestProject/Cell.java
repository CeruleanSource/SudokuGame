
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Abdullah Mohamed
 */
public class Cell extends JPanel {

    int row, col;
    char token = ' ';
    char key = ' ', previousKey = ' ';
    boolean clicked = false;
    boolean unique = true;
    int validCount = 0;
    Font f = new Font("Dialog", Font.PLAIN, 50);
    Scanner in = new Scanner(System.in);
    char readChar;
    Color newColor = new Color(180, 180, 180);
    Color focusColor = new Color(150, 170, 200);
    Color originalColor;
    JLabel keyPrompt = new JLabel("Enter Key");

    public Cell(int row, int col) {
        this.row = row; // note starting row/col is 1 and ending row/col is 9
        this.col = col;
        originalColor = this.getBackground();
        this.setBorder(new LineBorder(Color.black, 1));
        this.setFocusable(true);
    }

    public char getToken() {
        return token;
    }

    public void setToken(char token) {
        this.token = token;
        repaint();
    }
    
    public int getValidCount(){
        return validCount;
    }
    
    public void incrementValidCount(){
        validCount ++;
    }

    public Border currentBorder() {
        if (row % 3 == 0 && (col % 3 == 0) && (row % 3 == 0 && (col != 9)) && (col % 3 == 0 && (row != 9))) {
            return BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK);
        } else if (row % 3 == 0 && row != 9) {
            return BorderFactory.createMatteBorder(1, 1, 5, 1, Color.BLACK);
        } else if (col % 3 == 0 && col != 9) {
            return BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK);
        } else {
            return new LineBorder(Color.BLACK, 1);
        }
    }

    public void setThickBorder() {
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }

    public void revertBorder() {
        this.setBorder(currentBorder());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(f);

        if (clicked && key <= '9' && key >= '1') {
            g.drawString(Character.toString(key), 50, 70);
            clicked = false;
            setBackground(originalColor);
        } else if (token <= '9' && token >= '1') {
            g.drawString(Character.toString(token), 50, 70);
        }
    }

    @Override
    public boolean isFocusTraversable() {
        return true;
    }

    public class thisKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            remove(keyPrompt);
            key = e.getKeyChar();
            token = key;
            repaint();
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public class thisMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if (token == ' ') {
                clicked = true;
                setBackground(focusColor);
                //JLabel enterKeyLabel = new JLabel("Enter key");
            } else {
                System.out.println("Cell is already occupied.");
            }
            setFocusable(true);
            requestFocus();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setBackground(newColor);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setBackground(originalColor);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (token == ' '){
            add(keyPrompt);
            }
            setThickBorder();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (token == ' ' && clicked == false){
            remove(keyPrompt);
            }
            revertBorder();
        }
    }
}

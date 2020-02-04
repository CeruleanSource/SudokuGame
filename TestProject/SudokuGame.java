
import java.awt.GridLayout;
import javax.swing.JFrame;

public class SudokuGame {
    JFrame game;
    Panel panel;
    
    public SudokuGame(){
        // JFrame initialization
        game = new JFrame();
        game.setSize(1500, 1200);
        game.setTitle("Sudoku Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new GridLayout(1, 1, 0, 0));
        
        // Panel Setup
        panel = new Panel();
        game.add(panel);  
        game.setVisible(true);
    }
    
    public static void main(String[] args) {
        SudokuGame sudoku = new SudokuGame();
    }
    
}

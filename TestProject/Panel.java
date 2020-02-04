
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Abdullah
 */
public class Panel extends JPanel {

    Cell cells[][] = new Cell[9][9];
    int globalRow, globalCol, randomRow, randomCol;
    char charCells[][] = {{'1', '5', '2', '4', '8', '9', '3', '7', '6'},
    {'7', '3', '9', '2', '5', '6', '8', '4', '1'},
    {'4', '6', '8', '3', '7', '1', '2', '9', '5'},
    {'3', '8', '7', '1', '2', '4', '6', '5', '9'},
    {'5', '9', '1', '7', '6', '3', '4', '2', '8'},
    {'2', '4', '6', '8', '9', '5', '7', '1', '3'},
    {'9', '1', '4', '6', '3', '7', '5', '8', '2'},
    {'6', '2', '5', '9', '4', '8', '1', '3', '7'},
    {'8', '7', '3', '5', '1', '2', '9', '6', '4'}};
    char segment[][] = new char[9][9];
    char digit[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    boolean digitChecked[] = {false, false, false, false, false, false, false, false, false};
    boolean createGame = false;
    int trueCount = 0, winCheckCount = 0;
    JButton solveButton = new JButton("Solve");
    JButton winCheckButton = new JButton("Win Check");
    JButton generateGameButton = new JButton("New Game");
    Font f = new Font("Times New Roman", Font.PLAIN, 29);
    JLabel solvedLabel = new JLabel("SOLVED");
    JLabel winPrint = new JLabel("YOU WIN");
    JLabel losePrint = new JLabel("Incorrect");
    Color originalColor;
    Random random = new Random();

    public Panel() {
        originalColor = this.getBackground();
        setLayout(new GridLayout(10, 10, 0, 0));
        setBorder(new LineBorder(Color.black, 1));

        // Creating cells & adding Borders to Panel
        for (int row = 1; row < 10; row++) {
            for (int col = 1; col < 10; col++) {
                // Initialize cells
                this.add(cells[row - 1][col - 1] = new Cell(row, col));

                // Add mouselistener to cells
                cells[row - 1][col - 1].addMouseListener(cells[row - 1][col - 1].new thisMouseListener());

                // Add keylistener to cells
                cells[row - 1][col - 1].addKeyListener(cells[row - 1][col - 1].new thisKeyListener());
                // Thick borders
                if (row % 3 == 0 && (col % 3 == 0) && (row % 3 == 0 && (col != 9)) && (col % 3 == 0 && (row != 9))) {
                    cells[row - 1][col - 1].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
                } else if (row % 3 == 0 && row != 9) {
                    cells[row - 1][col - 1].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.BLACK));
                } else if (col % 3 == 0 && col != 9) {
                    cells[row - 1][col - 1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK));
                }
            }
        }
        updateCellArray(charCells);

        winCheckButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateSegmentArray(charCells);
                if (winCheck()) {
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            cells[row][col].setBackground(Color.green);
                        }
                    }
                } else {
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            cells[row][col].setBackground(Color.red);
                        }
                    }
                }
            }
        });

        solveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        cells[row][col].setBackground(originalColor);
                    }
                }
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        solve(row, col, charCells, segment);
                    }
                }
                updateCellArray(charCells);
            }

        });
        generateGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        cells[row][col].setBackground(originalColor);
                    }
                }
                createGame();

            }
        });

        solveButton.setFont(f);
        winCheckButton.setFont(f);
        generateGameButton.setFont(new Font("Times New Roman", Font.PLAIN, 29));
        add(winCheckButton);
        add(solveButton);
        add(generateGameButton);
        updateCellArray(charCells);
        createGame();
        setFocusable(true);
    } // Panel Constructor

    // Put each charCell[][] element into the corresponding cells[][] element
    // Update charCell Array
    public void updateCellArray(char[][] charCells) {
        // update cell tokens
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setToken(charCells[row][col]);
            }
        }
    } // cellArray() method 

    public char[][] updateSegmentArray(char[][] charCells) {
        // update segment array
        int endCol = 3, startCol = 0;
        int cellsRow = 0, cellsCol = 0;
        for (int row = 0; row < 9 && cellsRow < 9; row++) {
            for (int col = startCol; col < endCol; col++) {
                segment[cellsRow][cellsCol] = charCells[row][col];
                cellsCol++;
            }
            if (cellsCol == 9) {
                cellsRow++;
                cellsCol = 0;
            }
            if (row == 8 && startCol != 6) {
                row = -1;
                startCol += 3;
                endCol += 3;
            }
        }
        return segment;
    }

    public void createGame() {
        int randomEnd = random.nextInt(50) + 10;
        updateCellArray(charCells);
        for (int i = 0; i < randomEnd; i++) {
            randomRow = random.nextInt(9);
            randomCol = random.nextInt(9);
            cells[randomRow][randomCol].setToken(' ');
        }

    }

    public boolean checkUniqueSolution(int row, int col) {
        System.out.println(row + "," + col);
        if (cells[row][col].getValidCount() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean solve(int row, int col, char[][] solvedBoard, char[][] segmentArray) {
        globalRow = row;
        globalCol = col;
        cells[row][col].key = ' ';

        for (int num = 1; num <= 9; num++) {
            if (validPlacement(row, col, solvedBoard, Character.forDigit(num, 10), segmentArray)) {
                cells[row][col].setBackground(Color.green);
                solvedBoard[row][col] = Character.forDigit(num, 10);
                cells[row][col].setToken(Character.forDigit(num, 10));
                segmentArray = updateSegmentArray(solvedBoard);
                //System.out.println("cells["+row+"]["+col+"].validcount:"+cells[row][col].getValidCount());
            }
        }
        if (row == 8 && col == 8) {
            return true;
        } else if (col == 8) {
            row++;
            col = 0;
        } else {
            return solve(row, col + 1, solvedBoard, segmentArray);
        }

        return false;
    }

    public boolean validPlacement(int row, int col, char solvedBoard[][], char num, char[][] segmentArray) {
        // Check if number is in the column
        for (int column = 0; column < 9; column++) {
            if (column == col) {
            } else if (solvedBoard[row][column] == num) {
                return false;
            }
        }

        // Check if number is in the row
        for (int Row = 0; Row < 9; Row++) {
            if (Row == row) {
            } else if (solvedBoard[Row][col] == num) {
                return false;
            }
        }

        // Check if number is in the segment
        if (row < 3 && col < 3) {
            if (checkSegment(0, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 6 && col < 3) {
            if (checkSegment(1, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 9 && col < 3) {
            if (checkSegment(2, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 3 && col < 6) {
            if (checkSegment(3, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 6 && col < 6) {
            if (checkSegment(4, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 9 && col < 6) {
            if (checkSegment(5, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 3 && col < 9) {
            if (checkSegment(6, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 6 && col < 9) {
            if (checkSegment(7, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        } else if (row < 9 && col < 9) {
            if (checkSegment(8, solvedBoard, num, segmentArray) == true) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSegment(int segmentNum, char solvedBoard[][], char num, char[][] segmentArray) {
        int count = 0;
        for (int col = 0; col < 9; col++) {
            if (segmentArray[segmentNum][col] == num) {
                count++;
            }
        }
        if (count >= 2) {
            return true;
        }
        return false;
    }

    public boolean winCheck() {
        // Check Rows
        winCheckCount = 0;
        for (int row = 0; row < 9; row++) {
            setdigitChecked();
            trueCount = 0;
            for (int col = 0; col < 9; col++) {
                checkCellDigit(col, row);
            }
            if (trueCount == 9) {
                winCheckCount++;
            }
        }
        if (winCheckCount == 9) {
            winCheckCount = 1;
        }

        // Check Columns
        for (int col = 0; col < 9; col++) {
            setdigitChecked();
            trueCount = 0;
            for (int row = 0; row < 9; row++) {
                checkCellDigit(row, col);
            }
            if (trueCount == 9) {
                winCheckCount++;
            }
        }
        if (winCheckCount == 10) {
            winCheckCount = 2;
        }

        // Check 9 segments
        for (int row = 0; row < 9; row++) {
            setdigitChecked();
            trueCount = 0;
            for (int col = 0; col < 9; col++) {
                for (int count = 0; count < 9; count++) {
                    if (segment[row][col] == digit[count] && digitChecked[count] == false) {
                        trueCount++;
                        digitChecked[count] = true;
                    }
                }
                //System.out.println("trueCount: " + trueCount);
                if (trueCount == 9) {
                    winCheckCount++;
                }
            }
        }
        if (winCheckCount == 11) {
            return true;
        }
        //System.out.println(winCheckCount);
        return false;
    } // check for win

    public void setdigitChecked() {
        for (int count = 0; count < 9; count++) {
            digitChecked[count] = false;
        }
    }

    public void checkCellDigit(int row, int col) {
        for (int count = 0; count < 9; count++) {
            if (cells[col][row].getToken() == digit[count] && digitChecked[count] == false) {
                trueCount++;
                digitChecked[count] = true;
            }
        }
    }
} // Panel Class

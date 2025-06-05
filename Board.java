
import java.awt.*;
import javax.swing.*;

public class Board extends JFrame implements ModelObserver {

    private View view;
    private JPanel boardFrame;
    private Square[] square = new Square[40];
    private int startVal;

    // Constructor to initialize the board with squares
    public Board(View view, JPanel boardFrame) {
        this.view = view;
        this.boardFrame = boardFrame;

        int noSq = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                // Initialize each square and add it to the board frame
                square[noSq] = new Square(noSq, row, col);
                square[noSq].setPreferredSize(new Dimension(100, 100));
                boardFrame.add(square[noSq]);
                noSq++;
            }
        }
        view.refreshView();
    }

    // Method to initialize the board with specific pieces
    public void initializeBoard() {
        int noSq = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                // Replace squares with specific pieces based on their position
                if (square[noSq].getNoSquare() > 4 && square[noSq].getNoSquare() < 10) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new BlueRam(square, noSq, row, col, false);
                } else if (square[noSq].getNoSquare() == 2) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new BlueSau(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 1 || square[noSq].getNoSquare() == 3) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new BlueBiz(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 0) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new BlueTor(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 4) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new BlueXor(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() > 29 && square[noSq].getNoSquare() < 35) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new RedRam(square, noSq, row, col, false);
                } else if (square[noSq].getNoSquare() == 37) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new RedSau(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 36 || square[noSq].getNoSquare() == 38) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new RedBiz(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 35) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new RedXor(square, noSq, row, col);
                } else if (square[noSq].getNoSquare() == 39) {
                    boardFrame.remove(square[noSq]);
                    square[noSq] = new RedTor(square, noSq, row, col);
                }
                noSq++;
            }
        }
    }

    // Method to reset the board to its initial state
    public void restartPos() {
        int noSq = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                // Remove and reinitialize each square
                boardFrame.remove(square[noSq]);
                square[noSq] = new Square(noSq, row, col);
                boardFrame.add(square[noSq]);
                noSq++;
            }
        }
        view.refreshView();
    }

    // Method to refresh the board based on the moves made
    public void refreshBoard(boolean redMoved, boolean blueMoved) {
        if (startVal == 1) {
            if (redMoved || blueMoved) {
                // Remove all squares from the board frame
                for (int a = 0; a < 40; a++) {
                    boardFrame.remove(square[a]);
                }
                int noSq = 39;
                for (int row = 7; row >= 0; row--) {
                    for (int col = 4; col >= 0; col--) {
                        // Add squares back to the board frame in reverse order
                        boardFrame.add(square[noSq]);
                        // Set the icon facing direction based on the piece type and move
                        if (square[noSq].getName().equals("RedRam") && redMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("RedRam") && blueMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("RedSau") && redMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("RedSau") && blueMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("BlueRam") && redMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("BlueRam") && blueMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueSau") && redMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("BlueSau") && blueMoved) {
                            square[noSq].setIconFacingDown();
                        }
                        noSq--;
                    }
                }
            } else {
                // Remove all squares from the board frame
                for (int a = 0; a < 40; a++) {
                    boardFrame.remove(square[a]);
                }
                int noSq = 0;
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 5; col++) {
                        // Add squares back to the board frame in normal order
                        boardFrame.add(square[noSq]);
                        // Set the icon facing direction based on the piece type
                        if (square[noSq].getName().equals("RedRam")) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("RedSau")) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("BlueRam")) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueSau")) {
                            square[noSq].setIconFacingDown();
                        }
                        noSq++;
                    }
                }
            }
            view.refreshView();
        } else if (startVal == 2) {
            if (redMoved || blueMoved) {
                // Remove all squares from the board frame
                for (int a = 0; a < 40; a++) {
                    boardFrame.remove(square[a]);
                }
                int noSq = 0;
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 5; col++) {
                        // Add squares back to the board frame in normal order
                        boardFrame.add(square[noSq]);
                        // Set the icon facing direction based on the piece type and move
                        if (square[noSq].getName().equals("RedRam") && blueMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("RedRam") && redMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("RedSau") && blueMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("RedSau") && redMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueRam") && blueMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueRam") && redMoved) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("BlueSau") && blueMoved) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueSau") && redMoved) {
                            square[noSq].setIconFacingUp();
                        }
                        noSq++;
                    }
                }
            } else {
                // Remove all squares from the board frame
                for (int a = 0; a < 40; a++) {
                    boardFrame.remove(square[a]);
                }
                int noSq = 39;
                for (int row = 7; row >= 0; row--) {
                    for (int col = 4; col >= 0; col--) {
                        // Add squares back to the board frame in reverse order
                        boardFrame.add(square[noSq]);
                        // Set the icon facing direction based on the piece type
                        if (square[noSq].getName().equals("RedRam")) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueRam")) {
                            square[noSq].setIconFacingUp();
                        } else if (square[noSq].getName().equals("RedSau")) {
                            square[noSq].setIconFacingDown();
                        } else if (square[noSq].getName().equals("BlueSau")) {
                            square[noSq].setIconFacingUp();
                        }
                        noSq--;
                    }
                }
            }
            view.refreshView();
        }
    }

    // Getter for square array
    public Square[] getSquare() {
        return square;
    }

    // Getter for board frame
    public JPanel getBoardFrame() {
        return boardFrame;
    }

    // Setter for start value
    public void setStartVal(int val) {
        startVal = val;
    }

    // Method to handle model updates
    @Override
    public void onModelUpdate(boolean rm, boolean bm) {
        refreshBoard(rm, bm);
    }
}


import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Model {

    private View view;
    private Square[] square; // Passing the squares
    private ArrayList<ModelObserver> observers; // Array of observers for Model
    private int turn = 0; // Current turn
    private boolean redMoved = false; // Check for red turn
    private boolean blueMoved = false; // Check for blue turn
    private int startVal; // Value which determine who starts first

    public Model(View view) {

        this.view = view;
        this.square = view.getBoard().getSquare();
        this.observers = new ArrayList<ModelObserver>();
    }

    public void addObserver(ModelObserver observer) { // Allow to add observer to model
        observers.add(observer);
    }

    public void notifyObservers(boolean rm, boolean bm) { // Notify observer on model update
        for (ModelObserver observer : observers) {
            observer.onModelUpdate(rm, bm);
        }
    }

    public void notifyTurn(String currentPlayer) { // Notify menu on current turn update
        view.getMenu().updateTurnDisplay(currentPlayer);
    }

    public void notifyCurrentTurnNumber(int currentTurn) { // Notify menu on current number of turn
        view.getMenu().updateNoTurnDisplay(currentTurn);
    }

    public void notifyMoveHistoryMove(int from, int to) { // Notify move history on Movement
        view.getMenu().updateMoveHistoryMove(from, to);
    }

    public void notifyMoveHistoryEat(int from, int to) { // Notify move history on Eat 
        view.getMenu().updateMoveHistoryEat(from, to);
    }

    public void game(int startFirstVal) { // Initialize game according to the start value

        if (startFirstVal == 1) { //RED START
            disableBlue();
            disableSquare();
            startVal = 1;
            notifyTurn("Red");

        } else if (startFirstVal == 2) { //BLUE START
            disableRed();
            disableSquare();
            startVal = 2;
            notifyTurn("Blue");

        }
        notifyCurrentTurnNumber(turn);
    }

    public void pieceClicked(int noSquare) { // Handle piece being clicked first time
        if (square[noSquare].getType().equals("RedPiece")) {
            disableRed();
            //----------------------------------------------------------------------------------------------------
            // RED RAM SECTION
            //----------------------------------------------------------------------------------------------------
            if (square[noSquare].getName().equals("RedRam")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                square[noSquare].checkRotation();
                if (!square[noSquare].getRotate()) {
                    for (int a = 0; a < 40; a++) {
                        if (square[a].getCol() == square[noSquare].getCol()) {
                            if (square[a].getRow() == square[noSquare].getRow() - 1 && !square[a].getType().equals("RedPiece")) { // Row for ram's available moves in the same column
                                square[a].setEnabled(true);
                                square[a].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(a);
                                handleSquareClick(noSquare, a);
                            }
                        }
                    }
                } else if (square[noSquare].getRotate()) {
                    for (int a = 0; a < 40; a++) {
                        if (square[a].getCol() == square[noSquare].getCol()) {
                            if (square[a].getRow() == square[noSquare].getRow() + 1 && !square[a].getType().equals("RedPiece")) {// Row for ram's available moves in the same column
                                square[a].setEnabled(true);
                                square[a].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(a);
                                handleSquareClick(noSquare, a);
                            }
                        }
                    }
                }
            } //----------------------------------------------------------------------------------------------------
            // RED TOR SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("RedTor")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));

                int offsetPositive = 1;
                int offsetNegative = -1;

                boolean verticalTop = false;
                boolean verticalBottom = false;
                boolean horizontalLeft = false;
                boolean horizontalRight = false;

                int noSquareLoop = 0;

                while (square[noSquare].getRow() + offsetNegative >= 0 && !verticalTop) { // Check for topside of piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol()) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                verticalTop = true;
                            } else {
                                verticalTop = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getRow() + offsetPositive < 8 && !verticalBottom) { // Check for bottomside of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol()) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                verticalBottom = true;
                            } else {
                                verticalBottom = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetNegative >= 0 && !horizontalLeft) { // Check for leftside of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow()) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                horizontalLeft = true;
                            } else {
                                horizontalLeft = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetPositive < 5 && !horizontalRight) { // Check fpr the rightside of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow()) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);

                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                horizontalRight = true;
                            } else {
                                horizontalRight = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
            } //----------------------------------------------------------------------------------------------------
            // RED XOR SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("RedXor")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));

                int offsetPositive = 1;
                int offsetNegative = -1;

                boolean northwest = false;
                boolean northeast = false;
                boolean southwest = false;
                boolean southeast = false;

                int noSquareLoop = 0;

                while (square[noSquare].getRow() + offsetNegative >= 0 && square[noSquare].getCol() + offsetNegative >= 0 && !northwest) { // Check for topleft side diagonally of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                northwest = true;
                            } else {
                                northwest = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getRow() + offsetNegative >= 0 && square[noSquare].getCol() + offsetPositive < 5 && !northeast) {  // Check for topright side diagonally of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                northeast = true;
                            } else {
                                northeast = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetNegative >= 0 && square[noSquare].getRow() + offsetPositive < 8 && !southwest) {  // Check for bottomleft side diagonally of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                southwest = true;
                            } else {
                                southwest = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetPositive < 5 && square[noSquare].getRow() + offsetPositive < 8 && !southeast) {  // Check for bottomright side diagonally of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("RedPiece")) {
                                southeast = true;
                            } else {
                                southeast = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
            } //----------------------------------------------------------------------------------------------------
            // RED BIZ SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("RedBiz")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                for (int a = 0; a < 40; a++) {
                    if (square[a].getCol() == square[noSquare].getCol() - 2 || square[a].getCol() == square[noSquare].getCol() + 2) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("RedPiece")) { // Check for L shape top side of piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    } else if (square[a].getCol() == square[noSquare].getCol() - 1 || square[a].getCol() == square[noSquare].getCol() + 1) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 2 || square[a].getRow() == square[noSquare].getRow() + 2) && !square[a].getType().equals("RedPiece")) {// Check for L shape bottom side of piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    }
                }
            } //----------------------------------------------------------------------------------------------------
            // RED SAU SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("RedSau")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                for (int a = 0; a < 40; a++) {
                    if (square[a].getCol() == square[noSquare].getCol() - 1 || square[a].getCol() == square[noSquare].getCol() + 1) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("RedPiece")) { // Check for available moves around the piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    } else if (square[a].getCol() == square[noSquare].getCol()) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("RedPiece")) {
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    }
                }
            }
        } else if (square[noSquare].getType().equals("BluePiece")) {
            disableBlue();
            //----------------------------------------------------------------------------------------------------
            // BLUE RAM SECTION
            //----------------------------------------------------------------------------------------------------
            if (square[noSquare].getName().equals("BlueRam")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                square[noSquare].checkRotation();
                if (!square[noSquare].getRotate()) {
                    for (int a = 0; a < 40; a++) {
                        if (square[a].getCol() == square[noSquare].getCol()) {
                            if (square[a].getRow() == square[noSquare].getRow() + 1 && !square[a].getType().equals("BluePiece")) { // Row for ram's available moves in the same column
                                square[a].setEnabled(true);
                                square[a].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(a);
                                handleSquareClick(noSquare, a);
                            }
                        }
                    }
                } else if (square[noSquare].getRotate()) {
                    for (int a = 0; a < 40; a++) {
                        if (square[a].getCol() == square[noSquare].getCol()) {
                            if (square[a].getRow() == square[noSquare].getRow() - 1 && !square[a].getType().equals("BluePiece")) { // Row for ram's available moves in the same column
                                square[a].setEnabled(true);
                                square[a].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(a);
                                handleSquareClick(noSquare, a);
                            }
                        }
                    }
                }
            } //----------------------------------------------------------------------------------------------------
            // BLUE TOR SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("BlueTor")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));

                int offsetPositive = 1;
                int offsetNegative = -1;

                boolean verticalTop = false;
                boolean verticalBottom = false;
                boolean horizontalLeft = false;
                boolean horizontalRight = false;

                int noSquareLoop = 0;

                while (square[noSquare].getRow() + offsetPositive < 8 && !verticalTop) {// Check for topside of piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol()) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                verticalTop = true;
                            } else {
                                verticalTop = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getRow() + offsetNegative >= 0 && !verticalBottom) {// Check for bottomside of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol()) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                verticalBottom = true;
                            } else {
                                verticalBottom = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetPositive < 5 && !horizontalLeft) {// Check for leftside of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow()) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                horizontalLeft = true;
                            } else {
                                horizontalLeft = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetNegative >= 0 && !horizontalRight) {// Check fpr the rightside of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow()) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                horizontalRight = true;
                            } else {
                                horizontalRight = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
            } //----------------------------------------------------------------------------------------------------
            // BLUE XOR SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("BlueXor")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));

                int offsetPositive = 1;
                int offsetNegative = -1;

                boolean northwest = false;
                boolean northeast = false;
                boolean southwest = false;
                boolean southeast = false;

                int noSquareLoop = 0;

                while (square[noSquare].getRow() + offsetPositive < 8 && square[noSquare].getCol() + offsetPositive < 5 && !northwest) {// Check for topleft side diagonally of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                northwest = true;
                            } else {
                                northwest = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getRow() + offsetPositive < 8 && square[noSquare].getCol() + offsetNegative >= 0 && !northeast) { // Check for topright side diagonally of the piece
                    if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                        if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                northeast = true;
                            } else {
                                northeast = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetPositive < 5 && square[noSquare].getRow() + offsetNegative >= 0 && !southwest) {// Check for bottomleft side diagonally of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetPositive) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetPositive++;
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                southwest = true;
                            } else {
                                southwest = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
                offsetNegative = -1;
                offsetPositive = 1;
                while (square[noSquare].getCol() + offsetNegative >= 0 && square[noSquare].getRow() + offsetNegative >= 0 && !southeast) {// Check for bottomright side diagonally of the piece
                    if (square[noSquareLoop].getRow() == square[noSquare].getRow() + offsetNegative) {
                        if (square[noSquareLoop].getCol() == square[noSquare].getCol() + offsetNegative) {
                            if (square[noSquareLoop].getType().equals("Square")) {
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleMoveClick(noSquare, noSquareLoop);
                                offsetNegative--;
                            } else if (square[noSquareLoop].getType().equals("BluePiece")) {
                                southeast = true;
                            } else {
                                southeast = true;
                                square[noSquareLoop].setEnabled(true);
                                square[noSquareLoop].setBackground(new Color(255, 214, 90));
                                removeSingleSquareListeners(noSquareLoop);
                                handleEatClick(noSquare, noSquareLoop);
                            }
                        }
                    }
                    noSquareLoop = (noSquareLoop + 1) % 40;
                }
            } //----------------------------------------------------------------------------------------------------
            // BLUE BIZ SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("BlueBiz")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                square[noSquare].setBackground(new Color(255, 131, 131));
                for (int a = 0; a < 40; a++) {
                    if (square[a].getCol() == square[noSquare].getCol() - 2 || square[a].getCol() == square[noSquare].getCol() + 2) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("BluePiece")) { // Check for L shape top side of piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    } else if (square[a].getCol() == square[noSquare].getCol() - 1 || square[a].getCol() == square[noSquare].getCol() + 1) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 2 || square[a].getRow() == square[noSquare].getRow() + 2) && !square[a].getType().equals("BluePiece")) {// Check for L shape bottom side of piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    }
                }
            } //----------------------------------------------------------------------------------------------------
            // BLUE SAU SECTION
            //----------------------------------------------------------------------------------------------------
            else if (square[noSquare].getName().equals("BlueSau")) {
                square[noSquare].setEnabled(true);
                square[noSquare].setBackground(new Color(255, 131, 131));
                for (int a = 0; a < 40; a++) {
                    if (square[a].getCol() == square[noSquare].getCol() - 1 || square[a].getCol() == square[noSquare].getCol() + 1) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("BluePiece")) {// Check for available moves around the piece
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    } else if (square[a].getCol() == square[noSquare].getCol()) {
                        if ((square[a].getRow() == square[noSquare].getRow() - 1 || square[a].getRow() == square[noSquare].getRow() + 1) && !square[a].getType().equals("BluePiece")) {
                            square[a].setEnabled(true);
                            square[a].setBackground(new Color(255, 214, 90));
                            square[noSquare].setEnabled(true);
                            removeSingleSquareListeners(a);
                            handleSquareClick(noSquare, a);
                        }
                    }
                }
            }
        }
    }

    public void pieceClicked2(int noSquare) { // Handle piece being clicked second time
        for (int a = 0; a < 40; a++) {
            square[a].resetBackground();
            for (ActionListener al : square[a].getActionListeners()) {
                square[a].removeActionListener(al);
            }
            square[a].addListener();
        }
        if (square[noSquare].getType().equals("RedPiece")) {
            enableRed();
            disableBlue();
            disableSquare();
        } else if (square[noSquare].getType().equals("BluePiece")) {
            enableBlue();
            disableRed();
            disableSquare();
        }
    }

    public void movePiece(int fromSquare, int toSquare) { // Handle piece to move
        notifyMoveHistoryMove(fromSquare, toSquare);
        Controller.getController().enableSaveGame();
        Controller.getController().enableLoadGame();
        Controller.getController().playSound("./sounds/moved.wav");

        if (square[fromSquare].getName().equals("RedRam")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            Square tempSquare = square[fromSquare];
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedRam(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol(), tempSquare.getRotate());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedTor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedTor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedXor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedXor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedBiz")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedBiz(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedSau")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedSau(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("BlueRam")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            Square tempSquare = square[fromSquare];
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueRam(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol(), tempSquare.getRotate());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueTor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueTor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueXor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueXor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueBiz")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueBiz(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueSau")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueSau(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        }
        if (redMoved && blueMoved) { // Increase turn for each both have moved and reset both move
            turn++;
            redMoved = false;
            blueMoved = false;
        }
        if (turn % 2 == 0 && !redMoved && !blueMoved) { // Change Tor and Xor for each 2 turn
            for (int a = 0; a < 40; a++) {
                Square tempRedTor;
                Square tempRedXor;
                Square tempBlueTor;
                Square tempBlueXor;
                if (square[a].getName().equals("RedTor")) {
                    tempRedTor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new RedXor(square, tempRedTor.getNoSquare(), tempRedTor.getRow(), tempRedTor.getCol());
                } else if (square[a].getName().equals("RedXor")) {
                    tempRedXor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new RedTor(square, tempRedXor.getNoSquare(), tempRedXor.getRow(), tempRedXor.getCol());
                } else if (square[a].getName().equals("BlueTor")) {
                    tempBlueTor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new BlueXor(square, tempBlueTor.getNoSquare(), tempBlueTor.getRow(), tempBlueTor.getCol());
                } else if (square[a].getName().equals("BlueXor")) {
                    tempBlueXor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new BlueTor(square, tempBlueXor.getNoSquare(), tempBlueXor.getRow(), tempBlueXor.getCol());
                }
            }
            if (startVal == 1) {
                enableRed();
                disableBlue();
                disableSquare();
            } else {
                enableBlue();
                disableRed();
                disableSquare();
            }
        }
        notifyCurrentTurnNumber(turn);
        resetAllPieceClick();
        addAllListener();
        notifyObservers(redMoved, blueMoved);
    }

    public void eatPiece(int fromSquare, int toSquare) { // Handle piece to eat other piece
        notifyMoveHistoryEat(fromSquare, toSquare);
        String tempSquareLost = "";
        Controller.getController().enableSaveGame();
        Controller.getController().enableLoadGame();
        Controller.getController().playSound("./sounds/moved.wav");
        if ((square[toSquare].getName().equals("RedSau") || square[toSquare].getName().equals("BlueSau"))) {
            tempSquareLost = square[toSquare].getName();
        }
        if (square[fromSquare].getName().equals("RedRam")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            Square tempSquare = square[fromSquare];
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedRam(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol(), tempSquare.getRotate());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedTor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedTor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedXor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedXor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedBiz")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedBiz(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("RedSau")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new RedSau(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableBlue();
            disableRed();
            disableSquare();

            redMoved = true;
            notifyTurn("Blue");
        } else if (square[fromSquare].getName().equals("BlueRam")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            Square tempSquare = square[fromSquare];
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueRam(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol(), tempSquare.getRotate());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueTor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueTor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueXor")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueXor(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueBiz")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueBiz(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        } else if (square[fromSquare].getName().equals("BlueSau")) {
            view.getBoard().getBoardFrame().remove(square[fromSquare]);
            view.getBoard().getBoardFrame().remove(square[toSquare]);
            square[fromSquare] = new Square(fromSquare, square[fromSquare].getRow(), square[fromSquare].getCol());
            square[toSquare] = new BlueSau(square, toSquare, square[toSquare].getRow(), square[toSquare].getCol());

            enableRed();
            disableBlue();
            disableSquare();

            blueMoved = true;
            notifyTurn("Red");
        }
        if (redMoved && blueMoved) { // Increase turn for each both have moved and reset both move
            turn++;
            redMoved = false;
            blueMoved = false;
        }
        if (turn % 2 == 0 && !redMoved && !blueMoved) { // Change Tor and Xor for each 2 turns
            for (int a = 0; a < 40; a++) {

                Square tempRedTor;
                Square tempRedXor;
                Square tempBlueTor;
                Square tempBlueXor;

                if (square[a].getName().equals("RedTor")) {
                    tempRedTor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new RedXor(square, tempRedTor.getNoSquare(), tempRedTor.getRow(), tempRedTor.getCol());
                } else if (square[a].getName().equals("RedXor")) {
                    tempRedXor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new RedTor(square, tempRedXor.getNoSquare(), tempRedXor.getRow(), tempRedXor.getCol());
                } else if (square[a].getName().equals("BlueTor")) {
                    tempBlueTor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new BlueXor(square, tempBlueTor.getNoSquare(), tempBlueTor.getRow(), tempBlueTor.getCol());
                } else if (square[a].getName().equals("BlueXor")) {
                    tempBlueXor = square[a];
                    view.getBoard().getBoardFrame().remove(square[a]);
                    square[a] = new BlueTor(square, tempBlueXor.getNoSquare(), tempBlueXor.getRow(), tempBlueXor.getCol());
                }
            }
            if (startVal == 1) {
                enableRed();
                disableBlue();
                disableSquare();
            } else {
                enableBlue();
                disableRed();
                disableSquare();
            }
        }
        notifyCurrentTurnNumber(turn);
        resetAllPieceClick();
        addAllListener();
        notifyObservers(redMoved, blueMoved);

        if ((tempSquareLost.equals("RedSau") || tempSquareLost.equals("BlueSau"))) { // Call gameover when eating the sau
            gameOver(tempSquareLost);
        }
    }

    // Reset all piece clicked and remove their listeners
    public void resetAllPieceClick() {
        for (int a = 0; a < 40; a++) {
            square[a].resetClick();
            square[a].resetBackground();
            if (square[a].getType().equals("Square")) {
                for (ActionListener al : square[a].getActionListeners()) {
                    square[a].removeActionListener(al);
                }
            }
        }
    }

    // Reset game
    public void resetAll() {
        resetAllPieceClick();
        turn = 0;
        redMoved = false;
        blueMoved = false;
        view.getMenu().resetTurnDisplay();
        view.getMenu().resetNoTurnDisplay();
        view.getMenu().resetMoveHistory();
    }

    // Add all pieces default listener
    public void addAllListener() {
        for (int a = 0; a < 40; a++) {
            for (ActionListener al : square[a].getActionListeners()) {
                square[a].removeActionListener(al);
            }
            square[a].addListener();
        }
    }

    // Disable all red pieces
    public void disableRed() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("RedPiece")) {
                square[a].setEnabled(false);
            }
        }
    }

    // Disable all blue pieces
    public void disableBlue() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("BluePiece")) {
                square[a].setEnabled(false);
            }
        }
    }

    // Disable all squares
    public void disableSquare() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("Square")) {
                square[a].setEnabled(false);
            }
        }
    }

    // Enable all red pieces
    public void enableRed() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("RedPiece")) {
                square[a].setEnabled(true);
            }
        }
    }

    // Enable all blue pieces
    public void enableBlue() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("BluePiece")) {
                square[a].setEnabled(true);
            }
        }
    }

    // Enable all squares
    public void enableSquare() {
        for (int a = 0; a < 40; a++) {
            if (square[a].getType().equals("Square")) {
                square[a].setEnabled(true);
            }
        }
    }

    // Remove all listeners for a specific square
    public void removeSingleSquareListeners(int targetSquare) {
        for (ActionListener al : square[targetSquare].getActionListeners()) {
            square[targetSquare].removeActionListener(al);
        }
    }

    // Handle for the other square (desired location) clicked to move or eat
    public void handleSquareClick(int from, int to) {
        square[to].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (square[to].getType().equals("Square")) {
                    movePiece(from, to);
                } else {
                    eatPiece(from, to);
                }
            }
        });
    }

    // Handle move
    public void handleMoveClick(int from, int to) {
        square[to].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePiece(from, to);
            }
        });
    }

    // Handle eat 
    public void handleEatClick(int from, int to) {
        square[to].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eatPiece(from, to);
            }
        });
    }

    // End the game
    public void gameOver(String loser) {
        if (loser.equals("RedSau")) {
            view.gameOverDialog("BlueSau");
        } else if (loser.equals("BlueSau")) {
            view.gameOverDialog("RedSau");
        }
        resetAll();

        System.out.println("Game Over");
        notifyObservers(redMoved, blueMoved);
    }

    // Save current game
    public void saveCurrentGame() {
        try {
            FileWriter saveFile = new FileWriter("savedGame.txt");
            saveFile.write("Turn: " + turn + "\n");
            saveFile.write("RedMoved: " + redMoved + "\n");
            saveFile.write("BlueMoved: " + blueMoved + "\n");
            saveFile.write("StartVal: " + startVal + "\n");

            int squareNumber = 0;
            String squareName = "";
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 5; col++) {
                    squareName = square[squareNumber].getName();
                    switch (squareName) {
                        case "RedRam":
                            if (!square[squareNumber].getRotate()) {
                                saveFile.write("r1a");
                            } else if (square[squareNumber].getRotate()) {
                                saveFile.write("r1b");
                            }
                            break;
                        case "RedTor":
                            saveFile.write("r2a");
                            break;
                        case "RedXor":
                            saveFile.write("r3a");
                            break;
                        case "RedBiz":
                            saveFile.write("r4a");
                            break;
                        case "RedSau":
                            saveFile.write("r5a");
                            break;
                        case "BlueRam":
                            if (!square[squareNumber].getRotate()) {
                                saveFile.write("b1a");
                            } else if (square[squareNumber].getRotate()) {
                                saveFile.write("b1b");
                            }
                            break;
                        case "BlueTor":
                            saveFile.write("b2a");
                            break;
                        case "BlueXor":
                            saveFile.write("b3a");
                            break;
                        case "BlueBiz":
                            saveFile.write("b4a");
                            break;
                        case "BlueSau":
                            saveFile.write("b5a");
                            break;
                        default:
                            saveFile.write("sqa");
                            break;
                    }
                    if (col < 4) {
                        saveFile.write("|");
                    }
                    squareNumber++;
                }
                saveFile.write("\n");
            }
            saveFile.write(view.getMenu().getMoveHistory().getText());
            saveFile.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // Load to a last saved game
    public void loadGame() {
        try {
            BufferedReader loadFile = new BufferedReader(new FileReader("savedGame.txt"));
            String line = "";
            String[] splitLine;
            int lineNumber = 1;
            int squareNumber = 0;

            while ((line = loadFile.readLine()) != null) {
                if (lineNumber == 1) {
                    splitLine = line.split(" ");
                    turn = Integer.parseInt(splitLine[1].trim());
                } else if (lineNumber == 2) {
                    splitLine = line.split(" ");
                    redMoved = Boolean.parseBoolean(splitLine[1].trim());
                } else if (lineNumber == 3) {
                    splitLine = line.split(" ");
                    blueMoved = Boolean.parseBoolean(splitLine[1].trim());
                } else if (lineNumber == 4) {
                    splitLine = line.split(" ");
                    startVal = Integer.parseInt(splitLine[1].trim());
                } else if (lineNumber > 4 && lineNumber < 13) {
                    splitLine = line.split("\\|");
                    for (int a = 0; a < splitLine.length; a++) {
                        switch (splitLine[a]) {
                            case "r1a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedRam(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol(), false);
                                break;
                            case "r1b":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedRam(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol(), true);
                                break;
                            case "r2a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedTor(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "r3a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedXor(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "r4a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedBiz(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "r5a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new RedSau(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "b1a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueRam(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol(), false);
                                break;
                            case "b1b":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueRam(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol(), true);
                                break;
                            case "b2a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueTor(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "b3a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueXor(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "b4a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueBiz(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            case "b5a":
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new BlueSau(square, squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                            default:
                                view.getBoard().getBoardFrame().remove(square[squareNumber]);
                                square[squareNumber] = new Square(squareNumber, square[squareNumber].getRow(), square[squareNumber].getCol());
                                break;
                        }
                        squareNumber++;
                    }
                } else {
                    view.getMenu().getMoveHistory().append(line + "\n");
                }
                lineNumber++;
            }
            loadFile.close();
            view.getBoard().setStartVal(startVal);
            view.getMenu().setStartFirstVal(startVal);
            notifyCurrentTurnNumber(turn);
            if (startVal == 1) {
                if (redMoved) {
                    notifyTurn("Blue");
                } else {
                    notifyTurn("Red");
                }
            } else {
                if (blueMoved) {
                    notifyTurn("Red");
                } else {
                    notifyTurn("Blue");
                }
            }
            notifyObservers(redMoved, blueMoved);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

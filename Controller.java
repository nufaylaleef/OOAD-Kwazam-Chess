
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Controller {

    private static Controller controller = new Controller();
    private Model model;
    private View view;
    private Board board;
    private Menu menu;

    // Private constructor to initialize the controller
    private Controller() {
        view = new View();
        model = new Model(view);
        board = view.getBoard();
        menu = view.getMenu();

        model.addObserver(board);
        view.refreshView();

        menu.getOption1().addActionListener(new startGame());
        menu.getOption2().addActionListener(new restartGame());
        menu.getOption3().addActionListener(new loadGame());
        menu.getOption4().addActionListener(new saveGame());
        view.addWindowListener(new closeWindow());
    }

    public static Controller getController() {
        return controller;
    }

    // Method to handle piece click event
    public void handlePieceClick(int noSquare) {
        model.pieceClicked(noSquare);
        playSound("./sounds/move.wav");
    }

    // Method to handle second piece click event
    public void handlePieceClick2(int noSquare) {
        model.pieceClicked2(noSquare);
        playSound("./sounds/move.wav");
    }

    // Inner class to handle window closing event
    private class closeWindow extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            view.windowClosingDialog();
        }
    }

    // Method to handle end game event
    public void endGame(JDialog dialog, JButton closeButton) {
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                menu.getOption1().setEnabled(true);
                menu.getOption2().setEnabled(false);
            }
        });
    }

    // Method to save the current game state
    public void saveGame() {
        model.saveCurrentGame();
    }

    // Method to load a saved game state
    public void loadGame() {
        try {
            BufferedReader checkFile = new BufferedReader(new FileReader("savedGame.txt"));
            if (checkFile.readLine() == null) {
                checkFile.close();
                view.noSavedGameDialog();
            } else {
                menu.resetMoveHistory();
                model.loadGame();
            }
        } catch (IOException e) {
        }
    }

    // Method to reset the game
    public void resetGame() {
        model.resetAll();
    }

    // Method to enable the load game option
    public void enableLoadGame() {
        menu.getOption3().setEnabled(true);
    }

    // Method to enable the save game option
    public void enableSaveGame() {
        menu.getOption4().setEnabled(true);
    }

    // Method to disable the load game option
    public void disableLoadGame() {
        menu.getOption3().setEnabled(false);
    }

    // Method to disable the save game option
    public void disableSaveGame() {
        menu.getOption4().setEnabled(false);
    }

    // Method to play a sound
    public void playSound(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle start game action
    private class startGame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.resetAll();
            menu.startFirstOption();
            enableSaveGame();
            model.game(menu.getStartFirstVal());
            view.refreshView();
            playSound("./sounds/start.wav");
        }
    }

    // Inner class to handle restart game action
    private class restartGame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.restartGameDialog();
            enableLoadGame();
            disableSaveGame();
        }
    }

    // Inner class to handle save game action
    private class saveGame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.saveDialog();
        }
    }

    // Inner class to handle load game action
    private class loadGame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            view.loadDialog();
        }
    }
}

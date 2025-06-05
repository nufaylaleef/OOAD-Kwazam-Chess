import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View extends JFrame {

    private JPanel mainFrame;
    private JPanel gameFrame;
    private JPanel boardFrame;
    private JPanel menuFrame;

    private Board board;
    private Menu menu;

    private final Color CLOUD = Color.decode("#FFFCF5");
    private final Color CACAO = Color.decode("#4E2511");

    private final Font fontLabel = new Font("Courier New", Font.BOLD, 16);
    private final Font fontButton = new Font("Courier New", Font.BOLD, 14);

    // Constructor to initialize the view
    public View() {
        // Base frame
        super("Kwazam Chess");
        setSize(800, 850);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        // Main frame
        mainFrame = new JPanel(new BorderLayout());
        mainFrame.setBackground(CLOUD);
        mainFrame.setMinimumSize(new Dimension(800, 850));
        add(mainFrame);

        // Game frame
        gameFrame = new JPanel(new GridBagLayout());
        gameFrame.setBackground(CLOUD);
        gameFrame.setPreferredSize(new Dimension(550, 850));
        gameFrame.setMinimumSize(new Dimension(550, 850));
        mainFrame.add(gameFrame, BorderLayout.CENTER);

        // Board frame
        boardFrame = new JPanel(new GridLayout(8, 5));
        boardFrame.setBackground(CACAO);
        boardFrame.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        boardFrame.setPreferredSize(new Dimension(500, 800));
        boardFrame.setMinimumSize(new Dimension(500, 800));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gameFrame.add(boardFrame, gbc);

        // Menu frame
        menuFrame = new JPanel(new BorderLayout());
        menuFrame.setBackground(CLOUD);
        menuFrame.setLayout(new BoxLayout(menuFrame, BoxLayout.Y_AXIS));
        menuFrame.setPreferredSize(new Dimension(250, 850));
        mainFrame.add(menuFrame, BorderLayout.EAST);

        // Initialize Board and Menu
        board = new Board(this, boardFrame);
        menu = new Menu(this, menuFrame);
    }

    // Getter for board
    public Board getBoard() {
        return board;
    }

    // Getter for menu
    public Menu getMenu() {
        return menu;
    }

    // Method to refresh the view
    public void refreshView() {
        this.revalidate();
        this.repaint();
    }

    // Method to display game over dialog
    public void gameOverDialog(String winner) {
        Controller.getController().playSound("./sounds/win.wav");

        JDialog endGameDialog = new JDialog();
        endGameDialog.setModal(true);
        endGameDialog.setSize(300, 150);
        endGameDialog.setResizable(false);
        endGameDialog.setLocationRelativeTo(null);
        endGameDialog.setTitle("Game Over");

        JPanel endGamePanel = new JPanel(new FlowLayout());
        endGamePanel.setBackground(CLOUD);
        endGamePanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        endGameDialog.add(endGamePanel);

        JLabel endGameLabel = new JLabel((winner.equals("RedSau") ? "Red" : "Blue") + " has won the game!");
        endGameLabel.setFont(fontLabel);
        endGameLabel.setForeground(CACAO);
        endGameLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 50));
        endGamePanel.add(endGameLabel);

        JButton endGameClose = new JButton("Close");
        endGameClose.setForeground(CACAO);
        endGameClose.setBackground(CLOUD);
        endGamePanel.add(endGameClose);

        Controller.getController().endGame(endGameDialog, endGameClose);
        endGameDialog.revalidate();
        endGameDialog.repaint();
        endGameDialog.setVisible(true);

        board.restartPos();
        Controller.getController().resetGame();
        menu.getOption4().setEnabled(false);

        refreshView();
    }

    // Method to display window closing dialog
    public void windowClosingDialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(450, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setTitle("Exit Kwazam Chess?");

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(CLOUD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        dialog.add(panel);

        JLabel label = new JLabel("Are you sure you want to exit this game?");
        label.setFont(fontLabel);
        label.setForeground(CACAO);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label);

        JButton yesExit = new JButton("Yes");
        yesExit.setFont(fontButton);
        yesExit.setForeground(CACAO);
        yesExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                System.exit(0);
            }
        });
        panel.add(yesExit);

        JButton noExit = new JButton("No");
        noExit.setFont(fontButton);
        noExit.setForeground(CACAO);
        noExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(noExit);

        dialog.setVisible(true);

        refreshView();
    }

    // Method to display restart game dialog
    public void restartGameDialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(450, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setTitle("Reset the game?");

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(CLOUD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        dialog.add(panel);

        JLabel label = new JLabel("Are you sure you want to reset the game?");
        label.setFont(fontLabel);
        label.setForeground(CACAO);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label);

        JButton yesReset = new JButton("Yes");
        yesReset.setFont(fontButton);
        yesReset.setForeground(CACAO);
        yesReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                board.restartPos();
                Controller.getController().resetGame();
                menu.getOption1().setEnabled(true);
                menu.getOption2().setEnabled(false);
                refreshView();
            }
        });
        panel.add(yesReset);

        JButton noReset = new JButton("No");
        noReset.setFont(fontButton);
        noReset.setForeground(CACAO);
        noReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(noReset);

        dialog.setVisible(true);
    }

    // Method to display save game dialog
    public void saveDialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(450, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setTitle("Save Game?");

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(CLOUD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        dialog.add(panel);

        JLabel label = new JLabel("Are you sure you want to save this game?");
        label.setFont(fontLabel);
        label.setForeground(CACAO);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label);

        JButton yesExit = new JButton("Yes");
        yesExit.setFont(fontButton);
        yesExit.setForeground(CACAO);
        yesExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.getController().saveGame();
                menu.getOption1().setEnabled(false);
                menu.getOption2().setEnabled(true);
                menu.getOption3().setEnabled(false);
                menu.getOption4().setEnabled(false);
                dialog.dispose();
            }
        });
        panel.add(yesExit);

        JButton noExit = new JButton("No");
        noExit.setFont(fontButton);
        noExit.setForeground(CACAO);
        noExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(noExit);

        dialog.setVisible(true);

        refreshView();
    }

    // Method to display load game dialog
    public void loadDialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setSize(500, 200);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setTitle("Load Game?");

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(CLOUD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        dialog.add(panel);

        JLabel label = new JLabel("Are you sure you want to load last saved game?");
        label.setFont(fontLabel);
        label.setForeground(CACAO);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(label);

        JButton yesExit = new JButton("Yes");
        yesExit.setFont(fontButton);
        yesExit.setForeground(CACAO);
        yesExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.getOption1().setEnabled(false);
                menu.getOption2().setEnabled(true);
                menu.getOption3().setEnabled(false);
                menu.getOption4().setEnabled(false);
                Controller.getController().loadGame();
                dialog.dispose();
            }
        });
        panel.add(yesExit);

        JButton noExit = new JButton("No");
        noExit.setFont(fontButton);
        noExit.setForeground(CACAO);
        noExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(noExit);

        dialog.setVisible(true);

        refreshView();
    }

    // Method to display no saved game dialog
    public void noSavedGameDialog() {
        JDialog nsgDialog = new JDialog();
        nsgDialog.setModal(true);
        nsgDialog.setSize(300, 150);
        nsgDialog.setResizable(false);
        nsgDialog.setLocationRelativeTo(null);
        nsgDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        nsgDialog.setTitle("No Saved Game");

        JPanel nsgPanel = new JPanel(new FlowLayout());
        nsgPanel.setBackground(CLOUD);
        nsgPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        nsgDialog.add(nsgPanel);

        JLabel nsgLabel = new JLabel("No saved game found!");
        nsgLabel.setFont(fontLabel);
        nsgLabel.setForeground(CACAO);
        nsgLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 50));
        nsgPanel.add(nsgLabel);

        JButton nsgClose = new JButton("Close");
        nsgClose.setForeground(CACAO);
        nsgClose.setBackground(CLOUD);
        nsgPanel.add(nsgClose);

        nsgClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.getOption1().setEnabled(true);
                menu.getOption2().setEnabled(false);
                menu.getOption3().setEnabled(true);
                menu.getOption4().setEnabled(false);
                nsgDialog.dispose();
            }
        });

        nsgDialog.revalidate();
        nsgDialog.repaint();
        nsgDialog.setVisible(true);

        refreshView();
    }
}

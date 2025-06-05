import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Flow;
import javax.swing.*;

public class Menu {

    private View view;
    private Board board;
    private Square[] square;
    private int startFirstVal;

    private JPanel menuFrame;
    private JPanel menuTitleContainer;
    private JPanel optionsContainer;

    private JLabel menuTitle;
    private JButton option1;
    private JButton option2;
    private JButton option3;
    private JButton option4;
    private JLabel turnDisplay;
    private JLabel noTurnDisplay;

    private JPanel option1Container;
    private JPanel option2Container;
    private JPanel option3Container;
    private JPanel option4Container;
    private JPanel noTurnDisplayContainer;

    private JPanel moveHistoryContainer;
    private JTextArea moveHistory;

    private final Color CLOUD = Color.decode("#FFFCF5");
    private final Color CACAO = Color.decode("#4E2511");
    private final Color NAVY = Color.decode("#895159");
    private final Color COBALTBLUE = Color.decode("#010169");
    private final Color RED = Color.decode("#FF0000");
    private final Color meringue = Color.decode("#E8EBED");

    private final Font fontMenu = new Font("Courier New", Font.BOLD, 16);
    private final Font fontLabel = new Font("Courier New", Font.BOLD, 14);
    private final Font fontButton = new Font("Courier New", Font.BOLD, 14);
    private final Font fontDisplayTurn1 = new Font("Courier New", Font.BOLD, 22);
    private final Font fontDisplayTurn2 = new Font("Courier New", Font.BOLD, 16);

    // Constructor to initialize the Menu
    public Menu(View view, JPanel menuFrame) {
        this.view = view;
        this.menuFrame = menuFrame;
        this.board = view.getBoard();
        this.square = board.getSquare();

        // Create and add the logo image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(CACAO);
        imagePanel.setPreferredSize(new Dimension(250, 100));
        menuFrame.add(imagePanel);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("./images/logo.png");
        Image img = imageIcon.getImage();
        Image resizedImg = img.getScaledInstance(225, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(resizedImg);
        imageLabel.setIcon(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel);

        // Create and add the options container
        optionsContainer = new JPanel(new FlowLayout());
        optionsContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));
        optionsContainer.setBackground(CACAO);
        optionsContainer.setPreferredSize(new Dimension(250, 350));
        menuFrame.add(optionsContainer);

        // Add turn display label
        turnDisplay = new JLabel("Start the Game!");
        turnDisplay.setFont(fontDisplayTurn1);
        turnDisplay.setForeground(CLOUD);
        turnDisplay.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        optionsContainer.add(turnDisplay);

        // Add option buttons
        option1Container = new JPanel();
        option1Container.setBackground(CLOUD);
        option1Container.setPreferredSize(new Dimension(220, 50));
        optionsContainer.add(option1Container);

        option1 = new JButton("New Game");
        option1.setForeground(CLOUD);
        option1.setFont(fontMenu);
        option1.setBackground(CACAO);
        option1.setBorder(BorderFactory.createEmptyBorder(12, 65, 10, 65));
        option1Container.add(option1);

        option2Container = new JPanel();
        option2Container.setBackground(CLOUD);
        option2Container.setPreferredSize(new Dimension(220, 50));
        optionsContainer.add(option2Container);

        option2 = new JButton("Reset Game");
        option2.setForeground(CLOUD);
        option2.setBackground(CACAO);
        option2.setFont(fontMenu);
        option2.setBorder(BorderFactory.createEmptyBorder(12, 55, 10, 55));
        option2.setEnabled(false);
        option2Container.add(option2);

        option3Container = new JPanel();
        option3Container.setBackground(CLOUD);
        option3Container.setPreferredSize(new Dimension(220, 50));
        optionsContainer.add(option3Container);

        option3 = new JButton("Load Last Saved Game");
        option3.setForeground(CLOUD);
        option3.setBackground(CACAO);
        option3.setFont(fontMenu);
        option3.setBorder(BorderFactory.createEmptyBorder(12, 5, 10, 5));
        option3Container.add(option3);

        option4Container = new JPanel();
        option4Container.setBackground(CLOUD);
        option4Container.setPreferredSize(new Dimension(220, 50));
        optionsContainer.add(option4Container);

        option4 = new JButton("Save Game");
        option4.setForeground(CLOUD);
        option4.setBackground(CACAO);
        option4.setFont(fontMenu);
        option4.setBorder(BorderFactory.createEmptyBorder(12, 60, 10, 60));
        option4.setEnabled(false);
        option4Container.add(option4);

        // Create and add the move history container
        moveHistoryContainer = new JPanel();
        moveHistoryContainer.setBackground(CACAO);
        moveHistoryContainer.setPreferredSize(new Dimension(200, 350));
        menuFrame.add(moveHistoryContainer);

        moveHistory = new JTextArea();
        moveHistory.setEditable(false);
        moveHistory.setLineWrap(true);
        moveHistory.setWrapStyleWord(true);
        moveHistory.setFont(fontLabel);
        moveHistory.setForeground(CACAO);
        moveHistory.setBackground(CLOUD);
        moveHistory.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JScrollPane moveHistoryScroll = new JScrollPane(moveHistory);
        moveHistoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        moveHistoryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        moveHistoryScroll.setPreferredSize(new Dimension(220, 350));
        moveHistoryContainer.add(moveHistoryScroll);

        // Create and add the no turn display container
        noTurnDisplayContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        noTurnDisplayContainer.setBackground(CACAO);
        noTurnDisplayContainer.setPreferredSize(new Dimension(250, 50));
        noTurnDisplayContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        menuFrame.add(noTurnDisplayContainer, BorderLayout.SOUTH);

        noTurnDisplay = new JLabel("By Group: Abrar TT7L F");
        noTurnDisplay.setFont(fontDisplayTurn2);
        noTurnDisplay.setForeground(CLOUD);
        noTurnDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        noTurnDisplay.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        noTurnDisplayContainer.add(noTurnDisplay);
    }

    // Method to start the first option dialog
    public void startFirstOption() {
        JDialog sfDialog = new JDialog();
        sfDialog.setModal(true);
        sfDialog.setSize(400, 200);
        sfDialog.setResizable(false);
        sfDialog.setLocationRelativeTo(null);
        sfDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        sfDialog.setTitle("Who Starts First?");

        JPanel sfPanel = new JPanel(new FlowLayout());
        sfPanel.setBackground(CLOUD);
        sfPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        sfDialog.add(sfPanel);

        JLabel sfLabel = new JLabel("Who is going to start first?");
        sfLabel.setFont(fontLabel);
        sfLabel.setForeground(CACAO);
        sfLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        sfPanel.add(sfLabel);

        // Add buttons for selecting who starts first
        JButton redFirst = new JButton("Red");
        redFirst.setForeground(RED);
        redFirst.setBackground(meringue);
        redFirst.setFont(fontButton);
        redFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                startFirstVal = 1;

                view.getBoard().setStartVal(startFirstVal);
                view.getBoard().initializeBoard();
                view.getBoard().refreshBoard(false, false);

                option1.setEnabled(false);
                option2.setEnabled(true);
                sfDialog.dispose();
            }
        });
        sfPanel.add(redFirst);

        JButton blueFirst = new JButton("Blue");
        blueFirst.setForeground(COBALTBLUE);
        blueFirst.setBackground(meringue);
        blueFirst.setFont(fontButton);
        blueFirst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                startFirstVal = 2;

                view.getBoard().setStartVal(startFirstVal);
                view.getBoard().initializeBoard();
                view.getBoard().refreshBoard(false, false);

                option1.setEnabled(false);
                option2.setEnabled(true);
                sfDialog.dispose();
            }
        });
        sfPanel.add(blueFirst);

        JButton randomStart = new JButton("Random");
        randomStart.setForeground(CACAO);
        randomStart.setBackground(meringue);
        randomStart.setFont(fontButton);
        randomStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int secs = (int) System.currentTimeMillis() / 1000;
                int randomStartVal = Math.abs(secs) % 2 + 1;
                System.out.println(randomStartVal);

                startFirstVal = randomStartVal;

                view.getBoard().setStartVal(startFirstVal);
                view.getBoard().initializeBoard();
                view.getBoard().refreshBoard(false, false);

                option1.setEnabled(false);
                option2.setEnabled(true);
                sfDialog.dispose();
            }
        });
        sfPanel.add(randomStart);
        sfDialog.revalidate();
        sfDialog.repaint();
        sfDialog.setVisible(true);
    }

    // Getter for startFirstVal
    public int getStartFirstVal() {
        return startFirstVal;
    }

    // Setter for startFirstVal
    public void setStartFirstVal(int startFirstVal) {
        this.startFirstVal = startFirstVal;
    }

    // Getter for option1 button
    public JButton getOption1() {
        return option1;
    }

    // Getter for option2 button
    public JButton getOption2() {
        return option2;
    }

    // Getter for option3 button
    public JButton getOption3() {
        return option3;
    }

    // Getter for option4 button
    public JButton getOption4() {
        return option4;
    }

    // Update the turn display label with the current player's turn
    public void updateTurnDisplay(String currentPlayer) {
        turnDisplay.setText(currentPlayer + "'s Turn!");
    }

    // Reset the turn display label to the default text
    public void resetTurnDisplay() {
        turnDisplay.setText("Start the Game!");
    }

    // Getter for turnDisplay label
    public JLabel getTurnDisplay() {
        return turnDisplay;
    }

    // Update the no turn display label with the current turn number
    public void updateNoTurnDisplay(int currentTurn) {
        noTurnDisplay.setFont(fontDisplayTurn1);
        noTurnDisplay.setText("Turn " + (currentTurn + 1));
    }

    // Reset the no turn display label to the default text
    public void resetNoTurnDisplay() {
        noTurnDisplay.setFont(fontDisplayTurn2);
        noTurnDisplay.setText("By Group: Abrar TT7L F");
    }

    // Getter for noTurnDisplay label
    public JLabel getNoTurnDisplay() {
        return noTurnDisplay;
    }

    // Update the move history with a move action
    public void updateMoveHistoryMove(int from, int to) {
        moveHistory.append(square[from].getName() + " (" + square[from].getRow() + "," + square[from].getCol() + ") to ("
                + square[to].getRow() + "," + square[to].getCol() + ")" + "\n");
    }

    // Update the move history with an eat action
    public void updateMoveHistoryEat(int from, int to) {
        moveHistory.append(square[from].getName() + " (" + square[from].getRow() + "," + square[from].getCol() + ") eat ("
                + square[to].getRow() + "," + square[to].getCol() + ")" + "\n");
    }

    // Reset the move history text area
    public void resetMoveHistory() {
        moveHistory.setText("");
    }

    // Getter for moveHistory text area
    public JTextArea getMoveHistory() {
        return moveHistory;
    }
}

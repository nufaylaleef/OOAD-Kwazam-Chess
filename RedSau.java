
import java.awt.event.*;
import javax.swing.*;

public class RedSau extends Square {

    private final String type = "RedPiece";
    private final String name = "RedSau";
    private Square[] square;
    private ImageIcon Icon;
    private boolean clicked;

    private Controller controller = Controller.getController();

    // Constructor to initialize the RedSau piece
    public RedSau(Square[] square, int noSquare, int row, int col) {
        super(noSquare, row, col);
        this.square = square;
        this.clicked = false;

        // Set the initial icon for the RedSau piece
        this.Icon = new ImageIcon("./images/red_sau1.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);

        // Add action listener to handle piece clicks
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!clicked) {
                    controller.handlePieceClick(noSquare);
                    clicked = true;
                } else {
                    controller.handlePieceClick2(noSquare);
                    clicked = false;
                }
            }
        });

    }

    // Getter for the type of the piece
    public String getType() {
        return type;
    }

    // Getter for the name of the piece
    public String getName() {
        return name;
    }

    // Method to set the icon facing up
    @Override
    public void setIconFacingUp() {
        Icon = new ImageIcon("./images/red_sau1.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
    }

    // Method to set the icon facing down
    @Override
    public void setIconFacingDown() {
        Icon = new ImageIcon("./images/red_sau2.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
    }

    // Method to add action listener to handle piece clicks
    public void addListener() {
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!clicked) {
                    controller.handlePieceClick(getNoSquare());
                    clicked = true;
                } else {
                    controller.handlePieceClick2(getNoSquare());
                    clicked = false;
                }
            }
        });
    }
}

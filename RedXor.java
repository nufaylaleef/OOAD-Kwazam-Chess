
import java.awt.event.*;
import javax.swing.*;

public class RedXor extends Square {

    private final String type = "RedPiece";
    private final String name = "RedXor";
    private Square[] square;
    private boolean clicked;

    private Controller controller = Controller.getController();

    // Constructor to initialize the RedXor piece
    public RedXor(Square[] square, int noSquare, int row, int col) {
        super(noSquare, row, col);
        this.square = square;
        this.clicked = false;

        // Set the initial icon for the RedXor piece
        ImageIcon Icon = new ImageIcon("./images/red_xor.png");
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

    // Method to add action listener to the piece
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

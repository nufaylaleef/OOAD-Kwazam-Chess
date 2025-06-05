
import java.awt.event.*;
import javax.swing.*;

public class RedBiz extends Square {

    // Define the type and name of the piece
    private final String type = "RedPiece";
    private final String name = "RedBiz";
    private Square[] square;
    private boolean clicked;

    // Get the controller instance
    private Controller controller = Controller.getController();

    // Constructor to initialize the RedBiz piece
    public RedBiz(Square[] square, int noSquare, int row, int col) {
        super(noSquare, row, col);
        this.square = square;
        this.clicked = false;

        // Set the icon for the piece
        ImageIcon Icon = new ImageIcon("./images/red_biz.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);

        // Add action listener to handle piece clicks
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!clicked) {
                    // Handle first click
                    controller.handlePieceClick(noSquare);
                    clicked = true;
                } else {
                    // Handle second click
                    controller.handlePieceClick2(noSquare);
                    clicked = false;
                }
            }
        });
    }

    // Get the type of the piece
    public String getType() {
        return type;
    }

    // Get the name of the piece
    public String getName() {
        return name;
    }

    // Add action listener to handle piece clicks
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

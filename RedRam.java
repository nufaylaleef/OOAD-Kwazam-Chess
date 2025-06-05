
import java.awt.event.*;
import javax.swing.*;

public class RedRam extends Square {

    // Define the type and name of the piece
    private final String type = "RedPiece";
    private final String name = "RedRam";
    private Square[] square;
    private ImageIcon Icon;
    private boolean clicked;
    private boolean rotated;

    // Get the controller instance
    private Controller controller = Controller.getController();

    // Constructor to initialize the RedRam piece
    public RedRam(Square[] square, int noSquare, int row, int col, boolean rotation) {
        super(noSquare, row, col);
        this.square = square;
        this.clicked = false;
        this.rotated = rotation;

        // Set the icon for the piece
        this.Icon = new ImageIcon("./images/red_ram1.png");
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

    // Reset the clicked state
    public void resetClick() {
        this.clicked = false;
    }

    @Override
    public void checkRotation() {
        if (this.getRow() - 1 < 0) {
            this.rotated = true;
        } else if (this.getRow() + 1 > 7) {
            this.rotated = false;
        }
    }

    @Override
    public void setRotate() {
        this.rotated = true;
    }

    @Override
    public void resetRotate() {
        this.rotated = false;
    }

    @Override
    public boolean getRotate() {
        return rotated;
    }

    // Set the icon facing up
    @Override
    public void setIconFacingUp() {
        Icon = new ImageIcon("./images/red_ram1.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
    }

    // Set the icon facing down
    @Override
    public void setIconFacingDown() {
        Icon = new ImageIcon("./images/red_ram2.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
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

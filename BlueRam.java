
import java.awt.event.*;
import javax.swing.*;

public class BlueRam extends Square {

    private final String type = "BluePiece";
    private final String name = "BlueRam";
    private Square[] square;
    private ImageIcon Icon;
    private boolean clicked;
    private boolean rotated;

    private Controller controller = Controller.getController();

    // Constructor to initialize the BlueRam piece
    public BlueRam(Square[] square, int noSquare, int row, int col, boolean rotation) {
        super(noSquare, row, col);
        this.square = square;
        this.clicked = false;
        this.rotated = rotation;

        // Set the initial icon for the BlueRam piece
        this.Icon = new ImageIcon("./images/blue_ram1.png");
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

    // Method to reset the clicked state
    public void resetClick() {
        this.clicked = false;
    }

    @Override
    public void checkRotation() {
        if (this.getRow() + 1 > 7) {
            this.rotated = true;
        } else if (this.getRow() - 1 < 0) {
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

    // Method to set the icon facing up
    public void setIconFacingUp() {
        Icon = new ImageIcon("./images/blue_ram2.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
    }

    // Method to set the icon facing down
    public void setIconFacingDown() {
        Icon = new ImageIcon("./images/blue_ram1.png");
        this.setIcon(Icon);
        this.setDisabledIcon(Icon);
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

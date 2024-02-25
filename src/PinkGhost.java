import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class PinkGhost extends Ghost{
    public PinkGhost(int x, int y, AbstractTableModel model,GameController controller) {
        super(x, y, model,controller);
    }

    @Override
    void loadImages() {
        getLeftMoves()[0] = new ImageIcon("game_images/pinkleft1.png");
        getLeftMoves()[1] = new ImageIcon("game_images/pinkleft2.png");

        getRightMoves()[0] = new ImageIcon("game_images/pinkright1.png");
        getRightMoves()[1] = new ImageIcon("game_images/pinkright2.png");

        getUpMoves()[0] = new ImageIcon("game_images/pinkup1.png");
        getUpMoves()[1] = new ImageIcon("game_images/pinkup2.png");

        getDownMoves()[0] = new ImageIcon("game_images/pinkdown1.png");
        getDownMoves()[1] = new ImageIcon("game_images/pinkdown2.png");
    }
}

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class GreenGhost extends Ghost{
    public GreenGhost(int x, int y, AbstractTableModel model,GameController controller) {
        super(x, y, model,controller);
    }

    @Override
    void loadImages() {
        getLeftMoves()[0] = new ImageIcon("game_images/greenleft1.png");
        getLeftMoves()[1] = new ImageIcon("game_images/greenleft2.png");

        getRightMoves()[0] = new ImageIcon("game_images/greenright1.png");
        getRightMoves()[1] = new ImageIcon("game_images/greenright2.png");

        getUpMoves()[0] = new ImageIcon("game_images/greenup1.png");
        getUpMoves()[1] = new ImageIcon("game_images/greenup2.png");

        getDownMoves()[0] = new ImageIcon("game_images/greendown1.png");
        getDownMoves()[1] = new ImageIcon("game_images/greendown2.png");
    }
}

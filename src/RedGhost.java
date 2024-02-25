import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class RedGhost extends Ghost{
    public RedGhost(int x, int y, AbstractTableModel model,GameController controller) {
        super(x, y, model,controller);
    }

    @Override
    void loadImages() {
        getLeftMoves()[0] = new ImageIcon("game_images/redleft1.png");
        getLeftMoves()[1] = new ImageIcon("game_images/redleft2.png");

        getRightMoves()[0] = new ImageIcon("game_images/redright1.png");
        getRightMoves()[1] = new ImageIcon("game_images/redright2.png");

        getUpMoves()[0] = new ImageIcon("game_images/redup1.png");
        getUpMoves()[1] = new ImageIcon("game_images/redup2.png");

        getDownMoves()[0] = new ImageIcon("game_images/reddown1.png");
        getDownMoves()[1] = new ImageIcon("game_images/reddown2.png");
    }
}

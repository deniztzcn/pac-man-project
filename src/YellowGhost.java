import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class YellowGhost extends Ghost {
    public YellowGhost(int x, int y, AbstractTableModel model,GameController controller) {
        super(x, y,model,controller);
        loadImages();
    }
    @Override
    public void loadImages(){
        getLeftMoves()[0] = new ImageIcon("game_images/yellowleft1.png");
        getLeftMoves()[1] = new ImageIcon("game_images/yellowleft2.png");

        getRightMoves()[0] = new ImageIcon("game_images/yellowright1.png");
        getRightMoves()[1] = new ImageIcon("game_images/yellowright2.png");

        getUpMoves()[0] = new ImageIcon("game_images/yellowup1.png");
        getUpMoves()[1] = new ImageIcon("game_images/yellowup2.png");

        getDownMoves()[0] = new ImageIcon("game_images/yellowdown1.png");
        getDownMoves()[1] = new ImageIcon("game_images/yellowdown2.png");
    }
}

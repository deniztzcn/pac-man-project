import javax.swing.*;

public class DoublePointUpgrade implements Upgrade{
    private static final ImageIcon DOUBLE_IMAGE = new ImageIcon("game_images/double_score.png");
    @Override
    public boolean isRelatedGhosts() {
        return false;
    }

    @Override
    public ImageIcon getImageIcon() {
        return DOUBLE_IMAGE;
    }

    @Override
    public void accept(Object o) {
        ((Pacman) o).setFoodVal(20);
    }
}

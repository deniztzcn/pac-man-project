import javax.swing.*;

public class HeartUpgrade implements Upgrade{
    private static final ImageIcon HEART_IMAGE= new ImageIcon("game_images/heart.png");
    @Override
    public boolean isRelatedGhosts() {
        return false;
    }

    @Override
    public ImageIcon getImageIcon() {
        return HEART_IMAGE;
    }

    @Override
    public void accept(Object o) {
        if (((Pacman) o).getLives() + 1 <= 5) {
            ((Pacman) o).setLives(((Pacman) o).getLives() + 1);
        }
    }
}

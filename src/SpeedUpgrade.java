import javax.swing.*;

public class SpeedUpgrade implements Upgrade{
    private static final ImageIcon SPEED_ICON = new ImageIcon("game_images/speed.png");
    @Override
    public boolean isRelatedGhosts() {
        return false;
    }

    @Override
    public ImageIcon getImageIcon() {
        return SPEED_ICON;
    }

    @Override
    public void accept(Object o) {
        ((Pacman) o).setSpeed(3);
    }
}

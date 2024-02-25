import javax.swing.*;

public class EatableGhostUpgrade implements Upgrade{
    private static final ImageIcon EATABLE_IMAGE = new ImageIcon("game_images/dead.png");
    @Override
    public boolean isRelatedGhosts() {
        return true;
    }

    @Override
    public ImageIcon getImageIcon() {
        return EATABLE_IMAGE;
    }

    @Override
    public void accept(Object o) {
        for (Ghost g : (Ghost[]) o) {
            g.setCurrentMoves(g.getDeadMoves());
            g.setEatable(true);
        }
    }
}

import javax.swing.*;

public class CollectorUpgrade implements Upgrade {
    private static final ImageIcon COLLECTOR_IMAGE = new ImageIcon("game_images/collector.png");
    @Override
    public boolean isRelatedGhosts() {
        return true;
    }

    @Override
    public ImageIcon getImageIcon() {
        return COLLECTOR_IMAGE;
    }

    @Override
    public void accept(Object o) {
        for(Ghost g : (Ghost[])o){
            g.setCollector(true);
        }
    }
}

import javax.swing.*;
import java.util.function.Consumer;

public interface Upgrade extends Consumer {
    boolean isRelatedGhosts();
    ImageIcon getImageIcon();

    @Override
    void accept(Object o);
}

import javax.swing.*;

public interface Moveable{
    boolean up();
    boolean down();
    boolean left();
    boolean right();
    ImageIcon[] getCurrentMoves();
}

import javax.imageio.ImageReader;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Random;

public class GameTableCellRenderer extends DefaultTableCellRenderer {
    private Random random;
    private int rowHeight;
    private int columnWidth;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table,  value,  isSelected,  hasFocus,  row,  column);
        random = new Random();
        rowHeight = table.getRowHeight();
        columnWidth = table.getColumnModel().getColumn(column).getWidth();
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.black);

        if(value instanceof Moveable){
            ImageIcon[] images= ((Moveable) value).getCurrentMoves();
            ImageIcon move = images[random.nextInt(images.length)];
            Image scaled = move.getImage().getScaledInstance(columnWidth,rowHeight,Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            return label;
        }
        if(value instanceof Upgrade){
            ImageIcon img = ((Upgrade)value).getImageIcon();
            Image scaled = img.getImage().getScaledInstance(table.getColumnModel().getColumn(column).getWidth(),table.getRowHeight(),Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            return label;
        }
        if ((int) value == CellType.FOOD.getValue()) {
            setText("*");
            setFont(new Font(Font.SANS_SERIF,Font.BOLD,10));
            setForeground(Color.yellow);
            setBackground(Color.black);
            setVerticalAlignment(CENTER);
            setHorizontalAlignment(CENTER);
        } else if((int) value == CellType.GATE.getValue()) {
            ImageIcon img = new ImageIcon("game_images/gate.png");
            Image scaled = img.getImage().getScaledInstance(table.getColumnModel().getColumn(column).getWidth(),table.getRowHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            return label;
        } else if ((int) value == CellType.EMPTY.getValue()) {
            setBackground(Color.black);
            setText(null);
        } else {
            setBackground(Color.blue);
            setText(null);
        }
        return this;
    }
}

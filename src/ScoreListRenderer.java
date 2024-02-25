import javax.swing.*;
import java.awt.*;

public class ScoreListRenderer implements ListCellRenderer<Record> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Record> list, Record value, int index, boolean isSelected, boolean cellHasFocus) {
        String result = value.toString().replace("\t","&nbsp;&nbsp;&nbsp;&nbsp;");
        JLabel label = new JLabel("<html>" + result + "<html>");
        label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        label.setOpaque(true);
        if(index == 0){
            label.setBackground(Color.yellow);
        }
        if(index == 1){
            label.setBackground(Color.gray);
        }
        if(index == 2){
            label.setBackground(Color.orange);
        }
        return label;
    }
}

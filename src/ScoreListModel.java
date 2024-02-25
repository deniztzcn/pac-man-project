import javax.swing.*;
import java.util.List;

public class ScoreListModel extends AbstractListModel<Record> {
    private List<Record> items;
    public ScoreListModel(List<Record> items) {
        this.items = items;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Record getElementAt(int index) {
        return items.get(index);
    }
}

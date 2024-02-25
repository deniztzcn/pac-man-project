import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreListWindow extends JFrame {
    ScoreListModel model;
    JList scoreList;
    JScrollPane scrollPane;
    List<Record> recordList;
    public ScoreListWindow() {
        setTitle("Score List");
        recordList = new ArrayList<>();
        try {
            loadRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(recordList);
        model = new ScoreListModel(recordList);
        scoreList = new JList(model);
        scoreList.setCellRenderer(new ScoreListRenderer());
        scrollPane = new JScrollPane(scoreList);
        add(scrollPane);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift Q");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke,"gameOver");
        getRootPane().getActionMap().put("gameOver", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
    }
    private void loadRecords() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("scores.txt"));
        String line;
        while((line = br.readLine()) != null){
            String[] parts = line.split("\t");
            recordList.add(new Record(parts[0],Integer.parseInt(parts[1])));
        }
    }
    private void closeWindow(){
        System.out.println(Thread.activeCount());
        removeAll();
        System.out.println(Thread.activeCount());
        setVisible(false);
        dispose();
    }
}

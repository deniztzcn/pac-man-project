import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame {
    private JPanel gamePanel;
    private JPanel scorePanel;
    private JPanel heartPanel;
    private JTable gameTable;
    private GameTableModel model;
    private int row;
    private int column;
    GameController controller;
    private volatile boolean isMovingUp = false;
    private volatile boolean isMovingDown = false;
    private volatile boolean isMovingLeft = false;
    private volatile boolean isMovingRight = false;
    Map<Integer,Boolean> map;
    public GameWindow() {
        map = new HashMap<>();
        map.put(KeyEvent.VK_UP,isMovingUp);
        map.put(KeyEvent.VK_DOWN,isMovingDown);
        map.put(KeyEvent.VK_LEFT,isMovingLeft);
        map.put(KeyEvent.VK_RIGHT,isMovingRight);
        scorePanel = new JPanel(new GridBagLayout());
        heartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        this.row = 39;
        this.column = 39;

        model = new GameTableModel(row,column);
        gameTable = new JTable();
        gameTable.setModel(model);
        gameTable.setDefaultRenderer(Integer.class,new GameTableCellRenderer());
        removeBorderOfCell();
        addScorePanel();
        gamePanel.add(gameTable,BorderLayout.CENTER);
        add(gamePanel);
        controller = new GameController(this,model);
        controller.updatePacmanLocation();
        controller.updateScore();
        controller.randomUpgradeThread();
        controller.upgradeDurationChecker();
        controller.setFoodCheckThread();
        addHeartPanel();
        setFocusable(true);
        controller.randomMoveGhosts();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                directionKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e.getKeyCode());
            }
        });
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl shift Q");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke,"gameOver");
        getRootPane().getActionMap().put("gameOver", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        pack();
        setSize(610,710);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    private void addScorePanel(){
        JLabel score = new JLabel("Score: " + 0);
        score.setFont(new Font(Font.SANS_SERIF,Font.ITALIC | Font.BOLD,15));
        score.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        scorePanel.setBackground(Color.black);
        scorePanel.add(score,gbc);
        gamePanel.add(scorePanel,BorderLayout.NORTH);
    }
    public synchronized void addHeartPanel(){
        heartPanel.removeAll();
        ImageIcon heart = new ImageIcon("game_images/heart.png");
        Image scaled= heart.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
        ImageIcon result = new ImageIcon(scaled);
        heartPanel.setBackground(Color.black);
        if(controller.getPacman().getLives() == 0){
            try {
                gameOver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < controller.getPacman().getLives(); i++){
            JLabel label = new JLabel(result);
            label.setBackground(Color.black);
            heartPanel.add(label);
        }
        heartPanel.revalidate();
        heartPanel.repaint();
        gamePanel.add(heartPanel,BorderLayout.SOUTH);
    }
    private void removeBorderOfCell(){
        gameTable.setShowGrid(false);
        gameTable.setShowVerticalLines(false);
        gameTable.setShowHorizontalLines(false);
        gameTable.setRowMargin(0);
        gameTable.getColumnModel().setColumnMargin(0);
    }

    public void updateScorePanel() {
        ((JLabel)scorePanel.getComponent(0)).setText("Score: " + controller.getScore());
    }
    public synchronized void updatePacLoc(int y,int x){
        controller.getPacman().setX(1);
        controller.getPacman().setY(1);
        controller.getPacman().setLives(controller.getPacman().getLives()-1);
        model.setValueAt(CellType.EMPTY.getValue(),y,x);
    }
    public void gameOver() throws IOException {
        int score = controller.getScore();
        controller.reset();
        removeAll();
        setVisible(false);
        String name = JOptionPane.showInputDialog(null,"GAME OVER! Please enter your name.","Score Lists",JOptionPane.PLAIN_MESSAGE);
        if(name != null) {
            PrintWriter pw = new PrintWriter(new FileWriter("scores.txt", true), true);
            pw.println(new Record(name, score));
        }
    }
    public synchronized void updateHeartPanel(){
        if(controller.getPacman().getLives() == 0){
            try {
                gameOver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        heartPanel.removeAll();
        ImageIcon heart = new ImageIcon("game_images/heart.png");
        Image scaled= heart.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
        ImageIcon result = new ImageIcon(scaled);
        heartPanel.setBackground(Color.black);
        for(int i = 0; i < controller.getPacman().getLives(); i++){
            JLabel label = new JLabel(result);
            label.setBackground(Color.black);
            heartPanel.add(label);
        }
        heartPanel.revalidate();
        heartPanel.repaint();
    }
    private void directionKey(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP -> {
                if(!isMovingUp){
                    isMovingUp = true;
                    map.remove(keyCode,map.get(keyCode));
                    map.put(keyCode,isMovingUp);
                    movePacman(keyCode);
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(!isMovingDown){
                    isMovingDown = true;
                    map.remove(keyCode,map.get(keyCode));
                    map.put(keyCode,isMovingDown);
                    movePacman(keyCode);
                }
            }
            case KeyEvent.VK_LEFT -> {
                if(!isMovingLeft){
                    isMovingLeft = true;
                    map.remove(keyCode,map.get(keyCode));
                    map.put(keyCode,isMovingLeft);
                    movePacman(keyCode);
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if(!isMovingRight){
                    isMovingRight = true;
                    map.remove(keyCode,map.get(keyCode));
                    map.put(keyCode,isMovingRight);
                    movePacman(keyCode);
                }
            }
        }
    }
    private void movePacman(int keyCode){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(map.get(keyCode)){
                    int x = controller.getPacman().getX();
                    int y = controller.getPacman().getY();
                    try{
                        switch (keyCode){
                            case KeyEvent.VK_UP -> controller.getPacman().up();
                            case KeyEvent.VK_DOWN -> controller.getPacman().down();
                            case KeyEvent.VK_LEFT -> controller.getPacman().left();
                            case KeyEvent.VK_RIGHT -> controller.getPacman().right();
                        }
                    }catch (ClassCastException e){
                        updatePacLoc(y,x);
                        break;
                    }
                    model.setValueAt(CellType.EMPTY.getValue(),y,x);
                    controller.updatePacmanLocation();
                    try{
                        Thread.sleep(controller.getPacman().getSpeed() * 40);
                    } catch (InterruptedException e ){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void handleKeyRelease(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_UP -> {
                isMovingUp = false;
                map.remove(keyCode,map.get(keyCode));
                map.put(keyCode,isMovingUp);
            }
            case KeyEvent.VK_DOWN -> {
                isMovingDown = false;
                map.remove(keyCode,map.get(keyCode));
                map.put(keyCode,isMovingDown);
            }
            case KeyEvent.VK_LEFT -> {
                isMovingLeft = false;
                map.remove(keyCode,map.get(keyCode));
                map.put(keyCode,isMovingLeft);
            }
            case KeyEvent.VK_RIGHT -> {
                isMovingRight = false;
                map.remove(keyCode,map.get(keyCode));
                map.put(keyCode,isMovingRight);
            }
        }
    }
    private void closeWindow(){
        System.out.println(Thread.activeCount());
        controller.reset();
        removeAll();
        System.out.println(Thread.activeCount());
        setVisible(false);
        dispose();
    }
}

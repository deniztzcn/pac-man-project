import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public abstract class Ghost implements Moveable {
    private ImageIcon[] upMoves;
    private ImageIcon[] downMoves;
    private ImageIcon[] leftMoves;
    private ImageIcon[] rightMoves;
    private ImageIcon[] deadMoves;
    private ImageIcon[] currentMoves;
    private int speed;
    private int x;
    private int y;
    private AbstractTableModel model;
    private boolean flag;
    private boolean isCollector;
    private GameController controller;
    private Pacman pac;

    public Ghost(int x, int y, AbstractTableModel model,GameController controller) {
        this.model = model;
        this.speed = 4;
        this.x = x;
        this.y = y;
        upMoves = new ImageIcon[2];
        downMoves = new ImageIcon[2];
        leftMoves = new ImageIcon[2];
        rightMoves = new ImageIcon[2];
        deadMoves = new ImageIcon[2];
        deadMoves[0] = new ImageIcon("game_images/bl1.png");
        deadMoves[1] = new ImageIcon("game_images/bl2.png");
        this.flag = false;
        loadImages();
        currentMoves = rightMoves;
        this.controller = controller;
        pac = controller.getPacman();
        isCollector = false;
    }

    public ImageIcon[] getUpMoves() {
        return upMoves;
    }

    public ImageIcon[] getDownMoves() {
        return downMoves;
    }

    public ImageIcon[] getLeftMoves() {
        return leftMoves;
    }

    public ImageIcon[] getRightMoves() {
        return rightMoves;
    }

    public ImageIcon[] getDeadMoves() {
        return deadMoves;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    @Override
    public synchronized boolean up() {
        if(model.getValueAt(y-1,x) instanceof Integer &&
                (int)model.getValueAt(y-1,x) == CellType.FOOD.getValue() &&
                isCollector()){
            setY(y-1);
            pac.setScore(pac.getScore()+5);
            controller.updateScore();
            return true;
        }
        if(isEatable()){
            setY(y-1);
            return true;
        }
        if(model.getValueAt(y-1,x) instanceof Upgrade) {
            this.setY(y - 1);
            currentMoves = upMoves;
            return true;
        }
        if (model.getValueAt(y - 1, x) instanceof Integer && (int) model.getValueAt(y - 1, x) != CellType.WALL.getValue()) {
            setY(y - 1);
            currentMoves = upMoves;
            return true;
        } else if (model.getValueAt(y - 1, x) instanceof Pacman) {
            setY(y - 1);
            controller.getGameWindow().updatePacLoc(controller.getPacman().getY(),controller.getPacman().getX());
            currentMoves = upMoves;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean down() {
        if(model.getValueAt(y+1,x) instanceof Integer &&
                (int)model.getValueAt(y+1,x) == CellType.FOOD.getValue() &&
                isCollector()){
            setY(y+1);
            pac.setScore(pac.getScore()+5);
            controller.updateScore();
            return true;
        }
        if(isEatable()){
            setY(y+1);
            return true;
        }
        if(model.getValueAt(y+1,x) instanceof Upgrade){
            this.setY(y+1);
            currentMoves = downMoves;
            return true;
        }
        if (model.getValueAt(y + 1, x) instanceof Integer &&
                (int) model.getValueAt(y + 1, x) == CellType.GATE.getValue() &&
                !isEatable()) {
            return false;
        }
        if (model.getValueAt(y + 1, x) instanceof Integer && (int) model.getValueAt(y + 1, x) != CellType.WALL.getValue()) {
            setY(y + 1);
            currentMoves = downMoves;
            return true;
        } else if (model.getValueAt(y + 1, x) instanceof Pacman) {
            setY(y + 1);
            controller.getGameWindow().updatePacLoc(controller.getPacman().getY(),controller.getPacman().getX());
            currentMoves = downMoves;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean left() {
        if(model.getValueAt(y,x-1) instanceof Integer &&
                (int)model.getValueAt(y,x-1) == CellType.FOOD.getValue() &&
                isCollector()){
            setX(x-1);
            pac.setScore(pac.getScore()+5);
            controller.updateScore();
            return true;
        }
        if(isEatable()){
            setX(x-1);
            return true;
        }
        if(model.getValueAt(y,x-1) instanceof Upgrade){
            this.setX(x-1);
            currentMoves = leftMoves;
            return true;
        }
        if (model.getValueAt(y, x - 1) instanceof Integer && (int) model.getValueAt(y, x - 1) != CellType.WALL.getValue()) {
            setX(x - 1);
            currentMoves = leftMoves;
            return true;
        } else if (model.getValueAt(y, x - 1) instanceof Pacman) {
            setX(x - 1);
            controller.getGameWindow().updatePacLoc(controller.getPacman().getY(),controller.getPacman().getX());
            currentMoves = leftMoves;
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean right() {
        if(model.getValueAt(y,x+1) instanceof Integer &&
                (int)model.getValueAt(y,x+1) == CellType.FOOD.getValue() &&
                isCollector()){
            setX(x+1);
            pac.setScore(pac.getScore()+5);
            controller.updateScore();
            return true;
        }
        if(isEatable()){
            setX(x+1);
            return true;
        }
        if(model.getValueAt(y,x+1) instanceof Upgrade){
            this.setX(x+1);
            currentMoves = rightMoves;
            return true;
        }
        if (model.getValueAt(y, x + 1) instanceof Integer && (int) model.getValueAt(y, x + 1) != CellType.WALL.getValue()) {
            setX(x + 1);
            currentMoves = rightMoves;
            return true;
        } else if (model.getValueAt(y, x + 1) instanceof Pacman) {
            setX(x + 1);
            controller.getGameWindow().updatePacLoc(controller.getPacman().getY(),controller.getPacman().getX());
            currentMoves = rightMoves;
            return true;
        }
        return false;
    }

    public boolean isEatable() {
        return flag;
    }

    public void setEatable(boolean flag) {
        this.flag = flag;
    }
    @Override
    public synchronized ImageIcon[] getCurrentMoves() {
        return currentMoves;
    }

    public void setCurrentMoves(ImageIcon[] currentMoves) {
        this.currentMoves = currentMoves;
    }

    abstract void loadImages();

    public boolean isCollector() {
        return isCollector;
    }

    public void setCollector(boolean collector) {
        isCollector = collector;
    }
}

import javax.swing.*;

public class Pacman implements Moveable {
    private ImageIcon[] upMoves;
    private ImageIcon[] downMoves;
    private ImageIcon[] leftMoves;
    private ImageIcon[] rightMoves;
    private int speed;
    private int x;
    private int y;
    private GameTableModel model;
    private volatile int score;
    private ImageIcon[] currentMoves;
    private volatile boolean gotUpgrade;
    private volatile int foodVal;
    private volatile int lives;
    private GameController controller;

    public Pacman(int x, int y, GameTableModel model,GameController controller) {
        this.x = x;
        this.y = y;
        this.model = model;
        this.score = 0;
        upMoves = new ImageIcon[6];
        downMoves = new ImageIcon[6];
        leftMoves = new ImageIcon[6];
        rightMoves = new ImageIcon[6];
        loadImages();
        currentMoves = leftMoves;
        this.speed = 5;
        gotUpgrade = false;
        foodVal = 10;
        lives = 3;
        this.controller = controller;
    }
    private void loadImages(){
        upMoves[0] = new ImageIcon("game_images/a1.png");
        upMoves[1] = new ImageIcon("game_images/b2.png");
        upMoves[2] = new ImageIcon("game_images/b3.png");
        upMoves[3] = new ImageIcon("game_images/b4.png");
        upMoves[4] = new ImageIcon("game_images/b5.png");
        upMoves[5] = new ImageIcon("game_images/b6.png");

        downMoves[0] = new ImageIcon("game_images/a1.png");
        downMoves[1] = new ImageIcon("game_images/d2.png");
        downMoves[2] = new ImageIcon("game_images/d3.png");
        downMoves[3] = new ImageIcon("game_images/d4.png");
        downMoves[4] = new ImageIcon("game_images/d5.png");
        downMoves[5] = new ImageIcon("game_images/d6.png");

        leftMoves[0] = new ImageIcon("game_images/a1.png");
        leftMoves[1] = new ImageIcon("game_images/c2.png");
        leftMoves[2] = new ImageIcon("game_images/c3.png");
        leftMoves[3] = new ImageIcon("game_images/c4.png");
        leftMoves[4] = new ImageIcon("game_images/c5.png");
        leftMoves[5] = new ImageIcon("game_images/c6.png");

        rightMoves[0] = new ImageIcon("game_images/a1.png");
        rightMoves[1] = new ImageIcon("game_images/a2.png");
        rightMoves[2] = new ImageIcon("game_images/a3.png");
        rightMoves[3] = new ImageIcon("game_images/a4.png");
        rightMoves[4] = new ImageIcon("game_images/a5.png");
        rightMoves[5] = new ImageIcon("game_images/a6.png");
    }

    public int getSpeed() {
        return speed;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean up() {
        Object target = model.getValueAt(y-1,x);
        if(target instanceof Upgrade){
            Upgrade upg =((Upgrade) target);
            score += foodVal;
            controller.updateScore();
            this.setY(y-1);
            currentMoves = upMoves;
            if(upg.isRelatedGhosts()){
                upg.accept(controller.getGhosts());
                return true;
            }else {
                upg.accept(this);
            }
            gotUpgrade = true;
            return true;
        }
        if(target instanceof Ghost){
            if(((Ghost)target).isEatable()){
                score += foodVal*5;
                controller.updateScore();
                this.setY(y-1);
                ((Ghost) target).setY(model.getMiddleRow());
                ((Ghost) target).setX(model.getMiddleColumn());
                ((Ghost) target).setEatable(false);
                currentMoves = upMoves;
                return true;
            }
        }
        if((int)target != CellType.WALL.getValue()){
            if((int)target == CellType.FOOD.getValue()){
                score += foodVal;
                controller.updateScore();
            }
            this.setY(y-1);
            currentMoves = upMoves;
            return true;
        }
        return false;
    }

    @Override
    public boolean down() {
        Object target = model.getValueAt(y+1,x);
        if(target instanceof Upgrade){
            Upgrade upg =((Upgrade) target);
            score += foodVal;
            controller.updateScore();
            this.setY(y+1);
            currentMoves = downMoves;
            if(upg.isRelatedGhosts()){
                upg.accept(controller.getGhosts());
                return true;
            }else {
                upg.accept(this);
            }
            gotUpgrade = true;
            return true;
        }
        if(target instanceof Ghost){
            if(((Ghost)target).isEatable()){
                score += foodVal*5;
                controller.updateScore();
                this.setY(y+1);
                ((Ghost) target).setY(model.getMiddleRow());
                ((Ghost) target).setX(model.getMiddleColumn());
                ((Ghost) target).setEatable(false);
                currentMoves = downMoves;
                return true;
            }
        }
        if(((int)target != CellType.GATE.getValue()) && ((int)target != CellType.WALL.getValue())){
            if((int)target == CellType.FOOD.getValue()){
                score += foodVal;
                controller.updateScore();
            }
            this.setY(y + 1);
            currentMoves = downMoves;
            return true;
        }
        return false;
    }

    @Override
    public boolean left() {
        Object target = model.getValueAt(y,x-1);
        if(target instanceof Upgrade){
            Upgrade upg =((Upgrade) target);
            score += foodVal;
            controller.updateScore();
            this.setX(x-1);
            currentMoves = leftMoves;
            if(upg.isRelatedGhosts()){
                upg.accept(controller.getGhosts());
                return true;
            }else {
                upg.accept(this);
            }
            gotUpgrade = true;
            return true;
        }
        if(target instanceof Ghost){
            if(((Ghost)target).isEatable()){
                score += foodVal*5;
                controller.updateScore();
                this.setX(x-1);
                ((Ghost) target).setY(model.getMiddleRow());
                ((Ghost) target).setX(model.getMiddleColumn());
                ((Ghost) target).setEatable(false);
                currentMoves = leftMoves;
                return true;
            }
        }
        if((int)target != CellType.WALL.getValue()){
            if((int)target == CellType.FOOD.getValue()){
                score += foodVal;
                controller.updateScore();
            }
            this.setX(x-1);
            currentMoves = leftMoves;
            return true;
        }
        return false;
    }

    @Override
    public boolean right() {
        Object target = model.getValueAt(y,x+1);
        if(target instanceof Upgrade){
            Upgrade upg =((Upgrade) target);
            score += foodVal;
            controller.updateScore();
            this.setX(x+1);
            currentMoves = rightMoves;
            if(upg.isRelatedGhosts()){
                upg.accept(controller.getGhosts());
                return true;
            }else {
                upg.accept(this);
            }
            gotUpgrade = true;
            return true;
        }
        if(target instanceof Ghost){
            if(((Ghost)target).isEatable()){
                score += foodVal*5;
                controller.updateScore();
                this.setX(x+1);
                ((Ghost) target).setY(model.getMiddleRow());
                ((Ghost) target).setX(model.getMiddleColumn());
                ((Ghost) target).setEatable(false);
                currentMoves = rightMoves;
                return true;
            }
        }
        if((int)target != CellType.WALL.getValue()){
            if((int)target == CellType.FOOD.getValue()){
                score += foodVal;
                controller.updateScore();
            }
            this.setX(x + 1);
            currentMoves = rightMoves;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    @Override
    public synchronized ImageIcon[] getCurrentMoves() {
        return currentMoves;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public synchronized void setGotUpgrade(boolean gotUpgrade) {
        this.gotUpgrade = gotUpgrade;
    }

    public synchronized boolean isGotUpgrade() {
        return gotUpgrade;
    }

    public int getFoodVal() {
        return foodVal;
    }

    public void setFoodVal(int foodVal) {
        this.foodVal = foodVal;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        controller.updateHeartPanel();
    }

    public void setScore(int score) {
        this.score = score;
    }
}

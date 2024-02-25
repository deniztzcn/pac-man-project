import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class GameController {
    private static volatile int upgradeCounter;
    private Pacman pacman;
    private GameWindow gameWindow;
    private GameTableModel model;
    private int score;
    private Ghost[] ghosts;
    private Thread updateScoreThread;
    private Thread updatePacmanLocationThread;
    private Thread randomMoveThread;
    private Thread upgradeThread;
    private Thread upgradeDurationThread;
    private Upgrade[] upgrades;
    private SpeedUpgrade speedUpgrade;
    private Upgrade doublePointUpgrade;
    private Upgrade extraHeartUpgrade;
    private Upgrade makeGhostEatableUpgrade;
    private Upgrade collectorUpgrade;
    private Thread foodCheckThread;
    private static volatile boolean stopThreads;


    public GameController(GameWindow gameWindow, GameTableModel model) {
        stopThreads = false;
        upgradeCounter = 0;
        upgrades = new Upgrade[5];
        this.gameWindow = gameWindow;
        this.model = model;
        this.score = 0;
        pacman = new Pacman(1, 1, model, this);
        ghosts = new Ghost[4];
        ghosts[0] = new RedGhost(model.getMiddleColumn(), model.getBaseTopRow() - 1, model, this);
        ghosts[1] = new PinkGhost(2, model.getRow() - 2, model, this);
        ghosts[2] = new GreenGhost(model.getColumn() - 2, model.getRow() - 2, model, this);
        ghosts[3] = new YellowGhost(model.getColumn() - 2, 2, model, this);
        speedUpgrade = new SpeedUpgrade();

        collectorUpgrade = new CollectorUpgrade();
        doublePointUpgrade = new DoublePointUpgrade();
        extraHeartUpgrade = new HeartUpgrade();
        makeGhostEatableUpgrade = new EatableGhostUpgrade();
        upgrades[0] = speedUpgrade;
        upgrades[1] = doublePointUpgrade;
        upgrades[2] = extraHeartUpgrade;
        upgrades[3] = makeGhostEatableUpgrade;
        upgrades[4] = collectorUpgrade;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updatePacmanLocation() {
        updatePacmanLocationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("UpdatePacmanLoc Thread");
                model.setValueAt(pacman, pacman.getY(), pacman.getX());
            }
        });
        updatePacmanLocationThread.start();
        updatePacmanLocationThread.interrupt();
    }

    public void updateScore() {
        updateScoreThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("updateScoreThread");
                score = pacman.getScore();
                gameWindow.updateScorePanel();

            }
        });
        updateScoreThread.start();
        updateScoreThread.interrupt();
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void randomMoveGhosts() {
        Random random = new Random();
        randomMoveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<Ghost, Object[]> map = new HashMap<>();
                Map<Ghost, Integer> directionMap = new HashMap<>();
                for (int i = 0; i < ghosts.length; i++) {
                    map.put(ghosts[i], new Object[3]);
                }
                while (!stopThreads) {
                    for (Ghost g : ghosts) {
                        List<Integer> directions = emptyDirections(g);
                        if (directions.contains(directionMap.get(g)) && directions.size() - 1 > 0)
                            directions.remove(Integer.valueOf(directionMap.get(g)));
                        if (directions.isEmpty()) {
                            continue;
                        }
                        int index = random.nextInt(directions.size());
                        int direction = directions.get(index);
                        int x = g.getX();
                        int y = g.getY();
                        if (direction == Directions.up.getValue()) {
                            if (g.up()) {
                                if (!(model.getValueAt(y, x) instanceof Upgrade)) {
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    if (map.get(g)[0] != null) {
                                        model.setValueAt(map.get(g)[0], (int) map.get(g)[1], (int) map.get(g)[2]);
                                    }
                                }


                                Object cellVal = model.getValueAt(y - 1, x);
                                if (cellVal instanceof Pacman) {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.down.getValue());
                                } else {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    map.get(g)[0] = cellVal;
                                    map.get(g)[1] = g.getY();
                                    map.get(g)[2] = g.getX();
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.down.getValue());
                                }
                            }
                        }
                        if (direction == Directions.down.getValue()) {
                            if (g.down()) {
                                if (!(model.getValueAt(y, x) instanceof Upgrade)) {
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    if (map.get(g)[0] != null) {
                                        model.setValueAt(map.get(g)[0], (int) map.get(g)[1], (int) map.get(g)[2]);
                                    }
                                }else if(g.isCollector()){
                                    model.setValueAt(g, g.getY(), g.getX());
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    continue;
                                }
                                Object cellVal = model.getValueAt(y + 1, x);
                                if (cellVal instanceof Pacman) {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.up.getValue());
                                } else {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    map.get(g)[0] = cellVal;
                                    map.get(g)[1] = g.getY();
                                    map.get(g)[2] = g.getX();
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.up.getValue());
                                }
                            }
                        }
                        if (direction == Directions.left.getValue()) {
                            if (g.left()) {
                                if (!(model.getValueAt(y, x) instanceof Upgrade)) {
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    if (map.get(g)[0] != null) {
                                        model.setValueAt(map.get(g)[0], (int) map.get(g)[1], (int) map.get(g)[2]);
                                    }
                                }
                                Object cellVal = model.getValueAt(y, x - 1);
                                if (cellVal instanceof Pacman) {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.right.getValue());
                                } else {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    map.get(g)[0] = cellVal;
                                    map.get(g)[1] = g.getY();
                                    map.get(g)[2] = g.getX();
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.right.getValue());
                                }
                            }
                        }
                        if (direction == Directions.right.getValue()) {
                            if (g.right()) {
                                if (!(model.getValueAt(y, x) instanceof Upgrade)) {
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    if (map.get(g)[0] != null) {
                                        model.setValueAt(map.get(g)[0], (int) map.get(g)[1], (int) map.get(g)[2]);
                                    }
                                }
                                Object cellVal = model.getValueAt(y, x + 1);
                                if (cellVal instanceof Pacman) {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    model.setValueAt(CellType.EMPTY.getValue(), y, x);
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.left.getValue());
                                } else {
                                    model.setValueAt(g, g.getY(), g.getX());
                                    map.get(g)[0] = cellVal;
                                    map.get(g)[1] = g.getY();
                                    map.get(g)[2] = g.getX();
                                    directionMap.remove(g, directionMap.get(g));
                                    directionMap.put(g, Directions.left.getValue());
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(ghosts[0].getSpeed() * 40);
                    } catch (InterruptedException e) {
                        System.out.println("RandomMoves interrupted");
                    }
                }
            }
        });
        randomMoveThread.start();
    }

    public List<Integer> emptyDirections(Ghost g) {
        List<Integer> directions = new ArrayList<>();
        Object leftTarget = model.getValueAt(g.getY(),g.getX() - 1);
        Object rightTarget = model.getValueAt(g.getY(),g.getX() + 1);
        Object topTarget = model.getValueAt(g.getY() - 1,g.getX());
        Object bottomTarget = model.getValueAt(g.getY()+1,g.getX());
        if (! (leftTarget instanceof Ghost)) {
            if (leftTarget instanceof Integer) {
                if ((int) leftTarget != CellType.WALL.getValue()) {
                    directions.add(Directions.left.getValue());
                }
            } else if (leftTarget instanceof Pacman) {
                directions.add(Directions.left.getValue());
            } else if (leftTarget instanceof Upgrade) {
                directions.add(Directions.left.getValue());
            }
        } else {
            directions.add(Directions.right.getValue());
        }

        if (!(rightTarget instanceof Ghost)) {
            if (rightTarget instanceof Integer) {
                if ((int) rightTarget != CellType.WALL.getValue()) {
                    directions.add(Directions.right.getValue());
                }
            } else if (rightTarget instanceof Pacman) {
                directions.add(Directions.right.getValue());
            } else if (rightTarget instanceof Upgrade) {
                directions.add(Directions.right.getValue());
            }
        } else {
            directions.add(Directions.left.getValue());
        }

        if (!(topTarget instanceof Ghost)) {
            if (topTarget instanceof Integer) {
                if ((int) topTarget != CellType.WALL.getValue()) {
                    directions.add(Directions.up.getValue());
                }
            } else if (topTarget instanceof Pacman) {
                directions.add(Directions.up.getValue());
            } else if (topTarget instanceof Upgrade) {
                directions.add(Directions.up.getValue());
            }
        } else {
            directions.add(Directions.down.getValue());
        }

        if (!(bottomTarget instanceof Ghost)) {
            if (bottomTarget instanceof Integer) {
                if ((int) bottomTarget != CellType.WALL.getValue()) {
                    directions.add(Directions.down.getValue());
                }
            } else if (bottomTarget instanceof Pacman) {
                directions.add(Directions.down.getValue());
            } else if (bottomTarget instanceof Upgrade) {
                directions.add(Directions.down.getValue());
            }
        } else {
            directions.add(Directions.up.getValue());
        }
        return directions;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void reset() {
        setStopThreads(true);
        closeRandomMoveThread();
        closePacmanLocThread();
        closeUpdateScoreThread();
        closeUpgradeThread();
        closeUpgradeDurationThread();
        closeFoodCheckThread();
    }

    public void closePacmanLocThread() {
        updatePacmanLocationThread.interrupt();
        try {
            updatePacmanLocationThread.join();
        } catch (InterruptedException e) {
            System.out.println("updatePacman interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void closeUpdateScoreThread() {
        updateScoreThread.interrupt();
        try {
            updateScoreThread.join();
        } catch (InterruptedException e) {
            System.out.println("update score interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void closeRandomMoveThread() {
        randomMoveThread.interrupt();
        try {
            randomMoveThread.join();
        } catch (InterruptedException e) {
            System.out.println("randomMove interrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void createUpgrade(int y, int x) {
        Random random = new Random();
        int luck = random.nextInt(4);
        if (luck > 1) {
            increase();
            int index = random.nextInt(upgrades.length);
            model.setValueAt(upgrades[index], y, x);
        }
    }

    public void randomUpgradeThread() {
        upgradeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (upgradeCounter <= 10) {
                    for (Ghost g : ghosts) {
                        createUpgrade(g.getY(), g.getX());
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("RandomUpgradeInterrupted");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        upgradeThread.start();
    }

    public void closeUpgradeThread() {
        upgradeThread.interrupt();
        try {
            upgradeThread.join();
        } catch (InterruptedException e) {
            System.out.println("upgradeInterrupted");
            Thread.currentThread().interrupt();
        }
    }

    public void upgradeDurationChecker() {
        upgradeDurationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopThreads) {
                    try {
                        if (getPacman().isGotUpgrade()) {
                            decrease();
                            System.out.println(getPacman().isGotUpgrade());
                            Thread.sleep(3000);
                            getPacman().setSpeed(5);
                            getPacman().setFoodVal(10);
                            getPacman().setGotUpgrade(false);
                        }
                        if (getGhosts()[0].isEatable() || getGhosts()[0].isCollector()) {
                            decrease();
                            Thread.sleep(5000);
                            for (Ghost g : ghosts) {
                                g.setEatable(false);
                                g.setCollector(false);
                            }
                        }
                    } catch (InterruptedException e) {
                        System.out.println("UpgradeDurationInterrupted");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        upgradeDurationThread.start();
    }

    public void closeUpgradeDurationThread() {
        upgradeDurationThread.interrupt();
        try {
            upgradeDurationThread.join();
        } catch (InterruptedException e) {
            System.out.println("upgradeDurationInterr");
            Thread.currentThread().interrupt();
        }
    }

    public void updateHeartPanel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("HeartUpdateThread");
                gameWindow.updateHeartPanel();
            }
        }).start();
    }
    public void setFoodCheckThread(){
        foodCheckThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopThreads){
                    if(model.isFoodFinished()){
                        try {
                            gameWindow.gameOver();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        foodCheckThread.start();
    }
    public void closeFoodCheckThread(){
        foodCheckThread.interrupt();
        try {
            foodCheckThread.join();
        } catch (InterruptedException e) {
            System.out.println("food check interrupted");
            foodCheckThread.interrupt();
        }
    }
    public Ghost[] getGhosts() {
        return ghosts;
    }
    private synchronized void increase(){
        upgradeCounter++;
    }
    private synchronized void decrease(){
        upgradeCounter--;
    }
    private synchronized void setStopThreads(boolean bool){
        stopThreads = true;
    }
}

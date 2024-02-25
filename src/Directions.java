public enum Directions {
    left,
    right,
    up,
    down;


    public int getValue(){
        switch (this){
            case left -> {
                return 0;
            }
            case right -> {
                return 1;
            }
            case up -> {
                return 2;
            }
            case down -> {
                return 3;
            }
            default -> {
                return -1;
            }
        }
    }
}

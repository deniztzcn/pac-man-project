public enum CellType {
    FOOD,
    EMPTY,
    WALL,
    GATE,
    UPGRADE;

    public int getValue(){
        switch(this){
            case FOOD:
                return 1;
            case GATE:
                return 2;
            case EMPTY:
                return 3;
            case UPGRADE:
                return 4;
            case WALL:
                return 0;
            default:
                return -1;
        }
    }
}

package game;

public enum CoordinateDirection {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0);

    private final int HORIZONTAL_MOVEMENT;
    private final int VERTICAL_MOVEMENT;

    CoordinateDirection(int x, int y) {
        this.HORIZONTAL_MOVEMENT = x;
        this.VERTICAL_MOVEMENT = y;
    }

    public int getHORIZONTAL_MOVEMENT() {
        return HORIZONTAL_MOVEMENT;
    }

    public int getVERTICAL_MOVEMENT() {
        return VERTICAL_MOVEMENT;
    }
}

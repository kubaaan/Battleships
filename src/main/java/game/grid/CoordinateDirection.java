package game.grid;

import lombok.Getter;

public enum CoordinateDirection {

        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        @Getter
        private final int HORIZONTAL_MOVEMENT;
        @Getter
        private final int VERTICAL_MOVEMENT;

        CoordinateDirection(int x, int y) {
            this.HORIZONTAL_MOVEMENT = x;
            this.VERTICAL_MOVEMENT = y;
        }
}

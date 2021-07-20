package battleships.game.grid;

import lombok.Getter;

public enum Direction {

        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        @Getter
        private final int horizontalMovement;
        @Getter
        private final int verticalMovement;

        Direction(int x, int y) {
            this.horizontalMovement = x;
            this.verticalMovement = y;
        }
}

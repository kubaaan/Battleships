package battleships.game.grid;

import battleships.game.Utilities;
import lombok.Getter;

public class Ship {

    @Getter
    private final int address;
    @Getter
    private final Direction direction;
    @Getter
    private final ShipType type;

    public Ship(int x, int y, Direction direction, ShipType type) {
        this.address = Utilities.convertCoordinatesToAddress(x, y);
        this.direction = direction;
        this.type = type;
    }
}

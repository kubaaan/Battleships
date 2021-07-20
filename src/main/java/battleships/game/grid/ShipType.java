package battleships.game.grid;

public enum ShipType {
    CARRIER(5),
    CRUISER(4),
    SUBMARINE_1(3),
    SUBMARINE_2(3),
    DESTROYER(2);

    private final int LENGTH;

    ShipType(int length) {
        this.LENGTH = length;
    }

    public int getLength() {
        return LENGTH;
    }
}


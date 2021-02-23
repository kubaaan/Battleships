package game;

public enum ShipType {
    CARRIER(5),
    CRUISER(4),
    SUBMARINE_1(3),
    SUBMARINE_2(3),
    DESTROYER(2);

    private int length;
    ShipType(int length) {
        this.length=length;
    }

    public int getLength() {
        return length;
    }
}

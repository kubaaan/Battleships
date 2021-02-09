package game;

public enum ShipType {
    CARRIER(5), //1
    CRUISER(4), //1
    SUBMARINE_1(3), //2
    SUBMARINE_2(3),
    DESTROYER(2); //1

    private int length;
    ShipType(int length) {
        this.length=length;
    }

    public int getLength() {
        return length;
    }
}

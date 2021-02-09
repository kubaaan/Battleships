package game;

public class Ship {
    private String type;
    private int length;

    public Ship(String type, int length){
        this.type = type;
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
}

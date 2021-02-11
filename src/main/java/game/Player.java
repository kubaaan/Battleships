package game;

public abstract class Player {
    protected String name;
    protected Grid grid;
    protected int numberOfShips;

    public Player(String name) {
        this.name = name;
        this.grid = new Grid();
        this.numberOfShips = 0;
    }

    public abstract void deployShip();
    public abstract void guess(Player rival);
}

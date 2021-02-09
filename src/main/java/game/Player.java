package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected  String name;
    protected Grid grid;
    protected int numberOfShips;
    public List<ShipType> shipsList;

    public Player(String name) {
        this.name = name;
        this.grid = new Grid();
        this.shipsList = new ArrayList<>();
        this.shipsList.add(ShipType.CARRIER);
        this.shipsList.add(ShipType.CRUISER);
        this.shipsList.add(ShipType.SUBMARINE_1);
        this.shipsList.add(ShipType.SUBMARINE_2);
        this.shipsList.add(ShipType.DESTROYER);
    }

    public abstract void deployShip(ShipType shipType);

    public abstract void guess(Player rival);

    public List<ShipType> getShipsList() {
        return shipsList;
    }
}


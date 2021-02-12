package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected  String name;
    protected  Grid grid;
    protected int numberOfShips;
    protected int shipPoints;
    private List<ShipType> shipsList;

    public Player(String name) {
        this.name = name;
        this.grid = new Grid();
        this.shipsList = new ArrayList<>();
        this.shipsList.add(ShipType.CARRIER);
        this.shipsList.add(ShipType.CRUISER);
        this.shipsList.add(ShipType.SUBMARINE_1);
        this.shipsList.add(ShipType.SUBMARINE_2);
        this.shipsList.add(ShipType.DESTROYER);
        this.shipPoints = 0;
        for(ShipType type : shipsList){
            this.shipPoints += type.getLength();
        }
    }

    public abstract void deployShip(ShipType shipType);
    public abstract void guess(Player opponent);

    public List<ShipType> getShipsList() {
        return shipsList;
    }
}

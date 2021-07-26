package battleships.game.player;

import battleships.game.grid.Grid;
import battleships.game.grid.ShipType;
import battleships.model.DeployRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private final int startingShipPoints;
    protected String name;
    @Getter protected Grid grid;
    protected int numberOfShips;
    @Getter @Setter
    protected int shipPoints;
    @Getter private final List<ShipType> shipsList;

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
        for (ShipType type : shipsList) {
            this.shipPoints += type.getLength();
        }
        startingShipPoints = shipPoints;
    }

    public boolean deployShip(DeployRequest deployRequest){
        return this.grid.deployShip(deployRequest);
    }

    public abstract int guess(Player opponent);

    public void printStatistics(int currentTurn) {
        System.out.println(name);
        System.out.println("Played for " + currentTurn + " turns.");
        System.out.println("Percentage of missed shots: " + getPercentageOfMissed(currentTurn) + "%");
    }

    private float getPercentageOfMissed(int totalShots) {
        float result = 100f * (totalShots - startingShipPoints) / totalShots;
        return Math.round(result * 100) / 100f;
    }
}

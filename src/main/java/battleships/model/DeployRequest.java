package battleships.model;

import battleships.game.grid.Direction;
import battleships.game.grid.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DeployRequest {

    private ShipType shipType;
    private int address;
    private Direction direction;
    private int length;


    @Override
    public String toString() {
        return "DeployRequest{" +
                "shipType=" + shipType +
                ", address=" + address +
                ", direction=" + direction +
                ", length=" + length +
                '}';
    }
}

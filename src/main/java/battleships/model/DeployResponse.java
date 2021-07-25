package battleships.model;

import battleships.game.grid.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@AllArgsConstructor
public class DeployResponse {

    @Getter @Setter
    private Map<ShipType,Integer> shipsToDeploy;
}

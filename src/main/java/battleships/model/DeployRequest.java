package battleships.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DeployRequest {

    @Getter @Setter
    private int address;
    @Getter @Setter
    private String direction;
}

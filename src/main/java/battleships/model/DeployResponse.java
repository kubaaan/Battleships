package battleships.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
public class DeployResponse {

    @Getter @Setter
    private List<Integer> shipsToDeploy;
}

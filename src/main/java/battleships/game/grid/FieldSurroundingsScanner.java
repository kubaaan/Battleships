package battleships.game.grid;

import battleships.game.Utilities;
import battleships.game.grid.Direction;

import java.util.*;

public class FieldSurroundingsScanner {

    private FieldSurroundingsScanner() {
    }

    private static final List<Direction> DIRECTIONS = new LinkedList<>
            (Arrays.asList(Direction.UP, Direction.DOWN,
                    Direction.LEFT, Direction.RIGHT));

    public static Queue<String> getValidSurroundingTargets(Map<String, Grid.Field> fields, String address) {

        Queue<String> targets = new LinkedList<>();

        for (Direction direction : DIRECTIONS) {
            String examinedAddress = Utilities.getNeighbourAddress(address, direction);
            if (isPossibleTarget(fields, examinedAddress) != null) {
                targets.add(isPossibleTarget(fields, examinedAddress));
            }
        }

        return targets;
    }

    private static String isPossibleTarget(Map<String, Grid.Field> fields, String address) {

        if (isFieldInGrid(fields, address) && !isFieldRevealed(fields, address)) {
            return address;
        } else {
            return null;
        }
    }

    private static boolean isFieldInGrid(Map<String, Grid.Field> fields, String address) {
        return fields.containsKey(address);
    }

    private static boolean isFieldRevealed(Map<String, Grid.Field> fields, String address) {
        return fields.get(address).isRevealed();
    }
}
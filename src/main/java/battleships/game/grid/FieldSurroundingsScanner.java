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

    public static Queue<Integer> getValidSurroundingTargets(Map<Integer, Grid.Field> fields, int address) {

        Queue<Integer> targets = new LinkedList<>();

        for (Direction direction : DIRECTIONS) {
            int examinedAddress = Utilities.getNeighbourAddress(address, direction);
            if (isPossibleTarget(fields, examinedAddress) != -1) {
                targets.add(isPossibleTarget(fields, examinedAddress));
            }
        }

        return targets;
    }

    private static int isPossibleTarget(Map<Integer, Grid.Field> fields, int address) {

        if (isFieldInGrid(fields, address) && !isFieldRevealed(fields, address)) {
            return address;
        } else {
            return -1;
        }
    }

    private static boolean isFieldInGrid(Map<Integer, Grid.Field> fields, int address) {
        return fields.containsKey(address);
    }

    private static boolean isFieldRevealed(Map<Integer, Grid.Field> fields, int address) {
        return fields.get(address).isRevealed();
    }
}
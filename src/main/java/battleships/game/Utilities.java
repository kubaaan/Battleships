package battleships.game;

import battleships.game.grid.Direction;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Utilities {

    private Utilities() {
    }

    public static int convertCoordinatesToAddress(int x, int y) {
        return 10 * x + y;
    }

    public static int readCoordinateFromAddress(int address, String axe) {

        int coordinate;

        if (axe.equals("x")) {
            coordinate = address / 10;
        } else {
            coordinate = address % 10;
        }

        return coordinate;
    }

    public static int getNeighbourAddress(int address, Direction direction) {
        return getNeighbourAddress(address, direction, 1);
    }

    public static int getNeighbourAddress(int address, Direction direction, int moveLength) {
        int x = readCoordinateFromAddress(address, "x")
                + moveLength * direction.getHorizontalMovement();

        int y = readCoordinateFromAddress(address, "y")
                + moveLength * direction.getVerticalMovement();

        if (x < 0 || y < 0) {
            return -1;
        }

        return convertCoordinatesToAddress(x, y);
    }

    public static int generateRandomCoordinate(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    public static int generateRandomAddress(int size) {
        int x = generateRandomCoordinate(size);
        int y = generateRandomCoordinate(size);
        return 10 * x + y;
    }

    public static int generateParityAddress(int size, int parityLevel) {
        int x = generateRandomCoordinate(size);
        int y;
        int par_mod = x % parityLevel;
        if (par_mod == 0) {
            do {
                y = generateRandomCoordinate(size);
            } while (y % parityLevel != 0);
        } else {
            do {
                y = generateRandomCoordinate(size);
            } while ((y - par_mod) % parityLevel != 0);
        }
        return 10 * x + y;
    }

    public static Queue<Integer> mergeQueues(Queue<Integer> q1, Queue<Integer> q2) {
        Queue<Integer> mergedQueues = new LinkedList<>();

        while (!q1.isEmpty()) {
            mergedQueues.add(q1.poll());
        }
        while (!q2.isEmpty()) {
            mergedQueues.add(q2.poll());
        }

        return mergedQueues;
    }
}

package battleships.game;

import battleships.game.grid.Direction;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Utilities {

    private Utilities() {
    }

    public static String convertCoordinatesToAddress(int x, int y) {
        return x + Integer.toString(y);
    }

    public static int readCoordinateFromAddress(String address, String axe) {
        char coordinate = address.charAt(axe.equals("x") ? 0 : 1);
        return Character.getNumericValue(coordinate);
    }

    public static String getNeighbourAddress(String address, Direction direction) {
        return getNeighbourAddress(address, direction, 1);
    }

    public static String getNeighbourAddress(String address, Direction direction, int moveLength) {
        int x = readCoordinateFromAddress(address, "x")
                + moveLength * direction.getHorizontalMovement();
        int y = readCoordinateFromAddress(address, "y")
                + moveLength * direction.getVerticalMovement();

        return convertCoordinatesToAddress(x, y);
    }

    public static int generateRandomCoordinate(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    public static String generateRandomAddress (int size) {
        int x = generateRandomCoordinate(size);
        int y = generateRandomCoordinate(size);
        return x + Integer.toString(y);
    }

    public static String generateParityAddress(int size, int parityLevel){
        int x = generateRandomCoordinate(size);
        int y;
        int par_mod = x%parityLevel;
        if(par_mod==0){
            do{
                y = generateRandomCoordinate(size);
            }while(y%parityLevel!=0);
        }else{
            do{
                y = generateRandomCoordinate(size);
            }while((y-par_mod)%parityLevel != 0);
        }
        return x + Integer.toString(y);
    }

    public static Queue<String> mergeQueues(Queue<String> q1, Queue<String> q2) {
        Queue<String> mergedQueues = new LinkedList<>();

        while (!q1.isEmpty()){
            mergedQueues.add(q1.poll());
        }
        while (!q2.isEmpty()){
            mergedQueues.add(q2.poll());
        }

        return mergedQueues;
    }
}

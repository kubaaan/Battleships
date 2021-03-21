package game;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Utilities {

    private Utilities() {
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

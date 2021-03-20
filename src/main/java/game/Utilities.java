package game;

import java.util.Random;

public class Utilities {

    public static int generateRandomCoordinate(int size){
        Random random = new Random();
        return random.nextInt(size);
    }
}

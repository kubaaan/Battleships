package game;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    public static final int SIZE = 10;
    public static final Map<Integer, String> PLAYER_MARKERS
            = (Map.of(0, "X",
            1, "@",
            2, "$"));

    private Map<String,Field> fields;

    public Grid() {
        this.fields = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields.put(i + Integer.toString(j), new Field());
            }
        }
    }

    public int checkFieldShipOwner(String coordinates){
       return fields.get(coordinates).shipOwner;
    }

    public boolean checkIfFieldIsRevealed(String coordinates){
        return fields.get(coordinates).revealed;
    }

    public void markGuessedField(String coordinates){
        fields.get(coordinates).revealed = true;
    }

    public boolean deployShip(int x, int y, int player) {
        Field currentField = fields.get(x + Integer.toString(y));
        if (currentField.shipOwner == 0) {
            currentField.shipOwner = player;
            return true;
        }
        return false;
    }

    protected void printGrid() {

        Runnable printXAxe = () -> {
            System.out.print("   ");
            for (int i = 0; i < SIZE; i++) {
                System.out.print(i);
            }
            System.out.println();
        };

        printXAxe.run();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < SIZE; j++) {
                Field currentField = fields.get(j + Integer.toString(i));
                System.out.print(currentField.revealed || (currentField.shipOwner == 1)
                        ? PLAYER_MARKERS.get(currentField.shipOwner) : " ");
            }
            System.out.println("| " + i);
        }

        printXAxe.run();
    }

    protected void printGrid(int playerShips, int computerShips){
        printGrid();
        System.out.println("\nYour ships: " + playerShips + " | Computer ships: " + computerShips + "\n");
    }

    class Field {
        private int shipOwner;
        private boolean revealed;

        Field() {
            this.shipOwner = 0;
            this.revealed = false;
        }
    }
}
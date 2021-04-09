package game.grid;

import java.util.Map;

public class GridPrinter {

    public static final Map<FieldStatus, String> PLAYER_MARKERS
            = (Map.of(FieldStatus.HIT, "X",
            FieldStatus.OCCUPIED, "@",
            FieldStatus.MISSED, "O",
            FieldStatus.EMPTY, " "));

    public static void printGrid(Map<String, Grid.Field> fields, boolean mapIsRevealed) {

        Runnable printXAxe = () -> {
            System.out.print("   ");
            for (int i = 0; i < Grid.SIZE; i++) {
                System.out.print(i);
            }
            System.out.println();
        };

        printXAxe.run();

        for (int i = 0; i < Grid.SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < Grid.SIZE; j++) {
                Grid.Field currentField = fields.get(j + Integer.toString(i));
                if (mapIsRevealed) {
                    System.out.print(PLAYER_MARKERS.get(currentField.getStatus()));
                } else {
                    switch (currentField.getStatus()) {
                        case HIT:
                        case MISSED:
                            System.out.print(PLAYER_MARKERS.get(currentField.getStatus()));
                            break;
                        default:
                            System.out.print(PLAYER_MARKERS.get(FieldStatus.EMPTY));
                    }
                }
            }
            System.out.println("| " + i);
        }
        printXAxe.run();
    }

}

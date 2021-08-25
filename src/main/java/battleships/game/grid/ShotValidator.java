package battleships.game.grid;


public class ShotValidator {

    private ShotValidator() {
    }

    public static FieldStatus evaluateShot(Grid.Field targetedField) {
        FieldStatus status = targetedField.getStatus();
        targetedField.reveal();
        return status;
    }
}
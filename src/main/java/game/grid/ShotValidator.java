package game.grid;

public class ShotValidator {

    private ShotValidator() {
    }

    protected static FieldStatus evaluateShot(Grid.Field targetedField) {
        FieldStatus status = targetedField.getStatus();
        targetedField.reveal();
        return status;
    }
}
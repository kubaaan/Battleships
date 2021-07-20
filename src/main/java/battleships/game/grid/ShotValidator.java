package battleships.game.grid;


public class ShotValidator {

    private ShotValidator() {
    }

    protected static battleships.game.grid.FieldStatus evaluateShot(Grid.Field targetedField) {
        FieldStatus status = targetedField.getStatus();
        targetedField.reveal();
        return status;
    }
}
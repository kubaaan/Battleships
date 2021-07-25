package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.model.GuessResponse;

import java.util.LinkedList;
import java.util.Queue;

public class HuntTargetAlgorithm implements Algorithm {
    private boolean targetMode;
    private Queue<Integer> targets = new LinkedList<>();

    public HuntTargetAlgorithm() {
        this.targetMode = false;
    }

    @Override
    public GuessResponse guess(Grid grid) {

        int address = generateAddress();
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                 return new GuessResponse(FieldStatus.MISSED, address);
            case OCCUPIED:
                //ship hit
                addSurroundingsToTargetList(grid, address);
                this.targetMode = true;
                return new GuessResponse(FieldStatus.HIT, address);
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }

    private int generateAddress() {

        if (this.targetMode) {
            if (targets.peek() != null) {
                return targets.poll();
            } else {
                this.targetMode = false;
                return generateAddress();
            }
        } else {
            return Utilities.generateRandomAddress(Grid.SIZE);
        }
    }

    private void addSurroundingsToTargetList(Grid grid, int address) {
        Queue<Integer> newTargets = grid.getValidSurroundingTargets(address);
        targets = Utilities.mergeQueues(targets, newTargets);
    }
}

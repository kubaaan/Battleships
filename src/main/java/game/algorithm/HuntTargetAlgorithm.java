package game.algorithm;

import game.Utilities;
import game.grid.FieldStatus;
import game.grid.Grid;

import java.util.LinkedList;
import java.util.Queue;

public class HuntTargetAlgorithm implements Algorithm{
    private boolean targetMode;
    private Queue<String> targets = new LinkedList<>();

    public HuntTargetAlgorithm() {
        this.targetMode = false;
    }

    @Override
    public boolean guess(Grid grid) {

        String address = generateAddress();
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                 return false;
            case OCCUPIED:
                //ship hit
                addSurroundingsToTargetList(grid, address);
                this.targetMode = true;
                return true;
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }

    private String generateAddress() {

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

    private void addSurroundingsToTargetList(Grid grid, String address) {
        Queue<String> newTargets = grid.getValidSurroundingTargets(address);
        targets = Utilities.mergeQueues(targets, newTargets);
    }
}

package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.game.grid.ShotValidator;
import battleships.model.Guess;

import java.util.LinkedList;
import java.util.Queue;

public class HuntTargetAlgorithm implements Algorithm {
    private boolean targetMode;
    private Queue<Integer> targets = new LinkedList<>();

    public HuntTargetAlgorithm() {
        this.targetMode = false;
    }

    @Override
    public Guess guess(Grid grid) {

        int address = generateAddress();
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                 return new Guess(FieldStatus.MISSED, address);
            case OCCUPIED:
                //ship hit
                addSurroundingsToTargetList(address, grid);
                this.targetMode = true;
                return new Guess(FieldStatus.HIT, address);
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }

    public int generateAddress() {

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

    @Override
    public void addOccupiedAddress(int address, Grid grid) {
        addSurroundingsToTargetList(address, grid);
        this.targetMode = true;
    }

    private void addSurroundingsToTargetList( int address, Grid grid) {
        Queue<Integer> newTargets = grid.getValidSurroundingTargets(address);
        targets = Utilities.mergeQueues(targets, newTargets);
    }
}

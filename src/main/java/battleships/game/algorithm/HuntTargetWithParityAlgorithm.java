package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.model.Guess;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class HuntTargetWithParityAlgorithm implements Algorithm {
    private boolean targetMode;
    private Queue<Integer> targets = new LinkedList<>();
    private static int parityLevel=2;

    public HuntTargetWithParityAlgorithm() {
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
                grid.getShipsLocations().remove(address);
                parityLevel = changeParityLevel(grid.getShipsLocations());
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
            return Utilities.generateParityAddress(Grid.SIZE, parityLevel);
        }
    }
    private int changeParityLevel(Map<Integer, Integer> shipsLocations){
        int par_lvl = Integer.MAX_VALUE;
        for(int value : shipsLocations.values()){
            if(value<par_lvl){
                par_lvl = value;
            }
        }
        return  par_lvl;
    }

    @Override
    public void addOccupiedAddress(int address, Grid grid) {
        addSurroundingsToTargetList(address, grid);
        this.targetMode = true;
    }

    private void addSurroundingsToTargetList(int address, Grid grid) {
        Queue<Integer> newTargets = grid.getValidSurroundingTargets(address);
        targets = Utilities.mergeQueues(targets, newTargets);
    }
}

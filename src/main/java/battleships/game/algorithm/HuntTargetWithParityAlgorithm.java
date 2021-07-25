package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.model.GuessResponse;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class HuntTargetWithParityAlgorithm implements Algorithm {private boolean targetMode;
    private Queue<Integer> targets = new LinkedList<>();
    private static int parityLevel=2;

    public HuntTargetWithParityAlgorithm() {
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
                grid.getShipsLocations().remove(address);
                parityLevel = changeParityLevel(grid.getShipsLocations());
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
    private void addSurroundingsToTargetList(Grid grid, int address) {
        Queue<Integer> newTargets = grid.getValidSurroundingTargets(address);
        targets = Utilities.mergeQueues(targets, newTargets);
    }
}

package battleships.game.player;

import battleships.game.grid.*;


public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    public HumanPlayer() {
        super("Human");
    }




    @Override
    public void playTurn(Player rival) {
        System.out.println(name.toUpperCase() + "'S TURN");
        int address = 0;
        guess(address, rival);
    }

    @Override
    public void guess(int address, Player rival) {

        FieldStatus status = rival.grid.guess(address);
        switch (status) {
            case EMPTY:
                //missed shot
                break;
            case OCCUPIED:
                //ship hit
                rival.shipPoints--;
                break;
            default:
                //field already revealed (status MISSED or HIT)
                System.out.println("Field was already chosen. You loose your turn.");
        }
    }
}





package battleships.game.player;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    public HumanPlayer() {
        super("Human");
    }

    @Override
    public int guess(Player rival) {
        return -1;
    }
}





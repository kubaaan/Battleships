package battleships;

import battleships.game.Battleships;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BattleshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipsApplication.class, args);
		//Battleships newGame = new Battleships();
		battleships.game.Battleships newGame = Battleships.getBattleships("simulation");
		newGame.runGame();
	}

}

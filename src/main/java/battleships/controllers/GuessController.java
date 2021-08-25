package battleships.controllers;

import battleships.model.Guess;
import battleships.services.GuessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GuessController {

    private final GuessService guessService;

    public GuessController(GuessService guessService) {
        this.guessService = guessService;
    }

    @GetMapping("/guess")
    public @ResponseBody
    Guess checkGuess (@RequestParam int address){
        return guessService.evaluateGuess(address);
    }

    @GetMapping("/endgame")
    public @ResponseBody String sendEndGameResults(){
        return "Game has ended";
    }


}

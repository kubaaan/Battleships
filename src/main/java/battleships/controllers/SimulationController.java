package battleships.controllers;

import battleships.game.Battleships;
import battleships.model.Guess;
import battleships.model.TurnResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SimulationController {

    @GetMapping("/simulation")
    public String startSimulation(Model model){
        Battleships.startGame("simulation");

        List<Integer> fieldIdList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            fieldIdList.add(i);
        }

        model.addAttribute("idList",fieldIdList);
        model.addAttribute("scriptFile","simulation.js");

        return "game";
    }

    @GetMapping("/simulate")
    public @ResponseBody
    ArrayList<TurnResponse> sendSimulationResult(){
        Battleships.startGame("simulation");
        return Battleships.getBattleships().simulate();
    }
}

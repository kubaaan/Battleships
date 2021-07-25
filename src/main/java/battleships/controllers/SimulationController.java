package battleships.controllers;

import battleships.game.Battleships;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SimulationController {

    @GetMapping("/simulation")
    public String startSimulation(Model model){
        Battleships.startGame("playerVsCPU");

        List<Integer> fieldIdList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            fieldIdList.add(i);
        }

        model.addAttribute("idList",fieldIdList);

        return "game";
    }
}

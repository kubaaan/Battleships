package battleships.controllers;

import battleships.game.Battleships;
import battleships.game.grid.FieldStatus;
import battleships.model.GuessResponse;
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
        Battleships.startGame("playerVsCPU");

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
    ArrayList<GuessResponse> sendSimulationResult(){
        ArrayList<GuessResponse> array= new ArrayList<>();
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 1));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 15));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 2));
        array.add(new GuessResponse(FieldStatus.EMPTY, 24));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 35));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 11));
        array.add(new GuessResponse(FieldStatus.EMPTY, 10));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 13));
        array.add(new GuessResponse(FieldStatus.OCCUPIED, 11,true));


        return array;
    }
}

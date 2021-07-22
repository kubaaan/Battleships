package battleships.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimulationController {

    @GetMapping("/simulation")
    public String startSimulation(){
        return "simulation";
    }
}

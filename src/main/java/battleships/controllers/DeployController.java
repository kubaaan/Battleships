package battleships.controllers;


import battleships.game.Battleships;
import battleships.game.grid.ShipType;
import battleships.model.DeployRequest;
import battleships.model.DeployResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class DeployController {

    @GetMapping("/deploy")
    public @ResponseBody
    DeployResponse getShipsToDeploy (){

        Map<ShipType, Integer> list = Battleships.getShipsList();
        return new DeployResponse(list);
    }

    @PostMapping("/deploy")
    public @ResponseBody void deployShip(@RequestBody DeployRequest request){
        Battleships.getBattleships().deployShip(request);
    }
}

package battleships.controllers;


import battleships.model.DeployRequest;
import battleships.model.DeployResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeployController {

    @GetMapping("/deploy")
    public @ResponseBody
    DeployResponse getShipsToDeploy (){

        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        return new DeployResponse(list);
    }

    @PostMapping("/deploy")
    public @ResponseBody void deployShip(@RequestBody DeployRequest request){
        System.out.println(request.getAddress() + ", " + request.getDirection());
    }
}

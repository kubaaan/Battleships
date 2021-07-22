package battleships.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    @GetMapping("/game")
    public String startGame(Model model){

        List<Integer> fieldIdList = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            fieldIdList.add(i);
        }

        model.addAttribute("idList",fieldIdList);

        return "game";
    }
}

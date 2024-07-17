package card1.card.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    @GetMapping("/api-test")
    public String CardListPageController(){
        return "api-test";
    }

    @GetMapping("/card-main")
    public String CardMainPageController(){return "card-main";}
}

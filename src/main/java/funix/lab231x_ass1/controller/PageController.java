package funix.lab231x_ass1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
//    @GetMapping("/Home")
//    public String home() {
//        return "Home";
//    }


//    @GetMapping("/makequiz")
//    public String makeQuiz() {
//        return "makequiz";
//    }

    @GetMapping("/managequiz")
    public String manageQuiz() {
        return "managequiz";
    }
    @GetMapping("/takequiz")
    public String takeQuiz() {
        return "takequiz";
    }

}

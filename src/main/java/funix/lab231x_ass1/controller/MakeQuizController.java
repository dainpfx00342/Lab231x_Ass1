package funix.lab231x_ass1.controller;

import funix.lab231x_ass1.constants.Url;
import funix.lab231x_ass1.respository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MakeQuizController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(Url.MAKEQ)
    public String makeQuiz() {
        return "makequiz";
    }
}

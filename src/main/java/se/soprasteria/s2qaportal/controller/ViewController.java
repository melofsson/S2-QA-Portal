package se.soprasteria.s2qaportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.soprasteria.s2qaportal.repository.TestRepository;

@Controller
public class ViewController {


    private TestRepository testRepository;

    public ViewController (TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({ "/index", "/" })
    public String getMessage(Model model) {
        model.addAttribute("tests", testRepository.findAll());
        return "index";
    }
}
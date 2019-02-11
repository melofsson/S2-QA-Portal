package se.soprasteria.s2qaportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.soprasteria.s2qaportal.model.Test;
import se.soprasteria.s2qaportal.repository.TestRepository;

@Controller
public class TestResultController {

    private TestRepository testRepository;

    public TestResultController (TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping(value = {"/", "/index"})
    public String getMessage(Model model) {
        model.addAttribute("tests", testRepository.findAll());
        return "index";
    }
}

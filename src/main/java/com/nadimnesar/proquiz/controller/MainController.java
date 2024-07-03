package com.nadimnesar.proquiz.controller;

import com.nadimnesar.proquiz.model.Question;
import com.nadimnesar.proquiz.model.QuestionForm;
import com.nadimnesar.proquiz.service.GeminiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    GeminiService geminiService;

    public MainController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/quiz")
    public String generateQuiz(@RequestParam String topic, Model model) {
        QuestionForm questionForm = geminiService.createQuiz(topic);
        model.addAttribute("questionForm", questionForm);
        return "/quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz() {
        return "redirect:/";
    }
}
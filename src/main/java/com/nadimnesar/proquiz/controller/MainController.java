package com.nadimnesar.proquiz.controller;

import com.nadimnesar.proquiz.model.QuestionFormDto;
import com.nadimnesar.proquiz.service.GeminiService;
import com.nadimnesar.proquiz.service.GeneralService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final GeneralService generalService;
    private final GeminiService geminiService;
    private boolean submitStatus;

    public MainController(GeminiService geminiService, GeneralService generalService) {
        this.geminiService = geminiService;
        this.generalService = generalService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/quiz")
    public String generateQuiz(@RequestParam String topic, Model model) {
        QuestionFormDto questionForm = generalService.modifyQuestion(geminiService.createQuiz(topic));
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("topic", topic);
        submitStatus = false;
        return "quiz";
    }

    @PostMapping("/result")
    public String submitQuiz(@ModelAttribute QuestionFormDto questionForm, Model model) {
        if (submitStatus) return "redirect:/";
        submitStatus = true;
        int result = generalService.calculateResult(questionForm);
        model.addAttribute("result", result);
        return "result";
    }
}
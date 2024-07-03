package com.nadimnesar.proquiz.service;

import com.nadimnesar.proquiz.model.Question;
import com.nadimnesar.proquiz.model.QuestionDto;
import com.nadimnesar.proquiz.model.QuestionForm;
import com.nadimnesar.proquiz.model.QuestionFormDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralService {
    public QuestionFormDto modifyQuestion(QuestionForm questionForm) {
        List<Question> questions = questionForm.getQuestionList();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        QuestionFormDto questionFormDto = new QuestionFormDto();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestion(question.getQuestion());
            questionDto.setAnswer(question.getAnswer());
            questionDto.setOptionA(question.getOptionA());
            questionDto.setOptionB(question.getOptionB());
            questionDto.setOptionC(question.getOptionC());
            questionDto.setOptionD(question.getOptionD());
            questionDtoList.add(questionDto);
        }
        questionFormDto.setQuestionList(questionDtoList);
        return questionFormDto;
    }

    public int calculateResult(QuestionFormDto questionForm) {
        int result = 0;
        List<QuestionDto> questionDtoList = questionForm.getQuestionList();
        for (QuestionDto question : questionDtoList) {
            if(question.getAnswer().equals(question.getChosen())) result++;
        }
        return result;
    }
}
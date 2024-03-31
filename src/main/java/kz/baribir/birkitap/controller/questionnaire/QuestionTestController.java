package kz.baribir.birkitap.controller.questionnaire;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.questionnaire.entity.Answer;
import kz.baribir.birkitap.model.questionnaire.entity.MBTIQuestion;
import kz.baribir.birkitap.model.questionnaire.entity.Question;
import kz.baribir.birkitap.model.questionnaire.entity.QuestionTest;
import kz.baribir.birkitap.service.questionnaire.AnswerService;
import kz.baribir.birkitap.service.questionnaire.QuestionTestService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.ParamUtil;
import kz.baribir.birkitap.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class QuestionTestController {

    @Autowired
    private QuestionTestService questionTestService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping("/mbti")
    @ResponseBody
    public Response getMbti(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            QuestionTest questionTest = questionTestService.get("mbti");

            return Response.create_simple_success(questionTest);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String title = ParamUtil.get_string(params, "title", false);
            List<String> questions = (List<String>) params.get("questions");
            if (questions == null || questions.isEmpty()) {
                throw new RuntimeException("question is empty");
            }
            String type = ParamUtil.get_string(params, "type", false);
            List<Question> questionList = new ArrayList<>();

            for (String question : questions) {
                Question q = null;
                if ("mbti".equals(type)) {
                    q = new MBTIQuestion();
                    q.setQuestion(question);
                    q.setId(RandomUtil.random_uuid());
                } else {
                    throw new RuntimeException("not satisfies type");
                }
                questionList.add(q);

            }
            QuestionTest questionTest = new QuestionTest();
            questionTest.setQuestions(questionList);
            questionTest.setId(type);
            questionTest.setType(type);
            questionTest.setVisible(1);
            questionTest.setTitle(title);
            questionTestService.create(questionTest);

            return Response.create_simple_success(questionTest);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/answer")
    @ResponseBody
    public Response answer(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            Answer answer = new Answer();
            String questionTestId = ParamUtil.get_string(params, "questionTestId", false);
            QuestionTest questionTest = questionTestService.get(questionTestId);

            List<Map<String, Object>> answers = (List<Map<String, Object>>) params.get("answers");
            answer.setAnswers(answers);
            answer.setQuestion(questionTest);
            answer.setCreatetime(new Date().getTime());

            answerService.create(answer);

            return Response.create_simple_success(answer);
        } catch (Exception e) {
            return new Response(-1, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/get")
    @ResponseBody
    public Response get(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            QuestionTest questionTest = questionTestService.get(id);

            return Response.create_simple_success(questionTest);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Response update(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String id = ParamUtil.get_string(params, "id", false);

            QuestionTest questionTest = questionTestService.get(id);

            return Response.create_simple_success(questionTest);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }



}

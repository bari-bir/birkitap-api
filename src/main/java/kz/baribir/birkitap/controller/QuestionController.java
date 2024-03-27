package kz.baribir.birkitap.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.baribir.birkitap.bean.TokenInfo;
import kz.baribir.birkitap.model.common.Response;
import kz.baribir.birkitap.model.common.entity.Question;
import kz.baribir.birkitap.service.QuestionService;
import kz.baribir.birkitap.util.ExceptionUtil;
import kz.baribir.birkitap.util.JWTUtils;
import kz.baribir.birkitap.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping("/create")
    @ResponseBody
    public Response create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {
            String question = ParamUtil.get_string(params, "question", false);
            String type = ParamUtil.get_string(params, "type", false);
            TokenInfo tokenInfo = jwtUtils.getTokenInfo(request);
            List<String> answers = (List<String>) params.get("answers");

            Question question1 = new Question();
            question1.setQuestion(question);
            question1.setType(type);
            question1.setAuthor(tokenInfo.getUuid());
            question1.setAnswers(answers);

            questionService.create(question1);

            return Response.create_simple_success(question1);
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        try {

            return Response.create_simple_success(questionService.list(params));
        } catch (Exception e) {
            return new Response(-2, e.getMessage(), ExceptionUtil.getStackTrace(e));
        }
    }
}

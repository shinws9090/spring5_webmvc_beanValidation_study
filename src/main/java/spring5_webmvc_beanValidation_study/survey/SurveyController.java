package spring5_webmvc_beanValidation_study.survey;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/survey")
public class SurveyController {

//	@GetMapping
//	public String form(Model model) {
//		List<Question> questions = createQuestion();
//		model.addAttribute("questions",questions);
//		return "survey/surveyForm";
//	}
	@GetMapping
	public ModelAndView form() {
		List<Question> questions = createQuestion();
		ModelAndView mav = new ModelAndView();
		mav.addObject("questions", questions);
		mav.setViewName("survey/surveyForm");
		return mav;
	}

	@PostMapping
	public String submit(@ModelAttribute("ansData") AnswewdData data) {
		return "survey/submitted";
	}

	private List<Question> createQuestion() {
		Question q1 = new Question("당신의 역할은?", Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이쓴느 개발도구는?", Arrays.asList("이클립스", "인텔리j", "서브라임"));
		Question q3 = new Question("하고싶은말은??");
		return Arrays.asList(q1, q2, q3);
	}

}

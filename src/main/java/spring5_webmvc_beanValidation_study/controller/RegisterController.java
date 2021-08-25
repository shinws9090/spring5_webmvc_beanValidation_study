package spring5_webmvc_beanValidation_study.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring5_webmvc_beanValidation_study.spring.DuplicateMemberException;
import spring5_webmvc_beanValidation_study.spring.MemberRegisterService;
import spring5_webmvc_beanValidation_study.spring.RegisterRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private MemberRegisterService service;

	@RequestMapping("/step1")
	public String handleStep1() {
		return "register/step1";
	}

	@PostMapping("/step2")
	public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, /* Model model, */
			RegisterRequest regReq) {
		if (!agree) {
			return "redirect:/register/step1";
		}
//		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/step2")
	public String hendleStep2Get() {
		return "redirect:/register/step1";
	}

	@PostMapping("/step3")
	public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {
		System.out.println(regReq);
		if (errors.hasErrors())
			return "register/step2";
		if (!regReq.isPasswordEqualToConfirmPassword()) {
			errors.rejectValue("confirmPassword", "nomatch");
			return "register/step2";
		}

		try {
			service.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}
}

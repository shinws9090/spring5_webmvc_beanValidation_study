package spring5_webmvc_beanValidation_study.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring5_webmvc_beanValidation_study.spring.AuthInfo;
import spring5_webmvc_beanValidation_study.spring.ChangePasswordService;
import spring5_webmvc_beanValidation_study.spring.ChangePwdCommand;
import spring5_webmvc_beanValidation_study.spring.ChangePwdCommandValidator;
import spring5_webmvc_beanValidation_study.spring.WrongIdPasswordException;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {
	
	@Autowired
	private ChangePasswordService service;
	
	@GetMapping
	public String form(@ModelAttribute("command") ChangePwdCommand changePwdCommand ) {
		return "edit/changePwdForm";
	}
	@PostMapping
	public String submit(@ModelAttribute("command") ChangePwdCommand changePwdCommand ,Errors errors, HttpSession session) {
		new ChangePwdCommandValidator();
		if(errors.hasErrors()) {
			return "edit/changePwdForm";
		}
		AuthInfo authinfo = (AuthInfo) session.getAttribute("authInfo");
		try {
			service.changePassword(authinfo.getEmail(), changePwdCommand.getCurrentPassword(), changePwdCommand.getNewPassword());
			return "edit/changePwd";
		}catch(WrongIdPasswordException e) {
			errors.rejectValue("currentPassword", "notMatching");
			return "edit/changePwdForm";
		}
	}
	
}

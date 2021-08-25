package spring5_webmvc_beanValidation_study.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring5_webmvc_beanValidation_study.spring.DuplicateMemberException;
import spring5_webmvc_beanValidation_study.spring.ErrorResponce;
import spring5_webmvc_beanValidation_study.spring.Member;
import spring5_webmvc_beanValidation_study.spring.MemberDao;
import spring5_webmvc_beanValidation_study.spring.MemberNotFoundException;
import spring5_webmvc_beanValidation_study.spring.MemberRegisterService;
import spring5_webmvc_beanValidation_study.spring.RegisterRequest;
import spring5_webmvc_beanValidation_study.spring.RegisterRequestValidator;

@RestController
public class RestMemberController {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberRegisterService service;

	@GetMapping("api/members")
	public List<Member> members() {
		return memberDao.selectAll();
	}

	@GetMapping("api/members/{id}")
	public ResponseEntity<Object> member(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Member member = memberDao.selectById(id);
		if (member == null) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponce("no member")); //json으로 에러표시
			throw new MemberNotFoundException(); //왜되는거지
		}
		return ResponseEntity.status(HttpStatus.OK).body(member);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)  //에러 헨들러
	public ResponseEntity<ErrorResponce> handleNoData(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponce("no member")); 
	}

	@PostMapping("/api/members")
	public ResponseEntity<Object> newMember(@Valid @RequestBody RegisterRequest regReq, Errors errors, HttpServletResponse response)
			throws IOException {
		try {
//			new RegisterRequestValidator().validate(regReq, errors);
			if (errors.hasErrors()) {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST); //일반
//				return ResponseEntity.badRequest().build(); //json으로 에러표시
				String errorCode = 
						errors.getAllErrors()
						.stream()
						.map(error->error.getCodes()[0])
						.collect(Collectors.joining(","));
				return ResponseEntity.badRequest().body(new ErrorResponce("errorCode = "+ errorCode));
			}
			Long newMemberId = service.regist(regReq);
//			response.setHeader("Location", "/api/members/" + newMemberId);
//			response.setStatus(HttpServletResponse.SC_CREATED);
			URI uri = URI.create("/api/members/" + newMemberId);
			return ResponseEntity.created(uri).build(); 
		} catch (DuplicateMemberException e) {
//			response.sendError(HttpServletResponse.SC_CONFLICT);
			return ResponseEntity.status(HttpStatus.CONFLICT).build(); //json으로 에러표시
		}

	}
}

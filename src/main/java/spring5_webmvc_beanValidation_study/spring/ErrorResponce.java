package spring5_webmvc_beanValidation_study.spring;

public class ErrorResponce {
	private String message;

	public ErrorResponce(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}

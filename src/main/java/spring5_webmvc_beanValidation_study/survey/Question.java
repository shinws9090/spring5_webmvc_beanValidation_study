package spring5_webmvc_beanValidation_study.survey;

import java.util.Collections;
import java.util.List;

public class Question {

	private String title;
	private List<String> options;

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}

	public Question(String title, List<String> options) {
		super();
		this.title = title;
		this.options = options;
	}

	public Question(String title) {
		this(title, Collections.<String>emptyList());
	}

	public boolean isChoice() {
		return options != null && !options.isEmpty();
	}

}

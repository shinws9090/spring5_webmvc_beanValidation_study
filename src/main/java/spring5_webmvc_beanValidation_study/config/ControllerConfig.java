package spring5_webmvc_beanValidation_study.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"spring5_webmvc_beanValidation_study.controller",
		"spring5_webmvc_beanValidation_study.survey",
		"spring5_webmvc_beanValidation_study.common"
		})
public class ControllerConfig {

}

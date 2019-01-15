package org.oxerr.spring.security.otp.samples.helloworld;

import org.oxerr.spring.security.otp.core.OTPAuthenticationService;
import org.oxerr.spring.security.otp.samples.helloworld.service.impl.OTPAuthenticationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Application {

	@Bean
	public OTPAuthenticationService otpAuthenticationService() {
		return new OTPAuthenticationServiceImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

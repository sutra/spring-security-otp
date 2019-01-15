package org.oxerr.spring.security.otp.samples.helloworld.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;

import org.oxerr.spring.security.otp.samples.helloworld.service.impl.OTPAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RolesAllowed("ROLE_USER")
public class GenerateOTPController {

	private final OTPAuthenticationServiceImpl otpAuthenticationServiceImpl;

	@Autowired
	public GenerateOTPController(OTPAuthenticationServiceImpl otpAuthenticationServiceImpl) {
		this.otpAuthenticationServiceImpl = otpAuthenticationServiceImpl;
	}

	@RequestMapping(method = GET, path = "/generate-otp")
	public ModelAndView getOtp(final Authentication authentication) throws IOException {
		String otp = this.otpAuthenticationServiceImpl.generateOneTimePassword(authentication);
		return new ModelAndView("generate-otp", "otp", otp);
	}

}

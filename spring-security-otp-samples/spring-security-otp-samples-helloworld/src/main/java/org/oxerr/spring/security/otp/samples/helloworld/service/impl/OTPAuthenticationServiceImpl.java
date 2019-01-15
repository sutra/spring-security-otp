package org.oxerr.spring.security.otp.samples.helloworld.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.oxerr.spring.security.otp.core.OTPAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OTPAuthenticationServiceImpl implements OTPAuthenticationService {

	private final Map<String, Authentication> store = new HashMap<>();

	@Override
	public Authentication loadAuthenticationByOneTimePassword(String oneTimePassword) throws AuthenticationException {
		return store.get(oneTimePassword);
	}

	public String generateOneTimePassword(Authentication authentication) {
		String oneTimePassword = RandomStringUtils.randomAlphabetic(8);
		store.put(oneTimePassword, authentication);
		return oneTimePassword;
	}

}

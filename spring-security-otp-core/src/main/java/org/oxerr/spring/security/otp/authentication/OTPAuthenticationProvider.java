package org.oxerr.spring.security.otp.authentication;

import org.oxerr.spring.security.otp.core.OTPAuthenticationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

public class OTPAuthenticationProvider implements AuthenticationProvider,
		InitializingBean {

	private final OTPAuthenticationService otpAuthenticationService;

	public OTPAuthenticationProvider(OTPAuthenticationService otpAuthenticationService) {
		this.otpAuthenticationService = otpAuthenticationService;
	}

	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		final OTPAuthenticationToken otpAuthenticationToken = (OTPAuthenticationToken) authentication;
		final String oneTimePassword = otpAuthenticationToken.getOneTimePassword();
		final Authentication auth = this.otpAuthenticationService.loadAuthenticationByOneTimePassword(oneTimePassword);
		return auth != null ? new OTPAuthenticationToken(auth) : null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OTPAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.otpAuthenticationService, "A otpAuthenticationService must be set.");
	}

}

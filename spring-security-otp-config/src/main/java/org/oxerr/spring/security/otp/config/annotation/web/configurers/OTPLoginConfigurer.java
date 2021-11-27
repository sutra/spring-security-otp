package org.oxerr.spring.security.otp.config.annotation.web.configurers;

import org.oxerr.spring.security.otp.authentication.OTPAuthenticationProvider;
import org.oxerr.spring.security.otp.core.OTPAuthenticationService;
import org.oxerr.spring.security.otp.web.authentication.OTPAuthenticationFilter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class OTPLoginConfigurer<H extends HttpSecurityBuilder<H>>
		extends
		AbstractAuthenticationFilterConfigurer<H, OTPLoginConfigurer<H>,
		OTPAuthenticationFilter> {

	private final OTPAuthenticationService otpAuthenticationService;

	public OTPLoginConfigurer(OTPAuthenticationService otpAuthenticationService) {
		super(new OTPAuthenticationFilter(), "/**");
		this.otpAuthenticationService = otpAuthenticationService;
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(
			String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl);
	}

	@Override
	public void init(H http) throws Exception {
		super.init(http);

		OTPAuthenticationProvider authenticationProvider = new OTPAuthenticationProvider(
				otpAuthenticationService);
		postProcess(authenticationProvider);
		http.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(H http) throws Exception {

		// Make sure the filter be registered in
		// org.springframework.security.config.annotation.web.builders.FilterComparator
		http.addFilterBefore(getAuthenticationFilter(), AnonymousAuthenticationFilter.class);

		super.configure(http);
	}

	public OTPLoginConfigurer<H> oneTimePasswordParameter(String oneTimePasswordParameter) {
		getAuthenticationFilter().setOneTimePasswordParameter(oneTimePasswordParameter);
		return this;
	}

}

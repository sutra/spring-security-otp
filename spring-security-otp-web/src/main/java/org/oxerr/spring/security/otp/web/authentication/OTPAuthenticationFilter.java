package org.oxerr.spring.security.otp.web.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oxerr.spring.security.otp.authentication.OTPAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class OTPAuthenticationFilter
		extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_ONE_TIME_PASSWORD_KEY = "otp";

	private String oneTimePasswordParameter = SPRING_SECURITY_ONE_TIME_PASSWORD_KEY;

	public OTPAuthenticationFilter() {
		super(new AntPathRequestMatcher("/**", ""));
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		final Authentication auth;
		return super.requiresAuthentication(request, response)
			&& ((auth = SecurityContextHolder.getContext().getAuthentication()) == null || !auth.isAuthenticated())
			&& obtainOneTimePassword(request) != null;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		final String oneTimePassword = obtainOneTimePassword(request);
		final OTPAuthenticationToken authRequest = new OTPAuthenticationToken(oneTimePassword);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		SecurityContextHolder.getContext().setAuthentication(authResult);

		getRememberMeServices().loginSuccess(request, response, authResult);

		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
		}

		chain.doFilter(request, response);
	}

	protected String obtainOneTimePassword(HttpServletRequest request) {
		return request.getParameter(oneTimePasswordParameter);
	}

	public void setOneTimePasswordParameter(String oneTimePasswordParameter) {
		Assert.hasText(oneTimePasswordParameter,
			"One-time password parameter must not be empty or null");
		this.oneTimePasswordParameter = oneTimePasswordParameter;
	}

}

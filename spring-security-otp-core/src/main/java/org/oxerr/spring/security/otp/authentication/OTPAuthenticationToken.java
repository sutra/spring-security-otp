package org.oxerr.spring.security.otp.authentication;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class OTPAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 2019011001L;

	private String oneTimePassword;

	private Authentication authentication;

	public OTPAuthenticationToken(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public OTPAuthenticationToken(Authentication authentication) {
		this.authentication = authentication;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	@Override
	public String getName() {
		return authentication.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authentication.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return authentication.getCredentials();
	}

	@Override
	public Object getDetails() {
		return authentication.getDetails();
	}

	@Override
	public Object getPrincipal() {
		return authentication.getPrincipal();
	}

	@Override
	public boolean isAuthenticated() {
		return authentication.isAuthenticated();
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authentication.setAuthenticated(isAuthenticated);
	}

}

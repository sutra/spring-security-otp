package org.oxerr.spring.security.otp.core;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface OTPAuthenticationService {

	Authentication loadAuthenticationByOneTimePassword(String oneTimePassword) throws AuthenticationException;

}

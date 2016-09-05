package com.rspective.interview.vouch.auth;


import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class VoucherAuthenticator implements Authenticator<BasicCredentials, User> {

	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if ("secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}
		return Optional.empty();
	}

}

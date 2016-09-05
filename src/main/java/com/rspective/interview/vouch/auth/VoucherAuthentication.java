package com.rspective.interview.vouch.auth;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class VoucherAuthentication implements Authenticator<BasicCredentials, User> {

	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if ("secret".equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername()));
		}
		return Optional.absent();
	}

}

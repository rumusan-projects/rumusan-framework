/*
 * Copyright 2017-2017 the original author or authors.
 */

package org.rumusanframework.security.oauth2.client.token.grant.password;

import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Sept 2017)
 *
 */
public class ClientPasswordResourceDetails extends ResourceOwnerPasswordResourceDetails {
	public ClientPasswordResourceDetails() {
		super();
	}

	@Override
	public boolean isClientOnly() {
		return true;
	}
}
package org.rumusanframework.security.oauth2.client.token.grant.password;

import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class ClientResourceOwnerPasswordResourceDetails extends ResourceOwnerPasswordResourceDetails {
    public ClientResourceOwnerPasswordResourceDetails() {
	super();
    }

    @Override
    public boolean isClientOnly() {
	return true;
    }
}
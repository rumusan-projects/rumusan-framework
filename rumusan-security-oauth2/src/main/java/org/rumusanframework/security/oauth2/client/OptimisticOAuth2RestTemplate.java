/*
 * Copyright 2017-2018 the original author or authors.
 */

package org.rumusanframework.security.oauth2.client;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author Harvan Irsyadi
 * @version 1.0.0
 * @since 1.0.0 (5 Sept 2017)
 */
public class OptimisticOAuth2RestTemplate extends OAuth2RestTemplate {

  private int retryError = 3;

  private int expirationRange = 5;

  private int nextExpiredSec = 2;

  private boolean isSleepOnExpirationRange = true;

  public OptimisticOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource) {
    super(resource);
  }

  public OptimisticOAuth2RestTemplate(OAuth2ProtectedResourceDetails resource,
      OAuth2ClientContext context) {
    super(resource, context);
  }

  @Override
  public OAuth2AccessToken getAccessToken() {
    OAuth2AccessToken accessToken = getAccessTokenInternal(retryError);

    printAccessTokenInfo(accessToken);

    return accessToken;
  }

  private OAuth2AccessToken getAccessTokenInternal(int retry) {
    OAuth2AccessToken accessToken = null;

    try {
      accessToken = getCheckedAccessToken(super.getAccessToken());
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error("Error Acquire or renew an access token.", e);
      }

      getOAuth2ClientContext().setAccessToken(null);

      if (retry >= 0) {
        int internalRetry = retry - 1;
        accessToken = getAccessTokenInternal(internalRetry);

        if (logger.isInfoEnabled()) {
          logger.info("Success Acquire or renew an access token in " + (retryError - internalRetry)
              + " attempt[s].");
        }
      } else {
        if (logger.isErrorEnabled()) {
          logger.error(
              "Failed to retry to Acquire or renew an access token for " + retryError
                  + " attempts.");
        }
      }
    }

    return accessToken;
  }

  private OAuth2AccessToken getCheckedAccessToken(OAuth2AccessToken currentAccessToken) {
    OAuth2AccessToken accessToken = currentAccessToken;
    int expiredIn = currentAccessToken.getExpiresIn();

    if (expiredIn <= expirationRange) {
      try {
        int seconds = expiredIn + nextExpiredSec;
        if (logger.isInfoEnabled()) {
          logger.info("Get next accessToken in : " + seconds + " seconds.");
        }

        if (isSleepOnExpirationRange) {
          Thread.sleep(seconds * 1000L);
        }
        accessToken = super.getAccessToken();
      } catch (InterruptedException e) {
        logger.error("Error while Thread.sleep.", e);

        Thread.currentThread().interrupt();
      }
    }
    return accessToken;
  }

  private void printAccessTokenInfo(OAuth2AccessToken accessToken) {
    if (logger.isDebugEnabled() && accessToken != null) {
      StringBuilder buff = new StringBuilder();

      buff.append("accessToken : " + accessToken.getValue());
      buff.append(", refreshToken : " + accessToken.getRefreshToken().getValue());
      buff.append(", expired in : " + accessToken.getExpiresIn() + " second[s].");

      logger.debug(buff.toString());
    }
  }
}
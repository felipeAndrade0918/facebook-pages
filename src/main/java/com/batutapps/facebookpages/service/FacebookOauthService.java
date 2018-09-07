package com.batutapps.facebookpages.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

@Service
public class FacebookOauthService {

	public static final Logger logger = LoggerFactory.getLogger(FacebookOauthService.class);
	
	@Value("${facebook.oauth.app.id}")
	private String appId;
	
	@Value("${facebook.oauth.app.secret}")
	private String appSecret;
	
	public Facebook setupFacebookClient() {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appId, appSecret);
		facebook.setOAuthPermissions("manage_pages");
		
		return facebook;
	}
	
	public String getCallbackUrl(String requestUrl) {
		StringBuilder callbackUrl = new StringBuilder(requestUrl);
		
        int index = callbackUrl.lastIndexOf("/");
        callbackUrl.replace(index, callbackUrl.length(), "").append("/callback");
        
        return callbackUrl.toString();
	}
	
	public void setAccessToken(Facebook facebook, String oauthCode) throws Exception {
        if (oauthCode != null) {
        	try {
        		AccessToken token = facebook.getOAuthAccessToken(oauthCode);
        		facebook.setOAuthAccessToken(token);
        	} catch (FacebookException e) {
        		logger.error("Error while setting the access token", e);
        	}
        }
	}
	
	public List<String> getPages(Facebook facebook) {
		if (facebook != null) {
			try {
				Reading reading = new Reading();
				reading.fields("name");
				
				ResponseList<Account> accounts = facebook.getAccounts(reading);
				
				return accounts.stream()
							   .map(Account::getName)
							   .collect(Collectors.toList());
			} catch (IllegalStateException e) {
				logger.error("User is not logged in", e);
			} catch (FacebookException e) {
				logger.error("An error occurred while fetching the user's pages", e);
			}
		}
		
		return null;
	}
	
}
package com.batutapps.facebookpages.service;

import java.util.List;

import facebook4j.Facebook;

public interface FacebookOauthService {

	Facebook setupFacebookClient();

	String getCallbackUrl(String requestUrl);

	void setAccessToken(Facebook facebook, String oauthCode) throws Exception;

	List<String> getPages(Facebook facebook);

}
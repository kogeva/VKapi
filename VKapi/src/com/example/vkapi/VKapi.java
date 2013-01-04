package com.example.vkapi;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.provider.SyncStateContract.Constants;

public class VKapi {
	private String email;
	private String password;
	private String toField;
	private String ip_hField;
	private String access_token;
	private httpRequester requester = new httpRequester();
	private String urlBody = "https://api.vk.com/method/"; 
	
	public VKapi(String email, String password)
	{
		this.email = email;
		this.password = password;
		this.authorization();
	}
	
	public void getValueForAuthorization()
	{
		String result = requester.executeGetRequest("https://oauth.vk.com/authorize?client_id=3141902&scope=audio&redirect_uri=http://oauth.vk.com/blank.html&display=touch&response_type=token");
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode domStrict = cleaner.clean(result);
    	TagNode hash[] = domStrict.getElementsByAttValue("name", "to", true, false);
    	TagNode ip_h[] = domStrict.getElementsByAttValue("name", "ip_h", true, false);
    	this.toField = hash[0].getAttributeByName("value");
    	this.ip_hField = ip_h[0].getAttributeByName("value");
	}
	
	public void authorization()
	{
		this.getValueForAuthorization();
		List<NameValuePair> params = new ArrayList<NameValuePair>(5);
		params.add(new BasicNameValuePair("_origin", "https://oauth.vk.com"));
		params.add(new BasicNameValuePair("email", this.email));
		params.add(new BasicNameValuePair("pass", this.password));
		params.add(new BasicNameValuePair("ip_h", this.ip_hField));
		params.add(new BasicNameValuePair("to", this.toField));
		String url = requester.executePostRequest("https://login.vk.com/?act=login&soft=1&utf8=1", params);
		Pattern p = Pattern.compile("[0-9_a-f]{30,100}+");
		Matcher m = p.matcher(url); 
		m.find();
		setAccess_token(m.group());
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public String searchAudio(String songName){
		String query = this.urlBody + 
						"audio.search?q="+
						URLEncoder.encode(songName)+"&access_token="+
						getAccess_token();
		System.out.println(query);
		return requester.executeGetRequest(query);
	}

}

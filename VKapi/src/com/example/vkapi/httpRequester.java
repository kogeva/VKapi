package com.example.vkapi;


import java.io.IOException;
import java.util.List;

import javax.xml.transform.URIResolver;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.DefaultedHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


public class httpRequester {
	public HttpClient client;
	
	public httpRequester()
	{
		this.client = new DefaultHttpClient();
	}
	
	public String executeGetRequest(String url)
	{
		final HttpGet request = new HttpGet(url);
				try {
					return EntityUtils.toString(client.execute(request).getEntity());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return null;
	}
	
	public String executePostRequest(String url, List<NameValuePair> list)
	{
		HttpPost request = new HttpPost(url);
		HttpContext context = new BasicHttpContext();
		try{
		request.setEntity(new UrlEncodedFormEntity(list));
		client.execute(request, context);
		HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		HttpUriRequest currentRequest = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
		return currentHost.toURI() + currentRequest.getURI().toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

}

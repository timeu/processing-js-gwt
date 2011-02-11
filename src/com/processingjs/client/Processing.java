package com.processingjs.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


public class Processing <I extends ProcessingInstance> extends Widget  {

	
	protected String url;
	protected I p_instance;
	protected boolean isLoaded = false;
	
	public Processing() {
		super();
		Element elem = createElement();
		setElement(createElement());
		elem.setId(DOM.createUniqueId());
	}
	
	
	
	protected native CanvasElement createElement() /*-{
    	return $doc.createElement("canvas");
	}-*/;
	
	
	public void loadFromUrl(String url,final Runnable onLoad) throws RequestException 
	{
		this.url = url;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setCallback(new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
			   load(response.getText(),onLoad);
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				
			}
		});
		
		builder.send();
	}
	
	public void load(ExternalTextResource code ,final Runnable onLoad) {
		try
		{
			code.getText(new ResourceCallback<TextResource>() {
				@Override
				public void onSuccess(TextResource resource) {
					load(resource.getText(),onLoad);
				}
				
				@Override
				public void onError(ResourceException e) {
				}
			});
		}
		catch (Exception e) {}
	}
	
	public void load(String code,final Runnable onLoad)
	{
		p_instance = init(code,getElement());
		isLoaded = true;
		if (onLoad != null)
			onLoad.run();
	}
	
	public I getInstance()
	{
		return p_instance;
	}
	
	public boolean isLoaded()
	{
		return isLoaded;
	}
	
	protected native I init(String programm,Element elem) /*-{
		
		instance = new $wnd.Processing(elem,programm);
		$wnd.Processing.addInstance(instance);
		return instance;
	}-*/;
}



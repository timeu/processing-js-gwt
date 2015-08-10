package com.github.timeu.gwtlibs.processingjsgwt.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


public class Processing <I extends ProcessingInstance> extends Widget  {

	interface ProcessingClientBundle extends ClientBundle {
		ProcessingClientBundle INSTANCE = GWT.create(ProcessingClientBundle.class);
		
		@Source("resources/processing.js")
		TextResource processingjs();
	}
	
	protected String url;
	protected I pInstance;
	protected boolean isLoaded = false;
	protected final Canvas canvas;
	
	public Processing() {
		injectScript();
		canvas = Canvas.createIfSupported();
		setElement(canvas.getElement());
	}

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
				throw new RuntimeException("Processing code from URL could not be loaded",exception);
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
					throw new RuntimeException("External Processing code could not be loaded",e);
				}
			});
		}
		catch (Exception e) {}
	}
	
	public void load(String code,final Runnable onLoad)
	{
		pInstance = init(code,getElement());
		isLoaded = true;
		if (onLoad != null)
			onLoad.run();
	}
	
	public I getInstance()
	{
		return pInstance;
	}
	
	public boolean isLoaded()
	{
		return isLoaded;
	}


	protected final native I init(String programm,Element elem) /*-{
		return  new $wnd.Processing(elem,programm);
	}-*/;
	
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	private void injectScript() {
		if (!isInjected()) {
			ScriptInjector.fromString(ProcessingClientBundle.INSTANCE.processingjs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}

	protected native final boolean isInjected() /*-{
		if (!(typeof $wnd.Processing === "undefined") && !(null===$wnd.Processing)) {
            return true;
        }
        return false;
	}-*/;
}



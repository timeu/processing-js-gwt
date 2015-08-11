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
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Processing wrapper widget around a ProcessingJS sketch
 *
 * @param <I> This must be an interface or class that extends/implements {@link ProcessingInstance} and is annotated with @JsType
 */
public class Processing <I extends ProcessingInstance> extends Widget  {

	interface ProcessingClientBundle extends ClientBundle {
		ProcessingClientBundle INSTANCE = GWT.create(ProcessingClientBundle.class);
		
		@Source("resources/processing.js")
		TextResource processingjs();
	}
	
	protected I pInstance;
	protected boolean isLoaded = false;
	protected final Canvas canvas;

    /**
     * Creates a Processing widget
     */
	public Processing() {
		injectScript();
		canvas = Canvas.createIfSupported();
		setElement(canvas.getElement());
	}

    /**
     * Loads the ProcessingJS sketch from a URL.
     *
     * @param url URL to the ProcessingJS sketch
     * @param onLoad optional callback that is executed when the sketch is loaded
     * @throws RequestException if the request to the URL throws an Exception
     */
	public void load(SafeUri url,final Runnable onLoad) throws RequestException
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.asString());
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

    /**
     * Loads a ProcessingJS sketch from an {@link ExternalTextResource}
     *
     * @param code the code of the ProcessingJS sketch
     * @param onLoad optional callback that is executed when the sketch is loaded
     */
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

    /**
     * Loads a ProcessingJS sketch from a String
     *
     * @param code the code of the ProcessingJS sketch
     * @param onLoad optional callback that is executed when the sketch is loaded
     */
	public void load(String code,final Runnable onLoad)
	{
		pInstance = init(code,getElement());
		isLoaded = true;
		if (onLoad != null)
			onLoad.run();
	}

    /**
     * Provides access to the (custom) ProcessingInstance that allows the user
     * to interface with the ProcessingJS sketch
     *
     * @return the actual {@link ProcessingInstance} instance
     */
	public I getInstance()
	{
		return pInstance;
	}

    /**
     * Can be used to check if the sketch is loaded
     *
     * @return true if the sketch is loaded
     */
	public boolean isLoaded()
	{
		return isLoaded;
	}


	protected final native I init(String programm,Element elem) /*-{
		return  new $wnd.Processing(elem,programm);
	}-*/;


    /**
     * Provides access to the Canvas widget
     *
     * @return the {@link Canvas} widget
     */
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



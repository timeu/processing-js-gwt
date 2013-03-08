package at.gmi.nordborglab.processingjs.client;

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
	protected I p_instance;
	protected boolean isLoaded = false;
	protected Canvas canvas;
	
	public Processing() {
		super();
		injectScript();
		canvas = createElement();
		setElement(canvas.getElement());
	}
	
	protected Canvas createElement() {
    	return canvas = Canvas.createIfSupported();
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
		return instance;
	}-*/;
	
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	private void injectScript() {
		if (!isInjected()) {
			ScriptInjector.fromString(ProcessingClientBundle.INSTANCE.processingjs().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}

	private native final boolean isInjected() /*-{
		if (!(typeof $wnd.Processing === "undefined") && !(null===$wnd.Processing)) {
            return true;
        }
        return false;
	}-*/;
}



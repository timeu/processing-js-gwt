package at.gmi.nordborglab.processingjs.client;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class ProcessingInstance extends JavaScriptObject{
	
	protected ProcessingInstance() {}
	
	public final native void setSize(int width,int height)  /*-{
		this.size(width,height);
	}-*/;
	
	public final native void draw() /*-{
		this.draw();
	}-*/;
	
}

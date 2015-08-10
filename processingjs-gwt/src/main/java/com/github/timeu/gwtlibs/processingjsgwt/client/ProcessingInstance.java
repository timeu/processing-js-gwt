package com.github.timeu.gwtlibs.processingjsgwt.client;

import com.google.gwt.core.client.js.JsType;


@JsType
public interface ProcessingInstance {

	void size(int width,int height);
	void draw();
}

package com.github.timeu.gwtlibs.processingjsgwt.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.RootPanel;
import jsinterop.annotations.JsType;


/**
 * Created by uemit.seren on 8/9/15.
 */

public class ProcessingTest extends GWTTestCase {

    @JsType(isNative = true)
    public interface MyProcessingInstance extends ProcessingInstance {
        String testMethod(String msg);
    }

    private final String code = "void setup()\n" +
            "{\n" +
            "  size(200,200);\n" +
            "  background(125);\n" +
            "  fill(255);\n" +
            "  noLoop();\n" +
            "  PFont fontA = loadFont(\"courier\");\n" +
            "  textFont(fontA, 14);  \n" +
            "}\n" +
            "\n" +
            "void draw(){  \n" +
            "  text(\"Hello Web!\",20,20);\n" +
            "  println(\"Hello ErrorLog!\");\n" +
            "}\n\n" +
            "String testMethod(msg) {\n" +
            "  console.log(msg);\n" +
            "  return msg;"+
            "}";

    @Override
    public String getModuleName() {
        return "com.github.timeu.gwtlibs.processingjsgwt.ProcessingJsGWT";
    }

    public void testCreateAndCheckCanvasExists() {
        Processing<MyProcessingInstance> widget = new Processing<>();
        assertEquals(widget.isLoaded(),false);
        assertEquals(widget.isInjected(),true);
        assertNull(widget.getInstance());
    }

    public void testLoadCode() {
        Processing<MyProcessingInstance> widget = new Processing<>();
        RootPanel.get().add(widget);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkLoadedWdiget(widget);
                assertEquals(widget.getInstance().testMethod("test"),"test");
            }
        };
        widget.load(code, runnable);
    }


    private void checkLoadedWdiget(Processing<MyProcessingInstance> widget) {
        assertEquals(widget.isInjected(),true);
        assertEquals(widget.isLoaded(),true);
        assertNotNull("Processing instance is null",widget.getInstance());
    }

}

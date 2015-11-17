package com.github.timeu.gwtlibs.processingjsgwt.client;


import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * ProcessingInstance is the base class that is used to interface with the ProcessingJS sketch.
 * Every ProcessingJS sketch has some default methods (draw, setup, etc)
 * Users should create their own custom interface extending ProcessingInstance and define the API methods
 * similar to draw and size. The custom interface must be annotated with {@link @JsType}
 */
@JsType(isNative = true,namespace = JsPackage.GLOBAL,name="Processing")
public interface ProcessingInstance {

	/**
	 * Called once when the program is started. Used to define initial enviroment properties such as screen size, background color, loading images, etc. before the draw() begins executing.
	 * Variables declared within setup() are not accessible within other functions, includingdraw().
     * There can only be one setup() function for each program and it should not be called again after it's initial execution.
	 */
	void setup();

	/**
	 * Defines the dimension of the display window in units of pixels. The size() function must be the first line in setup().
     * If size() is not called, the default size of the window is 100x100 pixels.
     * The system variables width and height are set by the parameters passed to the size() function.
	 *
	 * @param width width of the sketch
	 * @param height height of the sketch
	 */
	void size(int width,int height);

	/**
	 * Called directly after setup() and continuously executes the lines of code contained inside its block until the program is stopped or noLoop() is called.
     * The draw() function is called automatically and should never be called explicitly.
     * It should always be controlled with noLoop(), redraw() and loop().
     * After noLoop() stops the code in draw() from executing, redraw() causes the code inside draw() to execute once and loop() will causes the code inside draw() to execute continuously again.
     * The number of times draw() executes in each second may be controlled with the delay() and frameRate() functions.
     * There can only be one draw() function for each sketch and draw() must exist if you want the code to run continuously or to process events such as mousePressed().
     * Sometimes, you might have an empty call to draw() in your program as shown in the above example.
	 */
	void draw();

    /**
     * Executes the code within draw() one time.
     * This functions allows the program to update the display window only when necessary, for example when an event registered by mousePressed() or keyPressed() occurs.
     *
     * In structuring a program, it only makes sense to call redraw() within events such as mousePressed().
     * This is because redraw() does not run draw() immediately (it only sets a flag that indicates an update is needed).
     *
     * Calling redraw() within draw() has no effect because draw() is continuously called anyway.
     */
    void redraw();

    /**
     * Stops Processing from continuously executing the code within draw().
     * If loop() is called, the code in draw() begin to run continuously again.
     * If using noLoop() in setup(), it should be the last line inside the block.
     *
     * When noLoop() is used, it's not possible to manipulate or access the screen inside event handling functions such as mousePressed() or keyPressed().
     * Instead, use those functions to call redraw() or loop(), which will run draw(), which can update the screen properly.
     * This means that when noLoop() has been called, no drawing can happen, and functions like saveFrame() or loadPixels() may not be used.
     *
     * Note that if the sketch is resized, redraw() will be called to update the sketch, even after noLoop() has been specified.
     * Otherwise, the sketch would enter an odd state until loop() was called.
     */
    void noLoop();

    /**
     * Causes Processing to continuously execute the code within draw().
     * If noLoop() is called, the code in draw() stops executing.
     */
    void loop();
}

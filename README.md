## What is ProcessingJs-GWT?


ProcessingJs-GWT is a thin Google Web Toolkit (GWT) wrapper that allows to use [processingjs][1] sketches in GWT applications.

## How do I use it?

Following steps are required:  

Either create new interface that extends or create a class that implements the base interface `ProcessingInstance` and annotate it with `@JsType`. To interact with methods on the processing sketch define methods on that interface (i.e. `testMethod`):

```JAVA
@JsType
public interface MyCustomInstance extends ProcessingInstance {
    String testMethod(String msg);
}
```
Load the Processing sketch either via `ExternalTextResource` or `URL` or pass it directly as a `String`:

**Loading via URL**:
```JAVA
final Processing<MyCustomInstance> processing = new Processing<>();
processing.load(safeUri,new Runnable() {
    @Override
    public void run() {
        GWT.log("Sample initialized.");
        // Interact with sketch
        processing.getInstance().textMethod("test");
    }
}); 
```
**Loading via ExternalTextResource**:
```JAVA
interface ProcessingCodeBundle extends ClientBundle {
    ProcessingCodeBundle INSTANCE = GWT.create(ProcessingCodeBundle.class);
    
    @Source("sample.pde")
    ExternalTextResource sampleCode();
}
final Processing<MyCustomInstance> processing = new Processing<>();
processing.load(ProcessingCodeBundle.INSTANCE.sampleCode(),()-> {
    GWT.log("Sample initialized.");
    // Interact with sketch
    processing.getInstance().textMethod("test");
}; 
```
For a more sophistaced example that shows how to interact with the Processing sketch (Callbacks, etc) refer to the [LDViewer visualization][6]


## How do I install it?

If you're using Maven, you can add the following to your `<dependencies>`
section:

```xml
    <dependency>
      <groupId>com.github.timeu.gwt-libs.processingjs-gwt</groupId>
      <artifactId>processingjs-gwt</artifactId>
      <version>1.0.0</version>
    </dependency>
```

ProcessingJs-GWT uses [GWT 2.7's][4] new [JSInterop feature][5] and thus it has to be enabled in the GWT compiler args.
For maven:
```xml
<compilerArgs>
    <compilerArg>-XjsInteropMode</compilerArg>
    <compilerArg>JS</compilerArg>
</compilerArgs>
```
or passing it to the compiler via `-XjsInteropMode`

You can also download the [jar][1] directly or check out the source using git
from <https://github.com/timeu/processing-js-gwt.git> and build it yourself. Once
you've installed ProcessingJs-GWT, be sure to inherit the module in your .gwt.xml
file like this:

```xml
    <inherits name='com.github.timeu.gwtlibs.processingjsgwt.ProcessingJsGWT'/>
```

## Where can I learn more?

 * Check out the [sample app][2] ([Source Code][3]) for a full example of using ProcessingJs-GWT.
 
[0]: http://processingjs.org
[1]: http://search.maven.org/remotecontent?filepath=com/github/timeu/dygraphs-gwt/dygraphs-gwt/1.0.0/dygraphs-gwt-1.0.0.jar
[2]: https://timeu.github.io/processing-js-gwt
[3]: https://github.com/timeu/processing-js-gwt/tree/master/processingjs-gwt-sample
[4]: http://www.gwtproject.org/release-notes.html#Release_Notes_2_7_0_RC1
[5]: https://docs.google.com/document/d/1tir74SB-ZWrs-gQ8w-lOEV3oMY6u6lF2MmNivDEihZ4/edit#
[6]: https://github.com/timeu/LDViewer

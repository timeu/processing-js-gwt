## What is ProcessingJs-GWT?


ProcessingJs-GWT is a thin Google Web Toolkit (GWT) wrapper that allows to use [processingjs][1] sketches in GWT applications.

## How do I use it?

TODO



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
[3]: https://github.com/timeu/processing-js-gwt/tree/master/processing-js-gwt-sample/src/main/java/sample/client

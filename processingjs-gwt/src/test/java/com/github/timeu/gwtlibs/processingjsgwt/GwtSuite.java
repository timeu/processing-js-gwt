package com.github.timeu.gwtlibs.processingjsgwt;

import com.github.timeu.gwtlibs.processingjsgwt.client.ProcessingTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;

/**
 * Created by uemit.seren on 8/9/15.
 */
public class GwtSuite {

    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite("Test suite for lib");
        suite.addTestSuite(ProcessingTest.class);
        return suite;
    }
}

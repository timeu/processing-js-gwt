/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sample.client;

import com.github.timeu.gwtlibs.processingjsgwt.client.Processing;
import com.github.timeu.gwtlibs.processingjsgwt.client.ProcessingInstance;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Initializes the application. Nothing to see here: everything interesting
 * happens in the presenters.
 */
public class SampleEntryPoint implements EntryPoint {

    @JsType
    public interface MyCustomInstance extends ProcessingInstance {
        String testMethod(String msg);
    }

    public interface ProcessingCodeBundle extends ClientBundle {

        ProcessingCodeBundle INSTANCE = GWT.create(ProcessingCodeBundle.class);

        @Source("sample.pde")
        ExternalTextResource sampleCode();
    }

    @Override
    public void onModuleLoad() {
        Processing<MyCustomInstance> processing = new Processing<>();
        RootPanel.get().add(processing);
        processing.load(ProcessingCodeBundle.INSTANCE.sampleCode(), new Runnable() {
            @Override
            public void run() {
                GWT.log("Sample initialized");
                GWT.log("GWT Code:" +processing.getInstance().testMethod("Hell World"));
            }
        });
    }
}

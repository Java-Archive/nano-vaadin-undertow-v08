/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import com.vaadin.ui.Component;
import io.undertow.Undertow;
import org.rapidpm.frp.model.Triple;
import org.rapidpm.vaadin.nano.ComponentSupplier;
import org.rapidpm.vaadin.nano.Config;
import org.rapidpm.vaadin.nano.CoreUIService;

import static java.lang.System.setProperty;
import static org.rapidpm.vaadin.nano.CoreUI.COMPONENT_SUPPLIER_TO_USE;

public class MultipleServletContainers {

  /**
   * start adding your UI elements here.
   */
  public static class HelloWorldSupplier implements ComponentSupplier {
    @Override
    public Component get() {
      return new BasicTestUI();
    }
  }


  public static void main(String[] args) {

    setProperty(COMPONENT_SUPPLIER_TO_USE, MultipleServletContainers.HelloWorldSupplier.class.getName());

    CoreUIService coreUIServiceA = new CoreUIService();
    coreUIServiceA.startup(new Config("0.0.0.0", 8884 , MultipleServletContainers.HelloWorldSupplier.class));

    CoreUIService coreUIServiceB = new CoreUIService();
    coreUIServiceB.startup(new Config("0.0.0.0", 8885 , MultipleServletContainers.HelloWorldSupplier.class));

    CoreUIService coreUIServiceC = new CoreUIService();
    coreUIServiceC.startup(new Config("0.0.0.0", 8886 , MultipleServletContainers.HelloWorldSupplier.class));

    // shutdown
    //coreUIServiceB.undertow.ifPresent(Undertow::stop);

  }



}

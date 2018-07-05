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
import com.vaadin.ui.Label;
import org.rapidpm.vaadin.nano.ComponentSupplier;
import org.rapidpm.vaadin.nano.CoreUIService;

import static java.lang.System.setProperty;
import static org.rapidpm.vaadin.nano.CoreUI.COMPONENT_SUPPLIER_TO_USE;

/**
 *
 */
public class HelloWorld  {

  public static void main(String[] args) {
    setProperty(COMPONENT_SUPPLIER_TO_USE, HelloWorldSupplier.class.getName());
    new CoreUIService().startup();
  }


  /**
   * start adding your UI elements here.
   */
  public static class HelloWorldSupplier implements ComponentSupplier {
    @Override
    public Component get() {
      return new BasicTestUI();
    }
  }
}

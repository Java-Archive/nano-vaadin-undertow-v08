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
package org.rapidpm.vaadin.nano;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.functions.CheckedSupplier;

import java.util.function.Supplier;

import static java.lang.Class.forName;
import static java.lang.System.getProperty;

@PreserveOnRefresh
@Push
public class CoreUI extends UI implements HasLogger {
  public static final String COMPONENT_SUPPLIER_TO_USE = "COMPONENT_SUPPLIER_TO_USE";

  @Override
  protected void init(VaadinRequest request) {
    final String className = getProperty(COMPONENT_SUPPLIER_TO_USE);
    logger().info("class to load : " + className);
    ((CheckedSupplier<Class<?>>) () -> forName(className))
        .get()
        .ifFailed(e -> logger().warning(e))
        .flatMap((CheckedFunction<Class<?>, Object>) Class::newInstance)
        .flatMap((CheckedFunction<Object, ComponentSupplier>) ComponentSupplier.class::cast)
        .flatMap((CheckedFunction<ComponentSupplier, Component>) Supplier::get)
        .ifPresentOrElse(this::setContent,
                         failed -> logger().warning(failed)
        );
  }
}

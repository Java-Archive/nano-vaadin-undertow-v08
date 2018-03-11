package org.rapidpm.vaadin.nano;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.functions.CheckedSupplier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.function.Supplier;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.redirect;
import static io.undertow.servlet.Servlets.servlet;
import static java.lang.Class.forName;

/**
 *
 */
public class CoreUIService {

  @FunctionalInterface
  public static interface ComponentSupplier extends Supplier<Component> { }

  @PreserveOnRefresh
  @Push
  public static class MyUI extends UI implements HasLogger {
    public static final String COMPONENT_SUPPLIER_TO_USE = "COMPONENT_SUPPLIER_TO_USE";
    @Override
    protected void init(VaadinRequest request) {
      final String className = System.getProperty(COMPONENT_SUPPLIER_TO_USE);
      logger().info("class to load : " + className);
      ((CheckedSupplier<Class<?>>) () -> forName(className))
          .get() //TODO make it fault tolerant
          .flatMap((CheckedFunction<Class<?>, Object>) Class::newInstance)
          .flatMap((CheckedFunction<Object, ComponentSupplier>) ComponentSupplier.class::cast)
          .flatMap((CheckedFunction<ComponentSupplier, Component>) Supplier::get)
          .ifPresentOrElse(this::setContent,
                           failed -> logger().warning(failed)
          );
    }
  }

  @WebServlet("/*")
  @VaadinServletConfiguration(productionMode = false, ui = MyUI.class)
  public static class CoreServlet extends VaadinServlet {
    //customize Servlet if needed
  }

  public static void main(String[] args) throws ServletException {
    new CoreUIService().startup();
  }

  public void startup() throws ServletException {
    DeploymentInfo servletBuilder
        = Servlets.deployment()
                  .setClassLoader(CoreUIService.class.getClassLoader())
                  .setContextPath("/")
                  .setDeploymentName("ROOT.war")
                  .setDefaultEncoding("UTF-8")
                  .addServlets(
                      servlet(
                          CoreServlet.class.getSimpleName(),
                          CoreServlet.class
                      ).addMapping("/*")
                      .setAsyncSupported(true)
                  );

    final DeploymentManager manager = Servlets
        .defaultContainer()
        .addDeployment(servletBuilder);
    manager.deploy();
    PathHandler path = path(redirect("/"))
        .addPrefixPath("/", manager.start());
    Undertow.builder()
            .addHttpListener(8899, "0.0.0.0")
            .setHandler(path)
            .build()
            .start();
  }

}

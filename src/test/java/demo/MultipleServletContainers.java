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

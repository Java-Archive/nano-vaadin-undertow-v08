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
      return new Label("Hello World");
    }
  }
}

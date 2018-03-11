package demo;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import org.rapidpm.vaadin.nano.CoreUIService;

import static org.rapidpm.vaadin.nano.CoreUIService.MyUI.COMPONENT_SUPPLIER_TO_USE;

/**
 *
 */
public class HelloWorld extends CoreUIService {

  static {
    System.setProperty(COMPONENT_SUPPLIER_TO_USE, HelloWorldSupplier.class.getName());
  }

  public static class HelloWorldSupplier implements ComponentSupplier {
    @Override
    public Component get() {
      return new Label("Hello World");
    }
  }
}

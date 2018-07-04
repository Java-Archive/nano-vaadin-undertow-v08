package org.rapidpm.vaadin.nano;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/*", asyncSupported = true, loadOnStartup = 1)
@VaadinServletConfiguration(productionMode = false, ui = CoreUI.class)
public class CoreServlet extends VaadinServlet {
  //customize Servlet if needed
}

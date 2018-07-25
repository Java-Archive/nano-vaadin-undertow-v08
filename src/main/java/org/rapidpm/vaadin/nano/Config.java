package org.rapidpm.vaadin.nano;

import org.rapidpm.frp.model.serial.Triple;

public class Config extends Triple<String, Integer, Class<? extends ComponentSupplier>> {


  public Config(String host, Integer port, Class<? extends ComponentSupplier> supplierClass) {
    super(host, port, supplierClass);
  }

  public String host() { return getT1();}

  public Integer port() { return getT2();}

  public Class<? extends ComponentSupplier> supplierClass() { return getT3();}
}

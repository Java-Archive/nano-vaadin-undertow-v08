package org.rapidpm.vaadin.nano;

import org.rapidpm.frp.model.serial.Pair;

public class Config extends Pair<String, Integer> {


  public Config(String host, Integer port) {
    super(host, port);
  }

  public String host() { return getT1();}

  public Integer port() { return getT2();}
}

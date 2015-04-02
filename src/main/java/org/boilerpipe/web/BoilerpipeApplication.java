package org.boilerpipe.web;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class BoilerpipeApplication extends Application {
  private Set<Object> singletons = new HashSet<Object>();

  public BoilerpipeApplication() {
    singletons.add(new BoilerpipeService());
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }
}

package org.boilerpipe.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class BoilerpipeService {

  @GET
  @Path("/content")
  @Produces("text/json")
  public Map<String, String> getContent() {
    Map<String, String> cont = new HashMap<String, String>();
    cont.put("test", "Oh... hello");

    return cont;
  }

}

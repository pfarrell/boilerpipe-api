package org.boilerpipe.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Media;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.HtmlArticleExtractor;
import de.l3s.boilerpipe.sax.MediaExtractor;


@Path("/")
public class BoilerpipeService {

  @GET
  @Path("/content")
  @Produces("text/json")
  public Map<String, String> getContent(@QueryParam("url") String url, @QueryParam("extractor") String extractorName) {
    Map<String, String> cont = new HashMap<String, String>();
    cont.put("url", url);

    try {
      URL u = new URL(url);
      final BoilerpipeExtractor extractor = getExtractor(extractorName);
      final HtmlArticleExtractor htmlExtr = HtmlArticleExtractor.INSTANCE;
      String html = htmlExtr.process(extractor, u);
      cont.put("content", html);
      cont.put("extractor", extractor.getClass().getSimpleName());
    }catch(Exception ex) {
      ex.printStackTrace();
    }

    return cont;
  }

  private BoilerpipeExtractor getExtractor(String name) {
    BoilerpipeExtractor retval = CommonExtractors.ARTICLE_EXTRACTOR; 
    if(name == null) name="article"; 
    switch(name) {
      case "article": retval = CommonExtractors.ARTICLE_EXTRACTOR;
        break;
      case "largest": retval = CommonExtractors.LARGEST_CONTENT_EXTRACTOR;
        break;
      case "canola": retval = CommonExtractors.CANOLA_EXTRACTOR;
        break;
      default: retval = CommonExtractors.ARTICLE_EXTRACTOR;
    }
    return retval;
  }
}

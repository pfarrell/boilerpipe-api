package org.boilerpipe.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.net.URL;
import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.document.Media;
import de.l3s.boilerpipe.document.Image;
import de.l3s.boilerpipe.extractors.*;
import de.l3s.boilerpipe.sax.ImageExtractor;
import de.l3s.boilerpipe.sax.HtmlArticleExtractor;
import de.l3s.boilerpipe.sax.MediaExtractor;


@Path("/")
public class BoilerpipeService {

  @GET
  @Path("/content")
  @Produces("text/json")
  public Map<String, Object> getContent(@QueryParam("url") String url, @QueryParam("extractor") String extractorName, @QueryParam("words") int words) {
    if(words == 0) words = 10;
    Map<String, Object> ret = new HashMap<String, Object>();
    ret.put("url", url);

    try {
      URL u = new URL(url);
      final BoilerpipeExtractor extractor = getExtractor(extractorName, words);
      final HtmlArticleExtractor htmlExtr = HtmlArticleExtractor.INSTANCE;
      String html = htmlExtr.process(extractor, u);
      ret.put("content", html);
      ret.put("words", words);
      ret.put("extractor", extractor.getClass().getSimpleName());
    }catch(Exception ex) {
      ex.printStackTrace();
    }

    return ret;
  }

  @GET
  @Path("/images")
  @Produces("text/json")
  public Map<String, Object> getImages(@QueryParam("url") String url, @QueryParam("extractor") String extractorName, @QueryParam("words") int words) {
    if(words == 0) words = 10;
    Map<String, Object> ret = new HashMap<String, Object>();
    ret.put("url", url);
    try {
      URL u = new URL(url);
      final BoilerpipeExtractor extractor = getExtractor(extractorName, words);
      final ImageExtractor imageExtr = ImageExtractor.INSTANCE;
      List<Image> images = imageExtr.process(u, extractor);
      Collections.sort(images);
      ret.put("images", images);
      ret.put("words", words);
      ret.put("extractor", extractor.getClass().getSimpleName());
    }catch(Exception ex) {
      ex.printStackTrace();
    }
    return ret;
  }

  private BoilerpipeExtractor getExtractor(String name, int words) {
    BoilerpipeExtractor retval = CommonExtractors.ARTICLE_EXTRACTOR; 
    if(name == null) name=""; 
    switch(name) {
      case "article": retval = CommonExtractors.ARTICLE_EXTRACTOR; break;
      case "canola": retval = CommonExtractors.CANOLA_EXTRACTOR; break;
      case "everything": retval = KeepEverythingExtractor.INSTANCE; break;
      case "largest": retval = CommonExtractors.LARGEST_CONTENT_EXTRACTOR; break;
      case "mink": retval = new KeepEverythingWithMinKWordsExtractor(words); break;
      case "numwords": retval =  NumWordsRulesExtractor.INSTANCE; break;
      case "sentences": retval = ArticleSentencesExtractor.INSTANCE; break;
      default: retval = CommonExtractors.DEFAULT_EXTRACTOR;
    }
    return retval;
  }
}

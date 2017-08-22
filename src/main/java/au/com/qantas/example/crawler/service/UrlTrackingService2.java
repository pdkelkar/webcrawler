package au.com.qantas.example.crawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * @author Priyadarshan
 *
 */
@Service
@Qualifier("urlTrackingService2")
public class UrlTrackingService2 implements UrlTrackingServiceIF {

  private final Logger LOG = LoggerFactory.getLogger(this.getClass());

  private static final int MAX_PAGES_TO_SEARCH = 3;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();
  private List<String> links = new LinkedList<String>();
  private Map<String,PageNode> pageNodeMap = new HashMap<>();
  private Map<String,List<String>> parentChildUrlMap = new HashMap<>();

  /*
   * (non-Javadoc)
   * 
   * @see au.com.qantas.example.crawler.service.UrlTrackingServiceIF# buildUrlPageTree(java.lang.String, int)
   */
  @Override
  public Set<PageNode> buildUrlPageTree(String url, int depth) throws MalformedURLException, IOException {
    Set<PageNode> nodes = new HashSet<>();
    clearFields();
    while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
      String currentUrl;

      if (CollectionUtils.isEmpty(pagesToVisit)) {
        currentUrl = url; //initial first request URL
        this.pagesVisited.add(url);
      } else {
        currentUrl = this.nextUrl();
      }
      PageNode node = crawl(currentUrl);
      pageNodeMap.put(currentUrl, node);
      nodes.add(node);
      this.pagesToVisit.addAll(getLinks());
    }
    LOG.debug("pageNodeMap: {}",pageNodeMap);
    LOG.debug("parentChildUrlMap: {}",parentChildUrlMap);
    return nodes;
  }

  private String nextUrl() {
    String nextUrl;
    do {
      nextUrl = this.pagesToVisit.remove(0);
    } while (this.pagesVisited.contains(nextUrl));
    this.pagesVisited.add(nextUrl);
    return nextUrl;
  }
  
  private PageNode crawl(String url) throws MalformedURLException, IOException{
    PageNode pageNode = new PageNode();
    Document parentDoc = Jsoup.connect(url).get();
    pageNode.setUrl(url);
    pageNode.setTitle(parentDoc.title());
    Elements webLinks = parentDoc.select("a[href]");
    //@formatter:off
    List<Element> elementList = webLinks.stream()
                                        .limit(MAX_PAGES_TO_SEARCH+1)
                                        .collect(Collectors.toList());
   //@formatter:on
    webLinks = new Elements(elementList);
    List<String> childLinks = new ArrayList<>();
    for (Element webPage : webLinks) {
      String absUrl = webPage.attr("abs:href");
      this.links.add(absUrl);
      childLinks.add(absUrl);
    } 
    parentChildUrlMap.put(url, childLinks);
    return pageNode;
  }

  
  public List<String> getLinks()
  {
      return this.links;
  }
  
  
  private boolean isUrlVisited(String url) {

    if (!CollectionUtils.isEmpty(pagesVisited)) {
      return pagesVisited.contains(url);
    }

    return false;
  }

  private void clearFields() {
    if (!CollectionUtils.isEmpty(this.pagesVisited)) {
      this.pagesVisited.clear();
    }
    
    if (!CollectionUtils.isEmpty(this.pagesToVisit)) {
      this.pagesToVisit.clear();
    }
    
    if (!CollectionUtils.isEmpty(this.links)) {
      this.links.clear();
    }
    
    if (!CollectionUtils.isEmpty(this.parentChildUrlMap)) {
        this.parentChildUrlMap.clear();
    }
    
    if (!CollectionUtils.isEmpty(this.pageNodeMap)) {
        this.pageNodeMap.clear();
    }
  }

  private boolean isValidURL(String urlStr) throws MalformedURLException {
    boolean result = false;
    try {
      if (StringUtils.isNotBlank(urlStr)) {
        URL url = new URL(urlStr);
        LOG.debug("url.getHost(): {}", url.getHost());
        result = true;
      }
    } catch (MalformedURLException mue) {
      throw mue;
    }
    return result;
  }

}

/**
 * 
 */
package au.com.qantas.example.crawler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * @author Priyadarshan
 *
 */
@Service
public class UrlTrackingService implements UrlTrackingServiceIF {

	private Set<PageNode> urlTree = new HashSet<>();
	private Set<String> visitedUrls = new HashSet<>();
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.qantas.example.crawler.service.UrlTrackingServiceIF#
	 * buildUrlPageTree(java.lang.String, int)
	 */
	@Override
	public Set<PageNode> buildUrlPageTree(String url, int depth) {
		
		
		try {
			PageNode parentNode = new PageNode();
			
			if(!isUrlVisited(url) && StringUtils.isNotBlank(url)){
				visitedUrls.add(url);
				Document parentDoc = Jsoup.connect(url).get();			
				parentNode.setUrl(url);
				parentNode.setTitle(parentDoc.title());
				Elements webLinks = parentDoc.select("a[href]");
				Set<PageNode> childNodes = new HashSet<>();
				for(Element webPage: webLinks){
					String absUrl = webPage.absUrl("abs:href");
//					System.out.println("webPage.absUrl: "+absUrl);
					System.out.println("webPage.attr: "+webPage.attr("abs:href"));
					PageNode childNode = new PageNode();
					if(StringUtils.isNotBlank(absUrl)){
						Document childDoc = Jsoup.connect(absUrl).get();
						childNode.setUrl(absUrl);
						childNode.setTitle(childDoc.title());
						visitedUrls.add(absUrl);
						childNodes.add(childNode);
						parentNode.setNodes(childNodes);
						buildUrlPageTree(absUrl, depth);
					}
				}	
				
				urlTree.add(parentNode);
				
			}
			
		} catch (IOException ie) {
			// TODO log error
			ie.printStackTrace();
		}
		
		return urlTree;
	}

	private boolean isUrlVisited(String url) {

		if (!CollectionUtils.isEmpty(visitedUrls)) {
			return visitedUrls.contains(url);
		}

		return false;
	}

}

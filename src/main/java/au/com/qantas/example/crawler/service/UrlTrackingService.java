/**
 * 
 */
package au.com.qantas.example.crawler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * @author Priyadarshan
 *
 */
@Service
public class UrlTrackingService implements UrlTrackingServiceIF {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private Set<PageNode> urlTree = new HashSet<>();
	private Set<String> visitedUrls = new HashSet<>();
	private Set<PageNode> childTree = new HashSet<>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see au.com.qantas.example.crawler.service.UrlTrackingServiceIF#
	 * buildUrlPageTree(java.lang.String, int)
	 */
	@Override
	public Set<PageNode> buildUrlPageTree(String url, int depth) {
		
		LOG.debug("UrlTrackingService::buildUrlPageTree - enter");
		clearFields();
		PageNode pageNode = buildPageNode(url,depth);
		Set<PageNode> pageNodes = pageNode.getNodes();
		if(!CollectionUtils.isEmpty(pageNodes)){
			for(PageNode node:pageNodes){
				String nodeUrl = node.getUrl();
				PageNode childNode = buildPageNode(nodeUrl,depth);
				childTree.add(childNode);
			}
		}		
		pageNode.setNodes(childTree);
		urlTree.add(pageNode);
		LOG.debug("UrlTrackingService::buildUrlPageTree - exit");
		return urlTree;
	}
	
	private PageNode buildPageNode(String url, int depth){
		LOG.debug("UrlTrackingService::buildPageNode - enter");
		PageNode parentNode = new PageNode();
		try {
			
			if(StringUtils.isNotBlank(url)){
				visitedUrls.add(url);
				Document parentDoc = Jsoup.connect(url).get();			
				parentNode.setUrl(url);
				parentNode.setTitle(parentDoc.title());
				Elements webLinks = parentDoc.select("a[href]");
				List<Element> elementList = webLinks.stream()
													.limit(depth)
													.collect(Collectors.toList());
				webLinks = new Elements(elementList);
				Set<PageNode> childNodes = new HashSet<>();
				for (Element webPage : webLinks) {
					String absUrl = webPage.attr("abs:href");
					LOG.debug("absUrl: {}", absUrl);
					if (StringUtils.isNotBlank(absUrl) && !isUrlVisited(absUrl)) {
						PageNode childNode = new PageNode();
						Document childDoc = Jsoup.connect(absUrl).get();
						childNode.setUrl(absUrl);
						childNode.setTitle(childDoc.title());
						visitedUrls.add(absUrl);
						childNodes.add(childNode);						
						//buildUrlPageTree(absUrl, depth);
					}
				}
				parentNode.setNodes(childNodes);
				//urlTree.add(parentNode);

			}
			
		} catch (IOException ie) {
			LOG.error("There was an error while parsing the URL: {}",ie);
		}
		LOG.debug("UrlTrackingService::buildPageNode - exit");
		return parentNode;
	}

	private boolean isUrlVisited(String url) {

		if (!CollectionUtils.isEmpty(visitedUrls)) {
			return visitedUrls.contains(url);
		}

		return false;
	}
	
	private void clearFields(){
		if(!CollectionUtils.isEmpty(this.urlTree)){
			this.urlTree.clear();
		}
		
		if(!CollectionUtils.isEmpty(this.childTree)){
			this.childTree.clear();
		}
		
		if(!CollectionUtils.isEmpty(this.visitedUrls)){
			this.visitedUrls.clear();
		}
	}

}

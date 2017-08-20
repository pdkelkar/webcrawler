/**
 * 
 */
package au.com.qantas.example.crawler.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * @author Priyadarshan
 *
 */
@Service
public class UrlTrackingService implements UrlTrackingServiceIF {

	/* (non-Javadoc)
	 * @see au.com.qantas.example.crawler.service.UrlTrackingServiceIF#buildUrlPageTree(java.lang.String, int)
	 */
	@Override
	public Set<PageNode> buildUrlPageTree(String url, int depth) {
		
		Set<PageNode> urlTree = new HashSet<>();
		PageNode pageNode = new PageNode();
		pageNode.setUrl(url);
		pageNode.setTitle("title");
		urlTree.add(pageNode);
		
		return urlTree;
	}

}

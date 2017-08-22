package au.com.qantas.example.crawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * 
 * @author Priyadarshan
 *
 */
public interface UrlTrackingServiceIF {
	
	public Set<PageNode> buildUrlPageTree(String url, int depth) throws MalformedURLException, IOException;

}

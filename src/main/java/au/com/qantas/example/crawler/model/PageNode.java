/**
 * 
 */
package au.com.qantas.example.crawler.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Priyadarshan
 *
 */
public class PageNode implements Serializable {

	private static final long serialVersionUID = -1986097072974592347L;
	
	private String url;
	private String title;
	private Set<PageNode> nodes = new HashSet<>();
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Set<PageNode> getNodes() {
		return nodes;
	}
	public void setNodes(Set<PageNode> nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageNode [url=").append(url).append(", title=").append(title).append(", nodes=").append(nodes)
				.append("]");
		return builder.toString();
	}
}

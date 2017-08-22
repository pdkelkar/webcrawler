package au.com.qantas.example.crawler.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.com.qantas.example.crawler.model.PageNode;

/**
 * 
 * @author Priyadarshan
 *
 */
public class UrlTrackingServiceTest {
  
  @Mock
  private UrlTrackingService urlTrackingService;
  
  @Rule
  public ExpectedException exception = ExpectedException.none();


  @Before
  public void setUp() throws Exception {
    //Necessary to enable @Mock annotation
    MockitoAnnotations.initMocks(this);    
    Set<PageNode> stubNodes = buildPageNodes();
    when(urlTrackingService.buildUrlPageTree(anyString(), anyInt())).thenReturn(stubNodes);
    
  }

  @Test
  public void testBuildUrlPageTree() throws Exception {
    String url = "http://google.com.au";
    int depth = 2;
    Set<PageNode> nodes = urlTrackingService.buildUrlPageTree(url, depth);
    
    assertThat(nodes, notNullValue());
    
  }
  
  @Test
  public void testMalFormedExceptionURL() throws Exception{
    exception.expect(MalformedURLException.class);
    UrlTrackingService trackingService = new UrlTrackingService();
    String url = "google.com.au";
    int depth = 2;
    trackingService.buildUrlPageTree(url, depth);
  }
  
  private Set<PageNode> buildPageNodes(){
    Set<PageNode> pageNodes = new HashSet<>();
    
    PageNode pg1 = new PageNode();
    pg1.setUrl("http://google.com");
    pg1.setTitle("Google");
    
    PageNode pg2 = new PageNode();
    pg2.setUrl("http://image.google.com");
    pg2.setTitle("Google Images");
    
    PageNode pg3 = new PageNode();
    pg3.setUrl("http://mail.google.com");
    pg3.setTitle("Gmail");
    
    pg1.getNodes().add(pg2);
    pg1.getNodes().add(pg3);
    
    pageNodes.add(pg1);
    return pageNodes;
  }

}

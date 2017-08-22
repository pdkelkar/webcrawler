package au.com.qantas.example.crawler.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import au.com.qantas.example.crawler.service.UrlTrackingService;

/**
 * 
 * @author Priyadarshan
 *
 */
public class UrlTrackingControllerTest {
  
  @Mock
  private UrlTrackingService urlTrackingService;
  
  @InjectMocks
  private UrlTrackingController urlTrackingController = new UrlTrackingController();
  
  private MockMvc mockMvc;
  

  @Before
  public void setUp() throws Exception {    
    //Necessary to enable @Mock annotation
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(urlTrackingController).build();
  }

  @Test
  public void testGetUrlTreeWithCorrectURL() throws Exception{
    String url = "http://google.com.au";
    int depth = 2;
    ResponseEntity<String> responseEntity = urlTrackingController.getUrlTree(url, depth);
    assertThat(responseEntity, notNullValue());
    assertThat(responseEntity.getBody(), notNullValue());
    assertThat(responseEntity.getStatusCode(), notNullValue());
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    
    this.mockMvc.perform(get("/api/webcrawl?url="+url+"&depth="+depth))
          .andExpect(status().isOk());
  }
  
  
  @Test
  public void testGetUrlTreeWithInCorrectURL() throws Exception{
    String url = "http://google.com.au";
    int depth = 2;
    this.mockMvc.perform(get("/api/webcrawler?url="+url+"&depth="+depth))
          .andExpect(status().is4xxClientError());
  }

}

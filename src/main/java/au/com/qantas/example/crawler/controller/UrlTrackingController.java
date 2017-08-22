package au.com.qantas.example.crawler.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import au.com.qantas.example.crawler.model.PageNode;
import au.com.qantas.example.crawler.service.UrlTrackingServiceIF;

/**
 * 
 * @author Priyadarshan
 *
 */
@RestController
public class UrlTrackingController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("urlTrackingService2")
	private UrlTrackingServiceIF urlTrackingService;
	
	@GetMapping(value="/api/webcrawl", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getUrlTree(@RequestParam("url") String url, @RequestParam("depth") int depth) throws MalformedURLException, IOException{
		
		LOG.info("UrlTrackingController::getUrlTree() - enter");
		
		Set<PageNode> urlTree = urlTrackingService.buildUrlPageTree(url, depth);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(urlTree);
		
		LOG.info("UrlTrackingController::getUrlTree() - exit");
		return new ResponseEntity<String>(json, HttpStatus.OK);
		
	}

}

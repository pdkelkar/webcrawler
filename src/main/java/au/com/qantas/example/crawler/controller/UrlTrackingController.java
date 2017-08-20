package au.com.qantas.example.crawler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlTrackingController {
	
	@GetMapping(value="/api/webcrawl", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getUrlTree(){
		
		String json = "Hello World";
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
		
	}

}

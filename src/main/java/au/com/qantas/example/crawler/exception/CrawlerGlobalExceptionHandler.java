package au.com.qantas.example.crawler.exception;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Priyadarshan
 *
 */
@ControllerAdvice(basePackages="au.com.qantas.example.crawler")
public class CrawlerGlobalExceptionHandler {
  
  private final Logger LOG = LoggerFactory.getLogger(this.getClass());
  
  @ExceptionHandler(MalformedURLException.class)
  @ResponseBody
  public ResponseEntity<CrawlerErrorResponse> handleMalformedURLException(MalformedURLException e){
    LOG.error("MalformedURLException: ",e);
    CrawlerErrorResponse cer = new CrawlerErrorResponse("URL protocol format is not correct");
    return new ResponseEntity<CrawlerErrorResponse>(cer,HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(IOException.class)
  public String handleIOException(IOException e){
    LOG.error("IOException occured : {} ",e);
    return e.getMessage();
  }
  
  @ExceptionHandler(Exception.class)
  public String exception(Exception e) { 
    LOG.error("Exception occured {}",e.getMessage());
    return e.getMessage();
  }

}

package au.com.qantas.example.crawler.exception;

import java.io.Serializable;

/**
 * 
 * @author Priyadarshan
 *
 */
public class CrawlerErrorResponse implements Serializable {

  private static final long serialVersionUID = -5424528844509695492L;
  
  private String errorMsg;

  public CrawlerErrorResponse(String errorMsg) {
    super();
    this.errorMsg = errorMsg;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
  
}

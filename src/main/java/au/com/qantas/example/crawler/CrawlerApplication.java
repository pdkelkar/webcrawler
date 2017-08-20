package au.com.qantas.example.crawler;

/**
 * @author Priyadarshan
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class, 
					   scanBasePackages={"au.com.qantas.example.crawler"})
public class CrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}
}

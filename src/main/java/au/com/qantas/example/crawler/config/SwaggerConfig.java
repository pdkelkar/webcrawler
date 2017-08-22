package au.com.qantas.example.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Priyadarshan
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  
  @Bean
  public Docket getApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("au.com.qantas.example.crawler.controller"))
        .paths(PathSelectors.any())
        .build();

  }
}

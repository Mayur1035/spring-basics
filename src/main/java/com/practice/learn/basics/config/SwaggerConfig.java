/**
 * 
 */
package com.practice.learn.basics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author mayur-raj
 *
 */
@Configuration
public class SwaggerConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.practice.learn.basics.api"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
	
	//Sample to use when OpenAPI is added instead of springfox
		/*@Bean
		  public GroupedOpenApi publicApi() {
		      return GroupedOpenApi.builder()
		    		  .group("public")
		    		  .packagesToScan("com.practice.learn.basics.api")
		              .pathsToMatch("*")
		              .build();
		  }*/

}

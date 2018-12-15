package mx.uam.tsis.sbtutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import property.FileStorageProperties;

/**
 * Clase principal
 * 
 * http://howtodoinjava.com/spring/spring-core/how-to-use-spring-component-repository-service-and-controller-annotations/
 * https://spring.io/guides/tutorials/bookmarks/
 * https://spring.io/guides/gs/rest-service/
 * https://spring.io/guides/gs/serving-web-content/
 * https://spring.io/guides/gs/spring-boot/#scratch
 * 
 */
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class Application extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(Application.class);
    }

	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
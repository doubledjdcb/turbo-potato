package org.caringbridge.services.security;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main class to run the application.
 * 
 * @author Simi George
 *
 */

@SpringBootApplication(exclude=EmbeddedMongoAutoConfiguration.class)
@ComponentScan("org.caringbridge.services.security")
@RestController
@EnableSwagger2
@Api(basePath = "/oauthserver", description = "The security api for demonstration purposes.", value = "security")
@RequestMapping(path = "/oauthserver")
public class CbApplication{

    /**
	 * Main method to run the Spring Boot Application.
	 * @param args arguments used when running on command line.
	 */
	public static void main(final String[] args) {
		SpringApplication.run(CbApplication.class, args);
	}
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	
}




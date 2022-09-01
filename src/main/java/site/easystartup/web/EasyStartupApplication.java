package site.easystartup.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.OAUTH2, name = "api_key", in = SecuritySchemeIn.HEADER)
public class EasyStartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyStartupApplication.class, args);
	}

}

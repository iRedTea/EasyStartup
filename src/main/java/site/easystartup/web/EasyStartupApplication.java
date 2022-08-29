package site.easystartup.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EasyStartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyStartupApplication.class, args);
	}

}

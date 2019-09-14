package app.login.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("app.login")
@SpringBootApplication
public class LoginUtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginUtApplication.class, args);
	}

}

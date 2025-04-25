package cl.ntt.postulacion;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Postulacion APIs",
				version = "1.0.0",
				description = "Postulacion REST APIs",
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
				contact =@Contact(email="leoarriagadamartinez@gmail.com")
		),

		servers = { @Server(
				description = "PROD basePath",
				url = "https://{test}.api.com/base/path/{version}/"

		)        }
)
public class PostulacionApplication {

	public static void main(String[] args) {

		SpringApplication.run(PostulacionApplication.class, args);
	}

}

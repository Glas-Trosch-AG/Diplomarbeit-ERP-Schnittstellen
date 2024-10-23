package ch.glastroesch.hades.business.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8095");
        server.setDescription("Cockpit Development");

        Contact myContact = new Contact();
        myContact.setName("Marinko Andrijanic");
        myContact.setEmail("m.andrijanic@glastroesch.ch");

        Info information = new Info()
                .title("Cockpit Application for ERP interfaces")
                .version("1.0")
                .description("This API exposes endpoints to the cockpit functions")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}

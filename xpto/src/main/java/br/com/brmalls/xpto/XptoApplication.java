package br.com.brmalls.xpto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;

@SpringBootApplication( exclude = {
		WebSocketMessagingAutoConfiguration.class,
		WebServiceTemplateAutoConfiguration.class
} )
public class XptoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XptoApplication.class, args);
	}

}

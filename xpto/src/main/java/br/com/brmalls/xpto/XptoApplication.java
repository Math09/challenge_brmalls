package br.com.brmalls.xpto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;

import static java.lang.Runtime.getRuntime;

@SpringBootApplication( exclude = {
		WebSocketMessagingAutoConfiguration.class,
		WebServiceTemplateAutoConfiguration.class
} )
public class XptoApplication {

	private final static Logger logger = LoggerFactory.getLogger( XptoApplication.class );

	public static void main( String[] args ) {
		getRuntime().addShutdownHook(
				new Thread( () -> {
					System.out.println( "API being shut down." );
					logger.info( "API shut down." );
				} )
		);

		System.out.println( "Starting XPTO application..." );
		logger.info( "Starting XPTO application..." );

		SpringApplication.run( XptoApplication.class, args );
	}

}

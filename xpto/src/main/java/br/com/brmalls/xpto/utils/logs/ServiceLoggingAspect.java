package br.com.brmalls.xpto.utils.logs;

import br.com.brmalls.xpto.utils.CONSTANTS;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger( ServiceLoggingAspect.class );

    @Before( "execution( * br.com.brmalls.xpto.services.impls..*( String, .. ) ) && args( cnpj, .. )" )
    public void logBefore( JoinPoint joinPoint, String cnpj ) {
        final String className = OriginalClassName.get( joinPoint );
        logger.info( CONSTANTS.START_METHOD, joinPoint.getSignature().getName(), className, cnpj );
        logger.info( "---" );
    }

    @AfterReturning( "execution( * br.com.brmalls.xpto.services.impls..*( String, .. ) ) && args( cnpj, .. )" )
    public void logAfter( JoinPoint joinPoint, String cnpj ) {
        final String className = OriginalClassName.get( joinPoint );
        logger.info( "---" );
        logger.info( CONSTANTS.END_METHOD, joinPoint.getSignature().getName(), className, cnpj );
    }

}

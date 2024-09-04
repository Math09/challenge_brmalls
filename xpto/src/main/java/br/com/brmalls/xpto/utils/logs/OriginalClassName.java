package br.com.brmalls.xpto.utils.logs;

import org.aspectj.lang.JoinPoint;

public class OriginalClassName {

    public static String get( JoinPoint joinPoint ) {
        final Class<?> className = joinPoint.getTarget().getClass();

        if( className.getName().contains( "$$" ) ) {
            return className.getSuperclass().getName();
        }
        else {
            return className.getSimpleName();
        }
    }

}

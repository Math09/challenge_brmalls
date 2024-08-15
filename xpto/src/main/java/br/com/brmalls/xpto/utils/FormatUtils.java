package br.com.brmalls.xpto.utils;

public class FormatUtils {

    public static String formatCNPJ( String cnpj ) {
        return cnpj.replaceAll( "[^0-9]", "" );
    }

    public static String formatMessageErrorWithCNPJ( String message, String cnpj ) {
        return message.replace( "{}", cnpj );
    }

}

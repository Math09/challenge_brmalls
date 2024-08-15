package br.com.brmalls.xpto.utils;

import java.util.InputMismatchException;

public class CNPJUtils {

    public static boolean isValidCNPJ( String cnpj ) {
        String cleanCNPJ = FormatUtils.formatCNPJ( cnpj );
        return isCorrectCNPJ( cleanCNPJ ) && isValidCNPJDigits( cleanCNPJ );
    }

    private static boolean isCorrectCNPJ( String cnpj ) {
        return cnpj.length() == 14;
    }

    private static boolean isValidCNPJDigits( String cnpj ) {
        try {
            int sum = 0;
            int[] weight = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
            char[] cnpjArray = cnpj.toCharArray();

            for( int i = 0; i < 12; i++ ) {
                int num = Integer.parseInt( String.valueOf( cnpjArray[i] ) );
                sum += num * weight[i + 1];
            }

            int digit_one = 11 - ( sum % 11 );
            digit_one = ( digit_one >= 10 ) ? 0 : digit_one;

            sum = 0;
            for( int i = 0; i < 13; i++ ) {
                int num = Integer.parseInt( String.valueOf( cnpjArray[i] ) );
                sum += num * weight[i];
            }

            int digit_two = 11 - ( sum % 11 );
            digit_two = ( digit_two >= 10 ) ? 0 : digit_two;

            return ( digit_one == Character.getNumericValue( cnpjArray[12] ) ) &&
                   ( digit_two == Character.getNumericValue( cnpjArray[13] ) );
        }
        catch( InputMismatchException e ) {
            return false;
        }
    }

}

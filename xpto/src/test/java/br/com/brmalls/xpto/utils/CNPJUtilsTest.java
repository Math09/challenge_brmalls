package br.com.brmalls.xpto.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CNPJUtilsTest {

    @ParameterizedTest
    @ValueSource( strings = { "CNPJ_PLACEHOLDER", "CNPJ_PLACEHOLDER" } )
    void testValidCNPJ( String cnpj ) {
        assertTrue( CNPJUtils.isValidCNPJ( cnpj ) );
    }

    @Test
    void isInvalidCNPJForLength() {
        final String invalidCNPJ = "CNPJ_PLACEHOLDER"; // CNPJ with less than 14 digits.
        assertFalse( CNPJUtils.isValidCNPJ( invalidCNPJ ) );
    }

    @Test
    void isInvalidCNPJIncorrectDigits() {
        final String invalidCNPJ = "CNPJ_PLACEHOLDER"; // CNPJ with incorrect check digits.
        assertFalse( CNPJUtils.isValidCNPJ( invalidCNPJ ) );
    }

    @Test
    void isInvalidCNPJInputMismatchException() {
        final String invalidCNPJ = "CNPJ_PLACEHOLDER"; // CNPJ with an invalid character (letter 'A').
        assertFalse( CNPJUtils.isValidCNPJ( invalidCNPJ ) );
    }

    @Test
    void validateCNPJWithFormatting() {
        final String formattedCNPJ = "CNPJ_PLACEHOLDER"; // CNPJ with formatting.
        assertTrue( CNPJUtils.isValidCNPJ( formattedCNPJ ) );
    }

}

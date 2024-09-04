package br.com.brmalls.xpto.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatUtilsTest {

    @ParameterizedTest
    @CsvSource( {
            "11.111.111/1111-11, 11111111111111",
            "22.222.222.2222.22, 22222222222222",
            "33-333-333-3333-33, 33333333333333",
            "44/444/444/4444/44, 44444444444444",
            "55.555/555-5555.55, 55555555555555",
            "66 . 666 . 666 /6666- 66, 66666666666666",
            "77a.777b.777/7777c-77, 77777777777777",
            "8-8.88-88.888/8-888-88, 88888888888888",
            "99./99/9.999/9/999/-99, 99999999999999",
            "00 000 000 0000 00, INVALID_CNPJ_PLACEHOLDER",
            "1 1 1 1 1 1 1 1 1 1 1 1 1 1, 11111111111111"
    } )
    void isRemoveFormats( String cnpj, String cnpjExpected ) {
        final String cleanedCNPJ = FormatUtils.formatCNPJ( cnpj );
        assertEquals( cnpjExpected, cleanedCNPJ, "The method should remove all non-numeric characters and return the correctly formatted CNPJ." );
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "",
            " ",
            "              "
    } )
    void isEmptyOrWhitespace( String cnpj ) {
        final String expected = "";
        final String actual = FormatUtils.formatCNPJ( cnpj );

        assertEquals( expected, actual, "The method should return an empty string when the input is an empty string or whitespace." );
    }

    @Test
    void isReturningNull() {
        assertThrows( NullPointerException.class, () -> FormatUtils.formatCNPJ( null ), "The method should throw a NullPointerException when the input is null." );
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Error with CNPJ: {}",
            "Some message with {} and another {}"
    } )
    void isFormattedMessageWithValidCNPJ( String message ) {
        final String cnpj = "CNPJ_PLACEHOLDER";
        final String expected = message.replace( "{}", cnpj );
        final String formattedMessage = FormatUtils.formatMessageErrorWithCNPJ( message, cnpj );

        assertEquals( expected, formattedMessage, "The CNPJ should replace the placeholder {} in the message." );
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Error with CNPJ: {}",
            "Some message with {} and another {}"
    } )
    void isFormattedMessageWithNullCNPJ( String message ) {
        final String cnpj = null;
        final String expected = message.replace( "{}", cnpj != null ? cnpj : "cnpj_null" );
        final String actual = FormatUtils.formatMessageErrorWithCNPJ( message, null );

        assertEquals( expected, actual, "The placeholder {} should be replaced with the string 'cnpj_null' if the CNPJ is null." );
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Error with CNPJ: {}",
            "Some message with {} and another {}"
    })
    void isFormattedMessageWithEmptyCNPJ( String message ) {
        final String expected = message.replace( "{}", "" );
        final String actual = FormatUtils.formatMessageErrorWithCNPJ( message, "" );

        assertEquals( expected, actual, "The placeholder {} should be replaced with an empty string if the CNPJ is empty." );
    }

    @Test
    void isNotFormattedMessageWithNoPlaceholder() {
        final String message = "No placeholder here";
        final String cnpj = "CNPJ_PLACEHOLDER";
        final String expected = "No placeholder here";

        final String actual = FormatUtils.formatMessageErrorWithCNPJ( message, cnpj );

        assertEquals( expected, actual, "The message should remain unchanged if there is no placeholder {}." );
    }

    @Test
    void isNullPointerWithNullMessage() {
        final String cnpj = "CNPJ_VARIABLE";
        assertThrows( NullPointerException.class, () -> FormatUtils.formatMessageErrorWithCNPJ( null, cnpj ), "It should throw a NullPointerException when the message is null." );
    }

}

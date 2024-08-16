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
        assertEquals( cnpjExpected, cleanedCNPJ, "O método deve remover todos os caracteres não numéricos e retornar o CNPJ corretamente formatado." );
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

        assertEquals( expected, actual, "O método deve retornar uma string vazia quando a entrada é uma string vazia ou espaços em branco." );
    }

    @Test
    void isReturningNull() {
        assertThrows( NullPointerException.class, () -> FormatUtils.formatCNPJ( null ), "O método deve lançar NullPointerException quando a entrada é null." );
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Error with CNPJ: {}",
            "Some message with {} and another {}"
    } )
    void isFormattedMessageWithValidCNPJ( String message ) {
        final String cnpj = "CNPJ_VARIABLE";
        final String expected = message.replace( "{}", cnpj );
        final String formattedMessage = FormatUtils.formatMessageErrorWithCNPJ( message, cnpj );

        assertEquals( expected, formattedMessage, "O CNPJ deve substituir o placeholder {} na mensagem." );
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

        assertEquals( expected, actual, "O placeholder {} deve ser substituído pela string 'cnpj_null' se o CNPJ for null." );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Error with CNPJ: {}",
            "Some message with {} and another {}"
    })
    void isFormattedMessageWithEmptyCNPJ( String message ) {
        final String expected = message.replace( "{}", "" );
        final String actual = FormatUtils.formatMessageErrorWithCNPJ( message, "" );

        assertEquals( expected, actual, "O placeholder {} deve ser substituído por uma string vazia se o CNPJ for vazio." );
    }

    @Test
    void isNotFormattedMessageWithNoPlaceholder() {
        final String message = "No placeholder here";
        final String cnpj = "CNPJ_VARIABLE";
        final String expected = "No placeholder here";

        final String actual = FormatUtils.formatMessageErrorWithCNPJ( message, cnpj );

        assertEquals( expected, actual, "A mensagem deve permanecer inalterada se não houver placeholder {}." );
    }

    @Test
    void isNullPointerWithNullMessage() {
        final String cnpj = "CNPJ_VARIABLE";
        assertThrows( NullPointerException.class, () -> FormatUtils.formatMessageErrorWithCNPJ( null, cnpj ), "Deve lançar NullPointerException quando a mensagem é null." );
    }

}

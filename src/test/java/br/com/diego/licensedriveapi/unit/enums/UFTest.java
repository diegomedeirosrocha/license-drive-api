package br.com.diego.licensedriveapi.unit.enums;

import br.com.diego.licensedriveapi.enums.UF;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UFTest {

    @Test
    void mustReturnUF(){
        var sigla = UF.fromSigla("AM");
        assertEquals(UF.AMAZONAS, sigla);
    }

    @Test
    void mustReturnException(){
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    UF.fromSigla("XX_ERROR");
                });

        assertThat(exception.getMessage(), containsString("XX_ERROR is invalid"));
    }
}

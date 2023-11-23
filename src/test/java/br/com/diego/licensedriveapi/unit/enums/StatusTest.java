package br.com.diego.licensedriveapi.unit.enums;

import br.com.diego.licensedriveapi.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @ParameterizedTest
    @CsvSource({
            "DENY",
            "ALLOW_IF_PERMISSION_FATHER",
            "ALLOW"})
    void mustReturnTrue(String status){
        var valid = Status.isValid(status);
        assertTrue(valid);
    }

    @Test
    void mustReturnException() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    Status.valueOf( "TESTE_ERROR");
                });

        assertThat(exception.getMessage(), containsString("No enum constant"));
    }
}

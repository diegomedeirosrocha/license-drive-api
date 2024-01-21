package br.com.diego.licensedriveapi.unit.utils;

import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.utils.RulesLicense;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RulesLicenseTest {

    private final RulesLicense rulesLicense = new RulesLicense();

    @ParameterizedTest
    @CsvSource({
            "15, SP, DENY",
            "15, RJ, DENY",
            "16, SP, ALLOW_IF_PERMISSION_FATHER",
            "17, SP, ALLOW_IF_PERMISSION_FATHER",
            "18, SP, ALLOW",
            "50, SP, ALLOW"})
    @DisplayName("mustValidateOthersUf")
    void mustValidateOthersUf(int age, String uf, Status statusExpected) {
        var status = rulesLicense.validate(age, uf);
        assertEquals(statusExpected, status);
    }

    @ParameterizedTest
    @CsvSource({"17, AM, DENY",
            "18, AM, DENY",
            "19, AM, ALLOW"})
    void mustValidateUfAm(int age, String uf, Status statusExpected) {
        var status = rulesLicense.validate(age, uf);
        assertEquals(statusExpected, status);
    }

    @Test
    void mustReturnException() {
        Throwable exception = assertThrows(
                ResponseStatusException.class, () -> {
                    rulesLicense.validate(55, "XXX");
                });

        assertThat(exception.getMessage(), containsString("UF dont is valid"));
        assertThat(exception.getMessage(), containsString("404"));
    }

}
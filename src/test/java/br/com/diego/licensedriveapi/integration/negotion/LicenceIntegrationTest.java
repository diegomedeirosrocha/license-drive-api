package br.com.diego.licensedriveapi.integration.negotion;

import br.com.diego.licensedriveapi.LicenseDriveApiApplication;
import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.enums.Status;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@ActiveProfiles("integration")
@SpringBootTest(classes = LicenseDriveApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LicenceIntegrationTest {

    private static final String URL_API = "http://localhost:8081/api/v1/license";

    @Test
    void mustDennyCreateLicenceSP_CLEACOMMENTS() {
        var licenseDto = LicenseDto.builder()
                .name("treinamento")
                .age(10)
                .uf("SP")
                .build();

        RestAssured.
                given().
                    contentType("application/json").
                    body(licenseDto).
                when().
                    post(URL_API).
                then()
                    .assertThat().
                        statusCode(201).
                        body("name", Matchers.equalTo("treinamento")).
                        body("age", Matchers.equalTo(10)).
                        body("status", Matchers.equalTo("DENY"));
    }





    @Test
    void mustDennyCreateLicenceSP() {
        var licenseDto = crateLicenseDTOValid(10, "SP");
        log.info("massa -> ", licenseDto.toString());

        RestAssured.
                given(). //PASSAR PARAMETROS
                    contentType("application/json").
                    body(licenseDto).
                when().
                    post(URL_API). //METODO POST, URL DA APLICACAO
                then()
                    .assertThat().      //>>> ASSERT
                        statusCode(201). //status http code response
                        body("name", Matchers.equalTo("treinamento")).      //campos retornados
                        body("age", Matchers.equalTo(10)).
                        body("status", Matchers.equalTo("DENY"));
    }

    @Test
    void mustAllowCreateLicenceSP() {
        var licenseDto = crateLicenseDTOValid(25, "SP");
        log.info("massa -> ", licenseDto.toString());

        //request api
        RestAssured.
                given().
                    contentType("application/json").
                        body(licenseDto).
                when().
                    post(URL_API).
                then()
                    .assertThat().
                        statusCode(201).
                        body("name", Matchers.equalTo("treinamento")).
                        body("age", Matchers.equalTo(25)).
                        body("status", Matchers.equalTo("ALLOW"));
    }


    //TODO proximo encontro
    /*
        PROXIMA AULA, SO FAZER TODOS ESSES TESTES E MINHA API ESTA OK?

        quais testes devemos criar nas camadas?
          - UNIDADE
          - INTEGRACAO

        o que se preocupar em cada camada

        qual vantagem de colocar teste em cada camada

        com os testes nos ajudam em subidas???
     */
    @ParameterizedTest
    @CsvSource({
            "15, SP, DENY",
            "15, RJ, DENY",
            "16, SP, ALLOW_IF_PERMISSION_FATHER",
            "17, SP, ALLOW_IF_PERMISSION_FATHER",
            "18, SP, ALLOW",
            "19, SP, ALLOW",
            "50, SP, ALLOW",
            "17, AM, DENY",
            "18, AM, DENY",
            "19, AM, ALLOW"})
    @DisplayName("mustValidateOthersUf")
    void mustCreateLicenceParametrized(int age, String uf, String statusExpected) {
        var licenseDto = crateLicenseDTOValid(age, uf);
        log.info("massa -> ", licenseDto.toString());

        //request api
        RestAssured.
                given().
                    contentType("application/json").
                    body(licenseDto).
                when().
                    post(URL_API).
                then()
                    .assertThat().
                        statusCode(201).
                        body("name", Matchers.equalTo("treinamento")).
                        body("age", Matchers.equalTo(age)).
                        body("uf", Matchers.equalTo(uf)).
                        body("status", Matchers.equalTo(statusExpected));
    }


    //REFACTORY move to lib external
    private LicenseDto crateLicenseDTOValid(int age, String uf) {
        return LicenseDto.builder()
                .name("treinamento")
                .age(age)
                .uf(uf)
                .build();
    }

}

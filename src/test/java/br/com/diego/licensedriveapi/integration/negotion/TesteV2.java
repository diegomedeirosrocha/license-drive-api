package br.com.diego.licensedriveapi.integration.negotion;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.*;

public class TesteV2 {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
    }

}

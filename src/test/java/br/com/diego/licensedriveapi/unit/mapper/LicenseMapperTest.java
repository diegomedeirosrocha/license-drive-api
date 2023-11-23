package br.com.diego.licensedriveapi.unit.mapper;

import br.com.diego.licensedriveapi.commons.Commons;
import br.com.diego.licensedriveapi.mapper.LicenseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class LicenseMapperTest {

    private final LicenseMapper mapper = new LicenseMapper();

    @Test
    void mustConvertDtoToEntityAndSetStatus() {
        var licenseDtoValid = Commons.createLicenseDtoValid();
        var license = mapper.dtoToEntityAndSetStatus(licenseDtoValid, "ALLOW");

        assertThat(license.getAge()).isEqualTo(10);
        assertThat(license.getName()).isEqualTo("diego");
        assertThat(license.getSecondName()).isEqualTo("fulano");
        assertThat(license.getStatus().name()).isEqualTo("ALLOW");
        assertThat(license.getUf().sigla()).isEqualTo("SP");
    }

    @Test
    void mustReturnNullConvertDtoToEntityAndSetStatus() {
        var license = mapper.dtoToEntityAndSetStatus(null, "ALLOW");
        Assertions.assertNull(license);
    }

    @Test
    void mustConvertDtoResponseToEntity() {
        var licenseResponseDtoValid = Commons.createLicenseResponseDtoValid();
        var license = mapper.dtoResponseToEntity(licenseResponseDtoValid);

        assertThat(license.getAge()).isEqualTo(15);
        assertThat(license.getId()).isEqualTo(8);
        assertThat(license.getName()).isEqualTo("Manuel");
        assertThat(license.getSecondName()).isEqualTo("A");
        assertThat(license.getStatus().name()).isEqualTo("DENY");
        assertThat(license.getUf().sigla()).isEqualTo("RJ");
    }

    @Test
    void mustReturnNullConvertDtoResponseToEntity() {
        var license = mapper.dtoResponseToEntity(null);
        Assertions.assertNull(license);
    }

    @Test
    void mustConvertEntityToDtoResponse() {
        var licenseValid = Commons.createLicenseValid();
        var license = mapper.entityToDtoResponse(licenseValid);

        assertThat(license.getId()).isEqualTo(1);
        assertThat(license.getAge()).isEqualTo(16);
        assertThat(license.getName()).isEqualTo("Yasmin");
        assertThat(license.getSecondName()).isEqualTo("Test1");
        assertThat(license.getUf()).isEqualTo("AM");
        assertThat(license.getStatus()).isEqualTo("ALLOW_IF_PERMISSION_FATHER");
    }

    @Test
    void mustReturnNullConvertEntityToDtoResponse() {
        var license = mapper.entityToDtoResponse(null);
        Assertions.assertNull(license);
    }

    @Test
    void mustReturnListInConvertEntityToDtoResponse() {
        var licenseList = Commons.createListOfLicenses();

        var licenseResponseDtoList = mapper.entityListToDtosResponse(licenseList);

        assertThat(licenseResponseDtoList)
                .isNotEmpty()
                .extracting("id", "age", "name", "secondName", "uf", "status")
                .containsExactly(
                        tuple(1, 16, "Yasmin", "Test1", "AM", "ALLOW_IF_PERMISSION_FATHER"),
                        tuple(2, 50, "Fulano", null,  "AC", "ALLOW"))
                .hasSize(2);
    }
}

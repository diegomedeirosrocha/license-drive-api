package br.com.diego.licensedriveapi.unit.service;

import br.com.diego.licensedriveapi.commons.Commons;
import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import br.com.diego.licensedriveapi.mapper.LicenseMapper;
import br.com.diego.licensedriveapi.repository.LicenseRepository;
import br.com.diego.licensedriveapi.service.LicenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class LicenseServiceTest {

    @Autowired
    private LicenseService service;

    @MockBean
    private LicenseRepository repository;

    @Autowired
    private LicenseMapper mapper;

    @Test
    void mustReturnAllowInPLus18AgeOtherStates() {
        LicenseDto licenseDto = Commons.createLicenseDtoValid();

        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(1);

        Mockito.when(repository.save(any())).thenReturn(license);

        var create = service.create(licenseDto);

        assertThat(create.getId()).isEqualTo(1);
        assertThat(create.getName()).isEqualTo(license.getName());
        assertThat(create.getStatus()).isEqualTo(license.getStatus());
        assertThat(create.getAge()).isEqualTo(license.getAge());
    }

    @Test
    void mustReturnListLicencesInGetAll() {
        var licenseList = Commons.createListOfLicenses();

        Mockito.when(repository.findAll()).thenReturn(licenseList);

        var serviceGetAll = service.getAll();
        assertThat(serviceGetAll)
                .isNotEmpty()
                .extracting("id", "age", "name", "secondName", "uf", "status")
                .containsExactly(
                        tuple(1, 16, "Yasmin", "Test1", UF.AMAZONAS, Status.ALLOW_IF_PERMISSION_FATHER),
                        tuple(2, 50, "Fulano", null,  UF.ACRE, Status.ALLOW))
                .hasSize(2);
    }

    @Test
    void mustReturnNullInGetAll() {
        Mockito.when(repository.findAll()).thenReturn(null);

        var serviceGetAll = service.getAll();
        assertThat(serviceGetAll)
                .isNullOrEmpty();
    }

    @Test
    void mustReturnLicenceInGetId() {
        LicenseDto licenseDto = LicenseDto.builder()
                .age(25)
                .uf("RJ")
                .name("fulano1")
                .build();
        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(license));

        var optionalLicense = service.getId(1L);

        assertTrue(optionalLicense.isPresent());
        assertThat(optionalLicense.get().getId()).isEqualTo(1);
        assertThat(optionalLicense.get().getName()).isEqualTo("fulano1");
        assertThat(optionalLicense.get().getUf()).isEqualTo(UF.RIO_DE_JANEIRO);
        assertThat(optionalLicense.get().getStatus()).isEqualTo(Status.ALLOW);
        assertThat(optionalLicense.get().getAge()).isEqualTo(25);
        assertThat(optionalLicense.get().getSecondName()).isNull();
    }

}

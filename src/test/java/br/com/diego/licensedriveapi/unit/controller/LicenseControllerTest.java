package br.com.diego.licensedriveapi.unit.controller;

import br.com.diego.licensedriveapi.commons.Commons;
import br.com.diego.licensedriveapi.entity.License;
import br.com.diego.licensedriveapi.mapper.LicenseMapper;
import br.com.diego.licensedriveapi.service.LicenseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LicenseControllerTest {

    private final static String PATH_URL = "/api/v1/license";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService service;

    @Autowired
    private LicenseMapper mapper;

    @Test
    void mustReturn201InCreate() throws Exception {
        var licenseDto = Commons.createLicenseDtoValid();

        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(99);

        BDDMockito.given(service.create(licenseDto)).willReturn(license);

        mockMvc.perform(post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Commons.objectToJson(licenseDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("99"))
                .andExpect(jsonPath("$.name").value("diego"))
                .andExpect(jsonPath("$.secondName").value("fulano"))
                .andExpect(jsonPath("$.status").value("ALLOW"))
                .andExpect(jsonPath("$.uf").value("SP"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "uf"})
    void mustReturn404InCreate(String field) throws Exception {
        var licenseDto = Commons.createLicenseDtoValid();

        switch (field) {
            case "name":
                licenseDto.setName(null);
                break;
            case "uf":
                licenseDto.setUf(null);
                break;
            default:
                Assertions.fail("field dont parametrized");
        }

        mockMvc.perform(post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Commons.objectToJson(licenseDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void mustReturn200InGetId() throws Exception {
        var licenseDto = Commons.createLicenseDtoValid();

        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(1);

        var optionalLicense = Optional.of(license);

        BDDMockito.given(service.getId(1L)).willReturn(optionalLicense);

        mockMvc.perform(get(PATH_URL + "/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("diego"))
                .andExpect(jsonPath("$.secondName").value("fulano"))
                .andExpect(jsonPath("$.status").value("ALLOW"))
                .andExpect(jsonPath("$.uf").value("SP"));
    }


    @Test
    void mustReturn204InGetId() throws Exception {
        var licenseDto = Commons.createLicenseDtoValid();

        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(1);

        Optional<License> optionalLicenseEmpty = Optional.empty();

        BDDMockito.given(service.getId(1L)).willReturn(optionalLicenseEmpty);

        mockMvc.perform(get(PATH_URL + "/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void mustReturn200InGetAll() throws Exception {
        var licenseList = Commons.createListOfLicenses();

        BDDMockito.given(service.getAll()).willReturn(licenseList);

        mockMvc.perform(get(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].id").value("1"))
                .andExpect(jsonPath("[0].age").value("16"))
                .andExpect(jsonPath("[0].name").value("Yasmin"))
                .andExpect(jsonPath("[0].secondName").value("Test1"))
                .andExpect(jsonPath("[0].status").value("ALLOW_IF_PERMISSION_FATHER"))
                .andExpect(jsonPath("[0].uf").value("AM"))

                .andExpect(jsonPath("[1].id").value("2"))
                .andExpect(jsonPath("[1].age").value("50"))
                .andExpect(jsonPath("[1].name").value("Fulano"))
                .andExpect(jsonPath("[1].status").value("ALLOW"))
                .andExpect(jsonPath("[1].uf").value("AC"));
    }

    @Test
    void mustReturn200InUpdate() throws Exception {
        var licenseResponseDto = Commons.createLicenseResponseDtoValid();
        licenseResponseDto.setId( 96 );

        var license = mapper.dtoResponseToEntity(licenseResponseDto);
        license.setId(96);

        BDDMockito.given(service.getId(96L)).willReturn(Optional.of(license));
        BDDMockito.given(service.update(licenseResponseDto)).willReturn(license);

        mockMvc.perform(put(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Commons.objectToJson(licenseResponseDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("96"))
                .andExpect(jsonPath("$.age").value("15"))
                .andExpect(jsonPath("$.name").value("Manuel"))
                .andExpect(jsonPath("$.secondName").value("A"))
                .andExpect(jsonPath("$.status").value("DENY"))
                .andExpect(jsonPath("$.uf").value("RJ"));
    }

    @Test
    void mustReturn404InUpdate() throws Exception {
        var licenseResponseDto = Commons.createLicenseResponseDtoValid();
        licenseResponseDto.setId(96);

        var license = mapper.dtoResponseToEntity(licenseResponseDto);
        license.setId(96);

        BDDMockito.given(service.getId(96L)).willReturn(Optional.empty());
        BDDMockito.given(service.update(licenseResponseDto)).willReturn(license);

        mockMvc.perform(put(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Commons.objectToJson(licenseResponseDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void mustReturn200InDelete() throws Exception {
        var licenseDto = Commons.createLicenseDtoValid();

        var license = mapper.dtoToEntityAndSetStatus(licenseDto, "ALLOW");
        license.setId(99);

        BDDMockito.given(service.getId(99L)).willReturn(Optional.of(license));

        mockMvc.perform(delete(PATH_URL + "/" + "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void mustReturn404InDelete() throws Exception {
        BDDMockito.given(service.getId(99L)).willReturn(Optional.empty());

        mockMvc.perform(delete(PATH_URL + "/" + "99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
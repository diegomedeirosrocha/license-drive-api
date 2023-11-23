package br.com.diego.licensedriveapi.commons;

import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.dto.LicenseResponseDto;
import br.com.diego.licensedriveapi.entity.License;
import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Commons {
    @SneakyThrows
    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static LicenseResponseDto convertLicenseDtoToLicenseDtoResponse(License license) {
        LicenseResponseDto licenseResponseDto = new LicenseResponseDto();
        BeanUtils.copyProperties(license, licenseResponseDto);

        licenseResponseDto.setStatus( String.valueOf( license.getStatus() != null ? license.getStatus() : null ) );
        licenseResponseDto.setUf( String.valueOf( license.getUf() != null ? license.getUf().sigla() : null ) );

        return licenseResponseDto;
    }

    public static License convertDtoToEntity(LicenseDto bookDto){
        License licenseEntity = new License();
        BeanUtils.copyProperties(bookDto, licenseEntity);
        licenseEntity.setUf( UF.fromSigla( bookDto.getUf() ) );
        return licenseEntity;
    }

    public static LicenseDto createLicenseDtoValid() {
        return LicenseDto.builder()
                .age(10)
                .uf("SP")
                .name("diego")
                .secondName("fulano")
                .build();
    }

    public static LicenseResponseDto createLicenseResponseDtoValid() {
        return LicenseResponseDto.builder()
                .age(15)
                .id(8)
                .uf("RJ")
                .name("Manuel")
                .secondName("A")
                .status(Status.DENY.name())
                .build();
    }

    public static License createLicenseValid() {
        return License.builder()
                .id(1)
                .age(16)
                .name("Yasmin")
                .secondName("Test1")
                .uf(UF.AMAZONAS)
                .status(Status.ALLOW_IF_PERMISSION_FATHER)
                .build();
    }

    public static License createLicenseValid2() {
        return License.builder()
                .id(2)
                .age(50)
                .name("Fulano")
                .uf(UF.ACRE)
                .status(Status.ALLOW)
                .build();
    }

    public static List<License> createListOfLicenses() {

        List<License> licenses = new ArrayList<>();
        licenses.add(createLicenseValid());
        licenses.add(createLicenseValid2());

        return licenses;
    }
}
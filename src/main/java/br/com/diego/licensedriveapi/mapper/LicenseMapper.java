package br.com.diego.licensedriveapi.mapper;

import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.dto.LicenseResponseDto;
import br.com.diego.licensedriveapi.entity.License;
import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LicenseMapper {

    public License dtoToEntityAndSetStatus(LicenseDto dto, String status) {
        if (dto == null) {
            return null;
        } else {
            License license = new License();
            BeanUtils.copyProperties(dto, license);

            license.setStatus(Status.valueOf(status));
            license.setUf(UF.fromSigla(dto.getUf()));

            return license;
        }
    }

    public License dtoResponseToEntity(LicenseResponseDto licenseResponseDto) {
        if (licenseResponseDto == null) {
            return null;
        } else {
            License license = new License();
            BeanUtils.copyProperties(licenseResponseDto, license);

            license.setStatus(Status.valueOf(licenseResponseDto.getStatus()));
            license.setUf(UF.fromSigla(licenseResponseDto.getUf()));

            return license;
        }
    }

    public LicenseResponseDto entityToDtoResponse(License license) {
        if (license == null) {
            return null;
        } else {
            LicenseResponseDto licenseResponseDto = new LicenseResponseDto();
            BeanUtils.copyProperties(license, licenseResponseDto);

            licenseResponseDto.setStatus(String.valueOf(license.getStatus() != null ? license.getStatus() : null));
            licenseResponseDto.setUf(String.valueOf(license.getUf() != null ? license.getUf().sigla() : null));

            return licenseResponseDto;
        }
    }

    public List<LicenseResponseDto> entityListToDtosResponse(List<License> licenses) {
        return licenses.stream()
                .map(this::entityToDtoResponse)
                .collect(Collectors.toList());
    }

}
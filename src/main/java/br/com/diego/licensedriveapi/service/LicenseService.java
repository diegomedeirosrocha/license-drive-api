package br.com.diego.licensedriveapi.service;

import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.dto.LicenseResponseDto;
import br.com.diego.licensedriveapi.entity.License;
import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import br.com.diego.licensedriveapi.mapper.LicenseMapper;
import br.com.diego.licensedriveapi.repository.LicenseRepository;
import br.com.diego.licensedriveapi.utils.RulesLicense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository repository;

    @Autowired
    private RulesLicense rulesLicense;

    @Autowired
    private LicenseMapper mapper;

    @Transactional
    public License create(LicenseDto licenseDto) {
        var status = rulesLicense.validate(licenseDto.getAge(), licenseDto.getUf());
        var entity = mapper.dtoToEntityAndSetStatus(licenseDto, status.name());

        return repository.save(entity);
    }

    public List<License> getAll() {
        return repository.findAll();
    }

    public Optional<License> getId(Long id) {
        return repository.findById(Math.toIntExact(id));
    }

    public void deleteId(Integer id) {
        repository.deleteById(id);
    }

    public License update(LicenseResponseDto licenseResponseDto) {
        validStatusAndUFParseToEnum(licenseResponseDto);

        if (!validStatusOfLicenseResponse(licenseResponseDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status receive invalid to age");
        }

        var license = mapper.dtoResponseToEntity(licenseResponseDto);

        log.info("update id: {} ", licenseResponseDto.getId());
        return repository.saveAndFlush(license);
    }

    private void validStatusAndUFParseToEnum( LicenseResponseDto licenseResponseDto) {
        Status.isValid(licenseResponseDto.getStatus());
        UF.fromSigla(licenseResponseDto.getUf());
    }

    private boolean validStatusOfLicenseResponse(LicenseResponseDto licenseResponseDto) {
        var status = rulesLicense.validate(licenseResponseDto.getAge(), licenseResponseDto.getUf());
        return status.toString().equals(licenseResponseDto.getStatus());
    }

}
package br.com.diego.licensedriveapi.controller;

import br.com.diego.licensedriveapi.dto.LicenseDto;
import br.com.diego.licensedriveapi.dto.LicenseResponseDto;
import br.com.diego.licensedriveapi.dto.TesteDto;
import br.com.diego.licensedriveapi.mapper.LicenseMapper;
import br.com.diego.licensedriveapi.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/license")
@RestController()
@Slf4j
public class LicenseController {

    @Autowired
    private LicenseMapper mapper;

    @Autowired
    private LicenseService licenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create licence drive")
    public LicenseResponseDto create(@RequestBody @Valid LicenseDto licenseDto) {
        log.info("save licence {} ", licenseDto);

        var createLicense = licenseService.create(licenseDto);
        return mapper.entityToDtoResponse(createLicense);
    }

    @GetMapping
    @Operation(summary = "Get all licenses")
    public List<LicenseResponseDto> getAll() {
        return mapper.entityListToDtosResponse(licenseService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get license by id")
    public LicenseResponseDto get(@PathVariable long id) {
        return licenseService.getId(id)
                .map(mapper::entityToDtoResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item dont found"));
    }

    @PutMapping()
    @Operation(summary = "Update license drive")
    public LicenseResponseDto update(@RequestBody @Valid LicenseResponseDto licenseResponseDto) {
        return licenseService.getId(Long.valueOf(licenseResponseDto.getId()))
                .map(license -> {
                    license = licenseService.update(licenseResponseDto);
                    return mapper.entityToDtoResponse(license);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item dont found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get license by id")
    public void delete(@PathVariable("id") Long id) {
        var license = licenseService.getId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item dont found"));

        licenseService.deleteId(license.getId());
    }
}

package br.com.diego.licensedriveapi.controller;

import br.com.diego.licensedriveapi.dto.TesteDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/license/teste") //TODO apenas para demostracao swagger
@RestController()
@Slf4j
public class TesteController {

    private List<TesteDto> testeDtoList = new ArrayList<>();
    int counter = 0;

    @PostMapping("/teste")
    @ResponseStatus( HttpStatus.CREATED)
    @Operation(summary = "Create teste")
    public TesteDto createTest( @RequestBody @Valid TesteDto testeDto) {
        log.info("save teste {} ", testeDto);

        counter++;
        testeDto.setId( counter );

        testeDtoList.add( testeDto );
        return testeDto;
    }

    @GetMapping("/teste")
    @Operation(summary = "Get all itens - diego111")
    public List<TesteDto> getAllTest() {
        return testeDtoList;
    }

}

package br.com.diego.licensedriveapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TesteDto {

    @NotEmpty
    @Size( min = 2 )
    @NotNull( message = "dont allow name null" )
    private String name;

    private String idade;

    private int id;

}
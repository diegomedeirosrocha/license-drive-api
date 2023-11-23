package br.com.diego.licensedriveapi.dto;

import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
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
public class LicenseResponseDto {

    Integer id;

    @NotEmpty
    @Size(min = 2)
    @NotNull(message = "dont allow name null")
    private String name;

    private String secondName;

    @NotNull
    private int age;

    @NotEmpty
    private String uf;

    @NotEmpty
    private String status;
}

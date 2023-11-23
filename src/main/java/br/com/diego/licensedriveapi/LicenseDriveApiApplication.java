package br.com.diego.licensedriveapi;

import br.com.diego.licensedriveapi.entity.License;
import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import br.com.diego.licensedriveapi.repository.LicenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LicenseDriveApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseDriveApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(LicenseRepository licenseRepository) {
        return args -> {

            licenseRepository.save(
                    License.builder()
                            .id(1)
                            .name("Antonio")
                            .secondName("andrade")
                            .age(10)
                            .status(Status.DENY)
                            .uf(UF.SAO_PAULO)
                            .build()
            );

            licenseRepository.save(
                    License.builder()
                            .id(2)
                            .name("Karina")
                            .age(70)
                            .status(Status.ALLOW)
                            .uf(UF.RIO_DE_JANEIRO)
                            .build()
            );
        };
    }

}

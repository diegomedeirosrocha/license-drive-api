package br.com.diego.licensedriveapi.entity;

import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "licence")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private String secondName;

    private int age;

    private UF uf;

    @Enumerated(EnumType.STRING)
    private Status status;
}

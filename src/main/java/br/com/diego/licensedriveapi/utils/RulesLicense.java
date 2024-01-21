package br.com.diego.licensedriveapi.utils;

import br.com.diego.licensedriveapi.enums.Status;
import br.com.diego.licensedriveapi.enums.UF;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RulesLicense {

    public Status validate(int age, String uf ) {
        Status status = null;

        validIfUfParseToEnum( uf );

        if( uf.equals( UF.AMAZONAS.sigla() ) ) {
            status = rulesUfAM( age );
        } else {
            status = rulesOthersUf( age );
        }
        return status;
    }

    private Status rulesUfAM( int age ) {
        Status status = null;
        if( age <= 18 ) {
            status = Status.DENY;
        } else {
            status = Status.ALLOW;
        }
        return status;
    }

    private Status rulesOthersUf( int age ) {
        Status status = null;
        if( age < 16 ) {
            status = Status.DENY;
        } else if( age < 18 ) {
            status = Status.ALLOW_IF_PERMISSION_FATHER;
        } else {
            status = Status.ALLOW;
        }
        return status;
    }

    private void validIfUfParseToEnum(String uf ) {
        try {
            UF.fromSigla( uf );
        } catch ( IllegalArgumentException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "UF dont is valid");
        }
    }
}

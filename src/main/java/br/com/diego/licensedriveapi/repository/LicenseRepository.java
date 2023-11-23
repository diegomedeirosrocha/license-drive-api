package br.com.diego.licensedriveapi.repository;

import br.com.diego.licensedriveapi.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
}

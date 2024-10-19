package com.core.principal.repository;

import com.core.principal.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    @Query(value = "SELECT * FROM tbl_config c WHERE c.config_code = ?1", nativeQuery = true)
    Config findByConfigCode(String configCode);
}

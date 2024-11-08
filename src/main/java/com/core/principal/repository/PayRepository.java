package com.core.principal.repository;

import com.core.principal.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayRepository extends JpaRepository<Pay, Long> {
    @Query(value = "SELECT * FROM tbl_pay WHERE id_contract = ?1", nativeQuery = true)
    List<Pay> findByIdContract(Long id);
}

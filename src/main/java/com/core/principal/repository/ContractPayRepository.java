package com.core.principal.repository;

import com.core.principal.entity.ContractPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractPayRepository extends JpaRepository<ContractPay, Long> {
    @Query(value = "SELECT * FROM tbl_pay_contract WHERE id_contract = ?1", nativeQuery = true)
    List<ContractPay> findByIdContract(Long id);
}

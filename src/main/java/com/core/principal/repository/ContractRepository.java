package com.core.principal.repository;

import com.core.principal.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "SELECT * FROM tbl_contract c WHERE c.id_customer = ?1", nativeQuery = true)
    ArrayList<Contract> findByCustomer(Long id);
    @Query(value = "SELECT * FROM tbl_contract c WHERE c.id = ?1", nativeQuery = true)
    Contract findByIdContract(Long id);
}

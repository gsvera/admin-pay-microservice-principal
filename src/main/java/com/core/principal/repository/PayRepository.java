package com.core.principal.repository;

import com.core.principal.entity.ContractPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PayRepository extends JpaRepository<ContractPay,Long> {
    @Query(value = "SELECT * FROM tbl_pay_contract p WHERE p.id_contract = ?1 AND p.status_pay = 0 ORDER BY pay_number ASC", nativeQuery = true)
    ArrayList<ContractPay> findByIdContractPaysPending(int idContract);
}

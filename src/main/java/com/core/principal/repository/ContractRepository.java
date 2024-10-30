package com.core.principal.repository;

import com.core.principal.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "SELECT * FROM tbl_contract c WHERE c.id_customer = ?1", nativeQuery = true)
    ArrayList<Contract> findByCustomer(Long id);
    @Query(value = "SELECT * FROM tbl_contract c WHERE c.id = ?1", nativeQuery = true)
    Contract findByIdContract(Long id);
    @Query(value = "" +
            "SELECT " +
            "c.id, c.id_customer, c.folio, c.status_contract, cu.first_name, cu.last_name, p.pay_did_date, p.pay_amount " +
            "FROM tbl_contract c " +
            "LEFT JOIN tbl_customer cu ON c.id_customer = cu.id " +
            "LEFT JOIN (SELECT id_contract, pay_did_date, pay_amount FROM tbl_pay_contract WHERE pay_amount > 0 ORDER BY id DESC LIMIT 1) p ON c.id = p.id_contract " +
            "WHERE c.status_contract NOT IN (?1) AND (c.folio LIKE %?2% OR cu.first_name LIKE %?2% OR cu.last_name LIKE %?2%) GROUP BY c.id;", nativeQuery = true)
    List<Object[]> findByCustomerContract(String status, String word);
//    @Query(value = "SELECT c.* FROM tbl_contract c LEFT JOIN tbl_pay_contract p ON c.id = p.id_contract WHERE c.id = ?1", nativeQuery = true)
    @Query(value = "SELECT c FROM Contract c LEFT JOIN FETCH c.pays WHERE c.id = ?1")
    Contract findByIdWithPays(@Param("id") Long id);
}

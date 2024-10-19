package com.core.principal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.core.principal.entity.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM tbl_customer c WHERE c.delete_register = false AND c.code_company = ?1 AND (?2 IS NULL OR c.first_name LIKE %?2% OR c.last_name LIKE %?2% OR c.email LIKE %?2%)", nativeQuery = true)
    Page<Customer> findAll(String codeCompany, String word, Pageable pageable);
    @Query(value = "SELECT * FROM tbl_customer c WHERE c.delete_register = false AND (c.first_name LIKE %?1% OR c.last_name LIKE %?1%)", nativeQuery = true)
    List<Customer> findAllData(String value);
    Optional<Customer> findById(long id);
}

package com.core.principal.repository;

import com.core.principal.entity.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.ArrayList;

public interface CustomerNoteRepository extends JpaRepository<CustomerNote, Long> {
    @Query(value = "SELECT * FROM tbl_customer_note WHERE id_customer = ?1", nativeQuery = true)
    ArrayList<CustomerNote> findByIdCustomer(Long id);
}

package com.core.principal.entity;

import com.core.principal.dto.CustomerNoteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_customer_note")
public class CustomerNote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_customer")
    private Long idCustomer;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "name_user_created")
    private String nameUserCreated;
    private String note;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    public CustomerNote (CustomerNoteDTO customerNoteDTO) {
        this.idCustomer = customerNoteDTO.getIdCustomer();
        this.idUser = customerNoteDTO.getIdUser();
        this.note = customerNoteDTO.getNote();
        this.createdAt = customerNoteDTO.getCreatedAt();
        this.nameUserCreated = customerNoteDTO.getNameUserCreated();
    }

    public CustomerNote(Long idCustomer, String idUser, String nameUserCreated, String note, Timestamp createdAt) {
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.nameUserCreated = nameUserCreated;
        this.note = note;
        this.createdAt = createdAt;
    }
}

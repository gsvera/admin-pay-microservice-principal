package com.core.principal.dto;

import com.core.principal.entity.CustomerNote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CustomerNoteDTO {
    private Long id;
    private Long idCustomer;
    private String idUser;
    private String nameUserCreated;
    private String note;
    private Timestamp createdAt;
    public CustomerNoteDTO(CustomerNote customerNote) {
        this.id = customerNote.getId();
        this.idCustomer = customerNote.getIdCustomer();
        this.idUser = customerNote.getIdUser();
        this.nameUserCreated = customerNote.getNameUserCreated();
        this.note = customerNote.getNote();
        this.createdAt = customerNote.getCreatedAt();
    }
}

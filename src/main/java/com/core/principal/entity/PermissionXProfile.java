package com.core.principal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permission_x_profile")
public class PermissionXProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(name = "id_profile")
    private int idProfile;
    @Column(name = "id_permission")
    private int idPermission;
}

package com.core.principal.dto;

import com.core.principal.entity.PermissionXProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PermissionXProfileDTO {
    public int idPermission;

    public PermissionXProfileDTO(PermissionXProfile permissionXProfile) {
        this.idPermission = permissionXProfile.getIdPermission();
    }
}

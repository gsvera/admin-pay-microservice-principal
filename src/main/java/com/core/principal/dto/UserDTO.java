package com.core.principal.dto;

import com.core.principal.dto.PermissionXProfileDTO;
import com.core.principal.entity.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String codeCompany;
    public int idProfile;
    public List<PermissionXProfileDTO> listPermissions;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.codeCompany = user.getCodeCompany();
    }
}

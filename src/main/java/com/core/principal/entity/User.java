package com.core.principal.entity;

import com.core.principal.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User implements UserDetails {
    @Id
    private String id;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;
    private String token;
    @Column(name = "code_company")
    private String codeCompany;
    @Column(name = "id_profile")
    private int idProfile;

    public User (UserDTO userDTO) {
        this.id = userDTO.getId();
        this.email = userDTO.getEmail();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.password = userDTO.getPassword();
        this.idProfile = userDTO.getIdProfile();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // AQUI ENTRARIA LA LOGICA POR PERFILES (ROLES)
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

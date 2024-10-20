package com.core.principal.repository;

import com.core.principal.entity.PermissionXProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface PermissionXProfileRepository extends JpaRepository<PermissionXProfile, Long> {
    @Query(value = "SELECT * FROM tbl_permission_x_profile pp WHERE pp.id_profile = ?1", nativeQuery = true)
    ArrayList<PermissionXProfile> findByIdProfile(int idProfile);
}

package com.ftn.sbnz.tim5.service.repository;

import com.ftn.sbnz.tim5.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String roleName);
}

package com.bankingsystemapplication.repository;

import com.bankingsystemapplication.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findRoleByName(String role_admin);
}

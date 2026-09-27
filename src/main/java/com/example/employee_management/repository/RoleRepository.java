package com.example.employee_management.repository;

import com.example.employee_management.entity.Role;
import com.example.employee_management.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameRole(Roles nameRole);
}

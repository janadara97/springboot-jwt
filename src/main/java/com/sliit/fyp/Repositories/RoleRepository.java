package com.sliit.fyp.Repositories;

import java.util.Optional;

import com.sliit.fyp.Models.Role;
import com.sliit.fyp.Models.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByRoleName(RoleName roleName);
}

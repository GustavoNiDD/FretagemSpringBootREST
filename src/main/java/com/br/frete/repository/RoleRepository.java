package com.br.frete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.frete.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);

}

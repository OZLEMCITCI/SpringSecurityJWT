package com.springjwt.repository;

import com.springjwt.model.Eroller;
import com.springjwt.model.KisiRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<KisiRole,Integer> {

    Optional<KisiRole> findByName(Eroller name);
}

package com.springjwt.repository;

import com.springjwt.model.Kisi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KisiRepository extends JpaRepository<Kisi,Integer> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Kisi> findByUsername(String username);
}

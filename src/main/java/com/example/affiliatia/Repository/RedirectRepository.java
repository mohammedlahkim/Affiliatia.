package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.Redirect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedirectRepository extends JpaRepository<Redirect, Long> {
    Optional<Redirect> findByFromPath(String fromPath);

}

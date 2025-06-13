package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.entity.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
}

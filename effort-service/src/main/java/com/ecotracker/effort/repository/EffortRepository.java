package com.ecotracker.effort.repository;

import com.ecotracker.effort.model.Effort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EffortRepository extends JpaRepository<Effort, Long> {

    List<Effort> findByUserId(Long userId);
}

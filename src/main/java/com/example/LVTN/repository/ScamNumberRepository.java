package com.example.LVTN.repository;

import com.example.LVTN.entity.ScamNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScamNumberRepository extends JpaRepository<ScamNumberEntity, Long> {
    Optional<ScamNumberEntity> findByPhone(String phone);
}

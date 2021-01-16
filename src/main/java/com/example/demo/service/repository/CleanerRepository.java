package com.example.demo.service.repository;

import com.example.demo.service.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CleanerRepository extends JpaRepository<Cleaner, UUID> {
}

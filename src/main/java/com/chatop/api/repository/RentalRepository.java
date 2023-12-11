package com.chatop.api.repository;

import com.chatop.api.models.Rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    public Optional<Rental> findById(Long id);
}

package com.chatop.api.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.models.Rental;
import com.chatop.api.services.RentalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/rentals")
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.findAll();
        return ResponseEntity.ok(rentals);
    }

    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @ModelAttribute Rental updatedRental) {
        Optional<Rental> rental = rentalService.getRentalById(id);

        if (rental.isPresent()) {
            Rental existingRental = rental.get();
            existingRental.setName(updatedRental.getName());
            existingRental.setSurface(updatedRental.getSurface());
            existingRental.setPrice(updatedRental.getPrice());
            existingRental.setDescription(updatedRental.getDescription());
            rentalService.save(existingRental);
            return ResponseEntity.ok(existingRental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

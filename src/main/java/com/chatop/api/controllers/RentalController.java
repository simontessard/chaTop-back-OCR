package com.chatop.api.controllers;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.models.Rental;
import com.chatop.api.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @Operation(summary = "Get a specific rental")
    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all rentals")
    @GetMapping("/api/rentals")
    public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
        List<Rental> rentals = rentalService.findAll();
        Map<String, List<Rental>> response = new HashMap<>();
        response.put("rentals", rentals);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a rental")
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @ModelAttribute Rental updatedRental) {
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

    @Operation(summary = "Created a new rental")
    @PostMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> createRental(@PathVariable Integer id, @RequestPart("rental") Rental newRental,
            @RequestPart("picture") MultipartFile picture) {
        newRental.setId(id);
        Rental savedRental = rentalService.createRental(newRental);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRental);
    }
}

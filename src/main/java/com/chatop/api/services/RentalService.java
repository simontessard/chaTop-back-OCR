package com.chatop.api.services;

import com.chatop.api.dto.RentalCreateDTO;
import com.chatop.api.models.Rental;
import com.chatop.api.repository.RentalRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.io.File;

import org.springframework.stereotype.Service;

/**
 * Service class for handling Rental operations.
 */
@Service
public class RentalService {
    /**
     * The repository used for interacting with rentals in the database.
     */
    private final RentalRepository rentalRepository;
    private final AmazonService amazonService;

    /**
     * Constructor for the RentalService class.
     *
     * @param rentalRepository The repository to use for interacting with rentals in
     *                         the database.
     */
    public RentalService(RentalRepository rentalRepository, AmazonService amazonService) {
        this.rentalRepository = rentalRepository;
        this.amazonService = amazonService;
    }

    /**
     * Retrieves all rentals from the database.
     *
     * @return A list of all rentals.
     */
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id The ID of the rental to retrieve.
     * @return An Optional containing the rental if found, or empty if not.
     */
    public Optional<Rental> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    /**
     * Updates a rental in the database.
     *
     * @param id            The ID of the rental to update.
     * @param updatedRental The rental object containing the updated data.
     * @return An Optional containing the updated rental if found, or empty if not.
     */
    public Optional<Rental> updateRental(Integer id, Rental updatedRental) {
        return rentalRepository.findById(id).map(rental -> {
            if (updatedRental.getName() != null) {
                rental.setName(updatedRental.getName());
            }
            if (updatedRental.getSurface() != null) {
                rental.setSurface(updatedRental.getSurface());
            }
            if (updatedRental.getPrice() != null) {
                rental.setPrice(updatedRental.getPrice());
            }
            if (updatedRental.getDescription() != null) {
                rental.setDescription(updatedRental.getDescription());
            }
            rental.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            return rentalRepository.save(rental);
        });
    }

    /**
     * Creates a new rental in the database.
     *
     * @param newRental The rental to create.
     * @return The created rental.
     */
    public Rental createRental(RentalCreateDTO rentalInfo) {
        Rental newRental = new Rental();
        newRental.setName(rentalInfo.getName());
        newRental.setSurface(rentalInfo.getSurface());
        newRental.setPrice(rentalInfo.getPrice());
        newRental.setDescription(rentalInfo.getDescription());
        newRental.setCreated_at(new Timestamp(System.currentTimeMillis()));
        newRental.setUpdated_at(newRental.getCreated_at());

        String pictureKey = rentalInfo.getPicture().getOriginalFilename();
        try {
            File tempFile = File.createTempFile("upload-", "");
            rentalInfo.getPicture().transferTo(tempFile);
            String pictureUrl = amazonService.putObject(pictureKey, tempFile.getAbsolutePath());

            newRental.setPicture(pictureUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload picture", e);
        }
        return rentalRepository.save(newRental);
    }

    /**
     * Saves a rental in the database.
     *
     * @param rental The rental to save.
     * @return The saved rental.
     */
    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }
}

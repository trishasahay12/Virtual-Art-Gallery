package main;

import dao.ArtworkDAO;
import dao.ArtworkDAOImpl;
import entity.Artwork;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ArtworkDAO artworkDAO = new ArtworkDAOImpl();

        // Add a new artwork
        // Artwork newArtwork = new Artwork("The Persistence of Memory", "Surrealist painting", LocalDate.of(1931, 4, 1), "Oil on canvas", "http://example.com/persistenceofmemory.jpg", 1);
        // artworkDAO.addArtwork(newArtwork);

        // Fetch and display the artwork
        Artwork fetchedArtwork = artworkDAO.getArtworkById(1);
        System.out.println("Fetched Artwork: " + fetchedArtwork.getTitle());

        // Update artwork
        // fetchedArtwork.setDescription("Updated Description");
        // artworkDAO.updateArtwork(fetchedArtwork);

        // Remove artwork
        // artworkDAO.removeArtwork(1);
    }
}
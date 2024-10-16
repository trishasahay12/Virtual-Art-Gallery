package dao;

import entity.Artwork;
import java.util.List;

public interface ArtworkDAO {
    // Add a new artwork
    boolean addArtwork(Artwork artwork);

    // Update existing artwork
    boolean updateArtwork(Artwork artwork);

    // Remove an artwork by ID
    boolean removeArtwork(int artworkID);

    // Get an artwork by ID
    Artwork getArtworkById(int artworkID);

    // Search artworks by a keyword (for title or description)
    List<Artwork> searchArtworks(String keyword);
}
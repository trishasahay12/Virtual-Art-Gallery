package dao;

import entity.Artwork;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDAOImpl implements ArtworkDAO {
    private Connection connection;

    // Constructor to initialize the connection
    public ArtworkDAOImpl() {
        this.connection = DBConnUtil.getConnection();
    }


    public int getNextArtworkId() {
        int nextId = 1; // Default to 1 if no artworks exist
        String query = "SELECT MAX(ArtworkID) AS maxId FROM Artwork";

        try (PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    @Override
    public boolean addArtwork(Artwork artwork) {
        int nextId = getNextArtworkId();
        String query = "INSERT INTO Artwork (ArtworkID, Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nextId);
            stmt.setString(2, artwork.getTitle());
            stmt.setString(3, artwork.getDescription());
            stmt.setDate(4, Date.valueOf(artwork.getCreationDate()));
            stmt.setString(5, artwork.getMedium());
            stmt.setString(6, artwork.getImageURL());
            stmt.setInt(7, artwork.getArtistID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateArtwork(Artwork artwork) {
        String query = "UPDATE Artwork SET Title = ?, Description = ?, CreationDate = ?, Medium = ?, ImageURL = ?, ArtistID = ? WHERE ArtworkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setDate(3, Date.valueOf(artwork.getCreationDate()));
            stmt.setString(4, artwork.getMedium());
            stmt.setString(5, artwork.getImageURL());
            stmt.setInt(6, artwork.getArtistID());
            stmt.setInt(7, artwork.getArtworkID());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeArtwork(int artworkID) {
        String query = "DELETE FROM Artwork WHERE ArtworkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Artwork getArtworkById(int artworkID) {
        String query = "SELECT * FROM Artwork WHERE ArtworkID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Artwork(
                        rs.getInt("ArtworkID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("CreationDate").toLocalDate(),
                        rs.getString("Medium"),
                        rs.getString("ImageURL"),
                        rs.getInt("ArtistID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Artwork> searchArtworks(String keyword) {
        String query = "SELECT * FROM Artwork WHERE Title LIKE ? OR Description LIKE ?";
        List<Artwork> artworks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Artwork artwork = new Artwork(
                        rs.getInt("ArtworkID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getDate("CreationDate").toLocalDate(),
                        rs.getString("Medium"),
                        rs.getString("ImageURL"),
                        rs.getInt("ArtistID")
                );
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }
}
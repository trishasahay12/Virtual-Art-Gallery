package main;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.myexceptions.*;
import util.DBConnUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        VirtualArtGalleryImpl galleryService = new VirtualArtGalleryImpl();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Virtual Art Gallery =====");
            System.out.println("1. Add Artwork");
            System.out.println("2. Update Artwork");
            System.out.println("3. Remove Artwork");
            System.out.println("4. Get Artwork by ID");
            System.out.println("5. Search Artworks");
            System.out.println("6. Add Artwork to User Favorites");
            System.out.println("7. Remove Artwork from User Favorites");
            System.out.println("8. Get User Favorite Artworks");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addArtwork(galleryService, scanner);
                    break;
                case 2:
                    updateArtwork(galleryService, scanner);
                    break;
                case 3:
                    removeArtwork(galleryService, scanner);
                    break;
                case 4:
                    getArtworkById(galleryService, scanner);
                    break;
                case 5:
                    searchArtworks(galleryService, scanner);
                    break;
                case 6:
                    addArtworkToFavorites(galleryService, scanner);
                    break;
                case 7:
                    removeArtworkFromFavorites(galleryService, scanner);
                    break;
                case 8:
                    getUserFavoriteArtworks(galleryService, scanner);
                    break;
                case 9:
                    running = false;
                    DBConnUtil.dbClose(); // Close database connection before exiting
                    System.out.println("Exiting the Virtual Art Gallery. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addArtwork(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Creation Date (yyyy-mm-dd): ");
        LocalDate creationDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Medium: ");
        String medium = scanner.nextLine();
        System.out.print("Enter Image URL: ");
        String imageURL = scanner.nextLine();
        System.out.print("Enter Artist ID: ");
        int artistID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Artwork newArtwork = new Artwork(0, title, description, creationDate, medium, imageURL, artistID);
        boolean success = service.addArtwork(newArtwork);
        System.out.println(success ? "Artwork added successfully." : "Failed to add artwork.");
    }

    private static void updateArtwork(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter Artwork ID to update: ");
        int artworkID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new Creation Date (yyyy-mm-dd): ");
        LocalDate creationDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter new Medium: ");
        String medium = scanner.nextLine();
        System.out.print("Enter new Image URL: ");
        String imageURL = scanner.nextLine();
        System.out.print("Enter new Artist ID: ");
        int artistID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Artwork updatedArtwork = new Artwork(artworkID, title, description, creationDate, medium, imageURL, artistID);
        boolean success = service.updateArtwork(updatedArtwork);
        System.out.println(success ? "Artwork updated successfully." : "Failed to update artwork.");
    }

    private static void removeArtwork(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter Artwork ID to remove: ");
        int artworkID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean success = service.removeArtwork(artworkID);
        System.out.println(success ? "Artwork removed successfully." : "Failed to remove artwork.");
    }

    private static void getArtworkById(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter Artwork ID: ");
        int artworkID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Artwork artwork = service.getArtworkById(artworkID);
            System.out.println("Artwork: " + artwork.getTitle() + ", " + artwork.getDescription());
        } catch (ArtWorkNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchArtworks(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();

        List<Artwork> artworks = service.searchArtworks(keyword);
        if (artworks.isEmpty()) {
            System.out.println("No artworks found matching the keyword \"" + keyword + "\".");
        } else {
            System.out.println("Search Results:");
            for (Artwork artwork : artworks) {
                System.out.println("ID: " + artwork.getArtworkID() + ", Title: " + artwork.getTitle());
            }
        }
    }

    private static void addArtworkToFavorites(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Artwork ID: ");
        int artworkId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean success = service.addArtworkToFavorite(userId, artworkId);
        System.out.println(success ? "Artwork added to favorites." : "Failed to add to favorites.");
    }

    private static void removeArtworkFromFavorites(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Artwork ID: ");
        int artworkId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean success = service.removeArtworkFromFavorite(userId, artworkId);
        System.out.println(success ? "Artwork removed from favorites." : "Failed to remove from favorites.");
    }

    private static void getUserFavoriteArtworks(VirtualArtGalleryImpl service, Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            List<Artwork> favorites = service.getUserFavoriteArtworks(userId);
            System.out.println("Favorite Artworks:");
            for (Artwork artwork : favorites) {
                System.out.println("ID: " + artwork.getArtworkID() + ", Title: " + artwork.getTitle());
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
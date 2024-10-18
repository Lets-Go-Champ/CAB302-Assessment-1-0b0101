package com.example.cab302assessment10b0101.Utility;

import javafx.scene.control.Alert;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Scraper class is responsible for scraping Google Books search results
 * and detailed information about a specific book.
 */
public class Scraper {

    public List<byte[]> imageBytesList = new ArrayList<>();  // Declare the imageBytesList

    /**
     * Scrapes the top 5 search results from Google Books based on the input query.
     *
     * @param query The search query entered by the user.
     * @return A list of book titles from the search results.
     * @throws IOException If there's an error during the scraping process.
     */
    public List<Map<String, String>> scrapeGoogleBooks(String query) throws IOException {
        String formattedQuery = query.trim().replace(" ", "+");
        String url = "https://books.google.com/books?q=" + formattedQuery;
        Document doc = Jsoup.connect(url).get();
        Elements searchResults = doc.select(".bHexk");

        List<Map<String, String>> books = new ArrayList<>();
        imageBytesList.clear();  // Clear the list before starting a new search
        int scrapeCount = 0;

        for (Element result : searchResults) {
            String bookTitle = result.select("h3").text();
            String bookUrl = result.select("a").attr("href");

            if (scrapeCount >= 5) {
                break;
            }

            if (bookUrl == null || bookUrl.isEmpty()) { continue; }

            if (!bookUrl.startsWith("http")) {
                bookUrl = "https://books.google.com" + bookUrl;
            }

            // Fetch the book details
            Map<String, String> bookDetails = scrapeBookDetails(bookUrl);
            bookDetails.put("title", bookTitle);

            // Download and store image bytes
            String imageUrl = bookDetails.get("imageUrl");
            byte[] imageBytes = (imageUrl != null && !imageUrl.isEmpty()) ? downloadImage(imageUrl) : loadDefaultImage();
            imageBytesList.add(imageBytes);

            books.add(bookDetails);
            scrapeCount++;
        }

        return books;
    }

    /**
     * Scrapes detailed information about a specific book from Google Books.
     *
     * @param bookUrl The URL of the selected book on Google Books.
     * @return A map containing detailed information about the book.
     * @throws IOException If there's an error during the scraping process.
     */
    public Map<String, String> scrapeBookDetails(String bookUrl) throws IOException {
        // Log the URL being used in the scraping process

        // Ensure the URL is not empty
        if (bookUrl == null || bookUrl.isEmpty()) {
            throw new IOException();
        }

        // Connect to the book URL and fetch the HTML document
        Document doc = Jsoup.connect(bookUrl).get();

        // Map to store detailed book information
        Map<String, String> bookDetails = new HashMap<>();

        // Extract the title from the <title> tag in the <head>
        String title = doc.title();

        // Clean the title if needed (e.g., remove extra " - Google Books" part)
        if (title != null && title.contains(" - Google Books")) {
            title = title.replace(" - Google Books", "").trim();
        }
        bookDetails.put("title", title);

        // Scrape the first ISBN
        String isbnText = doc.select("span:contains(ISBN)").next().text();
        String firstIsbn = isbnText.split(",")[0].trim(); // Get the first ISBN before the comma
        bookDetails.put("ISBN", firstIsbn);

        // Add other details
        bookDetails.put("Publisher", doc.select("span:contains(Publisher)").next().text());
        bookDetails.put("Author", doc.select("span:contains(Author)").next().text());
        bookDetails.put("Page Count", doc.select("span:contains(Page count)").next().text());


        // Scrape description
        String description = doc.select(".bHexk").text();
        if (description.isEmpty()) {
            description = "No Description Found";
        }
        bookDetails.put("Description", description);

        // Retrieve book cover image using the book ID from document

        String bookId = extractBookId(bookUrl);

        String imageUrl = "https://books.google.com/books/content?id=" + bookId + "&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";
        bookDetails.put("imageUrl", imageUrl);


        // Scrape publication date (adjusted)
        String publicationDateRaw = doc.select("span:contains(Published)").next().text();
        String formattedPublicationDate = formatDateString(publicationDateRaw);
        bookDetails.put("Publication Date", formattedPublicationDate);

        return bookDetails;
    }

    /**
     * Extracts the book ID from the book URL.
     *
     * @param bookUrl The URL of the book.
     * @return The extracted book ID or an empty string if not found.
     */
    private String extractBookId(String bookUrl){
        Pattern pattern = Pattern.compile("id=([^&]+)");
        Matcher matcher = pattern.matcher(bookUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";  // Return empty string if ID not found
    }

    /**
     * Downloads the image from the provided URL.
     *
     * @param imageUrl The URL of the image.
     * @return A byte array representing the image, or the default image if the download fails.
     */
    public byte[] downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            // Open the connection and set the timeout
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1500);  // 1.5 seconds to establish connection
            connection.setReadTimeout(1500);     // 1.5 seconds to read data

            // Start downloading the image
            try (InputStream inputStream = connection.getInputStream()) {
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                return baos.toByteArray();
            }
        } catch (IOException e) {
            AlertManager.getInstance().showAlert("Error: Image Download Failed ", "Failed to download cover image. Using default image instead.", Alert.AlertType.INFORMATION);
            return loadDefaultImage();  // Load the default image if downloading fails
        }
    }


    /**
     * Loads a default image if the cover image download fails.
     *
     * @return A byte array representing the default image.
     */
    public byte[] loadDefaultImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg");
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            AlertManager.getInstance().showAlert("Error: ", "Failed to load default image.", Alert.AlertType.ERROR);
            return null;
        }
    }

    /**
     * Helper method to format date strings from "14 September 2008" to "14-9-2008".
     * If only the year is provided (e.g., "2008"), it assumes "01-01-YYYY".
     *
     * @param dateStr The date string to format.
     * @return The formatted date string.
     */
    private String formatDateString(String dateStr) {
        SimpleDateFormat inputFormatFull = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        SimpleDateFormat inputFormatYear = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");  // Database format

        try {
            // Try parsing full date (e.g., "14 September 2008")
            Date date = inputFormatFull.parse(dateStr);
            return outputFormat.format(date);  // Convert to yyyy-MM-dd format
        } catch (ParseException e) {
            // If only the year is provided (e.g., "2008"), assume 01-01-YYYY
            try {
                Date yearOnly = inputFormatYear.parse(dateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(yearOnly);
                calendar.set(Calendar.DAY_OF_MONTH, 1);  // Set default day to 1
                calendar.set(Calendar.MONTH, Calendar.JANUARY);  // Set default month to January
                return outputFormat.format(calendar.getTime());
            } catch (ParseException ex) {
                // Handle unexpected formats by returning the original string
                return dateStr;
            }
        }
    }
}
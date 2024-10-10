package com.example.cab302assessment10b0101;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Scraper class is responsible for scraping Google Books search results
 * and detailed information about a specific book.
 */
public class Scraper {

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

        for (Element result : searchResults) {
            String bookTitle = result.select("h3").text();
            String bookUrl = result.select("a").attr("href");

            if (!bookUrl.startsWith("http")) {
                bookUrl = "https://books.google.com" + bookUrl;
            }

            Map<String, String> book = new HashMap<>();
            book.put("title", bookTitle);
            book.put("url", bookUrl);

            books.add(book);

            if (books.size() >= 5) {
                break;
            }
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

        // Fetch book cover image using the ISBN from Open Library API
        String imageUrl = "https://covers.openlibrary.org/b/isbn/" + firstIsbn + "-M.jpg";
        System.out.println("Cover Image URL: " + imageUrl);  // For verification
        bookDetails.put("imageUrl", imageUrl); // Store the cover image URL in the map

        // Scrape publication date (adjusted)
        String publicationDateRaw = doc.select("span:contains(Published)").next().text();
        String formattedPublicationDate = formatDateString(publicationDateRaw);
        bookDetails.put("Publication Date", formattedPublicationDate);

        return bookDetails;
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
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Try parsing full date (e.g., "14 September 2008")
            Date date = inputFormatFull.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            // If only the year is provided (e.g., "2008"), assume 01-01-YYYY
            try {
                Date yearOnly = inputFormatYear.parse(dateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(yearOnly);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                return outputFormat.format(calendar.getTime());
            } catch (ParseException ex) {
                // Handle unexpected formats by returning the original string
                return dateStr;
            }
        }
    }
}

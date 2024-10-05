package com.example.cab302assessment10b0101;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        // Replace spaces with '+' for the search URL
        String formattedQuery = query.trim().replace(" ", "+");

        // The URL for Google Books search results
        String url = "https://books.google.com/books?q=" + formattedQuery;

        // Connect to the URL and fetch the HTML document
        Document doc = Jsoup.connect(url).get();

        // Parse the document to get the search result elements
        Elements searchResults = doc.select(".bHexk");

        // List to store the top 5 book titles and URLs
        List<Map<String, String>> books = new ArrayList<>();

        // Iterate through the results and extract book titles and URLs
        for (Element result : searchResults) {
            String bookTitle = result.select("h3").text();  // Book title
            String bookUrl = result.select("a").attr("href"); // Get the book link

            // Ensure the link is a full URL
            if (!bookUrl.startsWith("http")) {
                bookUrl = "https://books.google.com" + bookUrl;
            }

            Map<String, String> book = new HashMap<>();
            book.put("title", bookTitle);
            book.put("url", bookUrl);

            books.add(book);

            // Only keep the top 5 results
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

        // Scrape the details (ISBN, publisher, author, page count, description, published date)
        bookDetails.put("ISBN", doc.select("span:contains(ISBN)").next().text());
        bookDetails.put("Publisher", doc.select("span:contains(Publisher)").next().text());
        bookDetails.put("Author", doc.select("span:contains(Author)").next().text());
        bookDetails.put("Page Count", doc.select("span:contains(Page count)").next().text());
        bookDetails.put("Description", doc.select(".bHexk").text());
        bookDetails.put("Originally Published", doc.select("span:contains(Originally published)").next().text());

        return bookDetails;
    }
}

/*
import com.example.cab302assessment10b0101.WebScraper;
import com.example.cab302assessment10b0101.model.Book;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Test
public void testWebScraperWithMockHtml() {
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/mockdata/mock-page.html")));
    WebScraper scraper = new WebScraper();
    Book book = scraper.extractBookDetails(html);

    assertEquals("Mock Book Title", book.getTitle());
    assertEquals("Mock Author", book.getAuthor());
    assertEquals("Science Fiction", book.getGenre());
    assertEquals("2023-06-15", book.getPublicationDate());
    assertEquals("9876543210", book.getIsbn());
    assertEquals("This is a description of the mock book.", book.getDescription());
}
*/

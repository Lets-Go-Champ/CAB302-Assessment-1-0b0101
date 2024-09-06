# LibraHome - Home Library Catalogue

## Project Overview

**LibraHome** is a JavaFX-based application designed to efficiently manage a personal collection of books. The application offers users a comprehensive tool to catalog their home library, track borrowed books, and interact with their collection through various features such as web scraping for book information, rating and reviews, and wish lists.

## Features

- **Book Management:** Add, edit, delete book entries, including title, author, genre, publication date, ISBN, and personal comments or annotations.
- **Search and Filter:** Search by title, author, or genre and filter books by multiple criteria.
- **Categorization:** Organize books into custom categories or shelves.
- **Book Details View:** Detailed view of each book entry with options to edit or delete.
- **Borrowing Tracking:** Track books lent out with borrower details and return date reminders.
- **Reading Tracking:** Track reading progress with start and end dates, and current page number.
- **Reading History:** Maintain a log of finished books with ratings and completion dates.
- **Wish Lists:** Create and manage wish lists for future book purchases or reads.
- **Borrower Reminders:** Send notifications for approaching return dates.
- **Data Exporting:** Export catalogue, reading history, and wish lists to CSV or XML.
- **Web Scraping for Book Information:** Retrieve and populate book details and cover images from external sources.
- **User Management:** Account creation and login with password security.
- **Analytics Dashboard:** Report and visualize reading statistics and book data.
- **User Interface and Experience:** Intuitive design, customizable views, and responsive layout.

## Project Structure

```scss
cab302assessment10b0101/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Book.java
│   │   │   ├── BookDAO.java
│   │   │   ├── DatabaseConnector.java
│   │   │   ├── LibraHomeApp.java
│   │   │   ├── BookManagerUI.java
│   │   │   ├── WebScraper.java
│   │   └── resources/   (for resource files like images)
│   └── test/            (for unit tests)
└── pom.xml  (for build management using Maven)

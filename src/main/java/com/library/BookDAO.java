package com.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    // Initialize database table
    public void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "isbn VARCHAR(20), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            
            // Insert sample data if table is empty
            insertSampleData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertSampleData() {
        String checkSql = "SELECT COUNT(*) FROM books";
        String insertSql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             ResultSet rs = checkStmt.executeQuery()) {
            
            if (rs.next() && rs.getInt(1) == 0) {
                // Table is empty, insert sample data
                String[][] sampleBooks = {
                    {"The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565"},
                    {"To Kill a Mockingbird", "Harper Lee", "978-0061120084"},
                    {"1984", "George Orwell", "978-0451524935"},
                    {"Pride and Prejudice", "Jane Austen", "978-0141439518"}
                };
                
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    for (String[] book : sampleBooks) {
                        insertStmt.setString(1, book[0]);
                        insertStmt.setString(2, book[1]);
                        insertStmt.setString(3, book[2]);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? ORDER BY title";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setIsbn(rs.getString("isbn"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}

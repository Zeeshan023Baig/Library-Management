package com.library;

import java.util.List;

public class LibraryService {
    private BookDAO bookDAO;
    
    public LibraryService() {
        this.bookDAO = new BookDAO();
        // Initialize database and create tables
        this.bookDAO.initDatabase();
    }
    
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    public void addBook(Book book) {
        bookDAO.addBook(book);
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }
}

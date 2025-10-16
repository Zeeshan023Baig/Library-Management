// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.library;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends HttpServlet {
   private LibraryService libraryService = new LibraryService();

   public BookServlet() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action = request.getParameter("action");
      if ("search".equals(action)) {
         String keyword = request.getParameter("keyword");
         List<Book> books = this.libraryService.searchBooks(keyword);
         request.setAttribute("books", books);
      } else {
         List<Book> books = this.libraryService.getAllBooks();
         request.setAttribute("books", books);
      }

      request.getRequestDispatcher("/books.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String title = request.getParameter("title");
      String author = request.getParameter("author");
      String isbn = request.getParameter("isbn");
      if (title != null && !title.trim().isEmpty()) {
         Book newBook = new Book(title, author, isbn);
         this.libraryService.addBook(newBook);
      }

      response.sendRedirect("books");
   }
}

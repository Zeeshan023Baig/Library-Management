<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management System</title>
    <style>
        body { font-family: Arial; margin: 40px; }
        table { border-collapse: collapse; width: 100%; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f2f2f2; }
        .search-box { margin: 20px 0; }
        .add-book { margin: 20px 0; padding: 20px; background: #f9f9f9; }
    </style>
</head>
<body>
    <h1>📚 Library Management System</h1>
    
    <!-- Search Box -->
    <div class="search-box">
        <form method="get">
            <input type="text" name="keyword" placeholder="Search books..." />
            <input type="hidden" name="action" value="search" />
            <button type="submit">Search</button>
            <a href="books">Show All</a>
        </form>
    </div>
    
    <!-- Books Table -->
    <h2>Book List (${books.size()} books)</h2>
    <table>
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
        </tr>
        <c:forEach items="${books}" var="book">
            <tr>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.isbn}</td>
            </tr>
        </c:forEach>
    </table>
    
    <!-- Add New Book -->
    <div class="add-book">
        <h3>Add New Book</h3>
        <form method="post">
            <input type="text" name="title" placeholder="Title" required /><br/><br/>
            <input type="text" name="author" placeholder="Author" /><br/><br/>
            <input type="text" name="isbn" placeholder="ISBN" /><br/><br/>
            <button type="submit">Add Book</button>
        </form>
    </div>
</body>
</html>

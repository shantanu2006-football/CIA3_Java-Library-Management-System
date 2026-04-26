package dao;

import model.Book;
import java.util.List;

public interface BookDAO {
    void createBook(Book book);
    Book getBookById(int id);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBook(int id);
    List<Book> searchBooksByTitle(String keyword);
}
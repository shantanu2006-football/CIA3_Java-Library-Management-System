package ui;

import dao.BookDAO;
import dao.BookDAOImpl;
import model.Book;
import model.PrintedBook;

import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDAO bookDAO = new BookDAOImpl();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    searchBook();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting System...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void addBook() {
        try {
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Number of Pages: ");
            int pages = Integer.parseInt(scanner.nextLine());

            // 0 is a placeholder ID since DB auto-increments
            Book newBook = new PrintedBook(0, title, author, price, pages);
            bookDAO.createBook(newBook);
        } catch (Exception e) {
            System.out.println("Data entry error! Make sure price and pages are numbers.");
        }
    }

    private static void viewAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found in the database.");
        } else {
            System.out.println("\n--- Book List ---");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }
    }

    private static void updateBook() {
        System.out.print("Enter the ID of the book to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Book existingBook = bookDAO.getBookById(id);
            
            if (existingBook != null) {
                System.out.print("Enter New Title (leave blank to keep current): ");
                String title = scanner.nextLine();
                if (!title.isEmpty()) existingBook.setTitle(title);

                System.out.print("Enter New Author (leave blank to keep current): ");
                String author = scanner.nextLine();
                if (!author.isEmpty()) existingBook.setAuthor(author);

                System.out.print("Enter New Price (type 0 to keep current): ");
                double price = Double.parseDouble(scanner.nextLine());
                if (price != 0) existingBook.setPrice(price);

                if (existingBook instanceof PrintedBook) {
                    System.out.print("Enter New Page Count (type 0 to keep current): ");
                    int pages = Integer.parseInt(scanner.nextLine());
                    if (pages != 0) ((PrintedBook) existingBook).setPages(pages);
                }

                bookDAO.updateBook(existingBook);
            } else {
                System.out.println("Book with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        }
    }

    private static void deleteBook() {
        System.out.print("Enter the ID of the book to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            bookDAO.deleteBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.");
        }
    }

    private static void searchBook() {
        System.out.print("Enter keyword for title search: ");
        String keyword = scanner.nextLine();
        List<Book> books = bookDAO.searchBooksByTitle(keyword);
        
        if (books.isEmpty()) {
            System.out.println("No books found matching: " + keyword);
        } else {
            System.out.println("\n--- Search Results ---");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }
    }
}
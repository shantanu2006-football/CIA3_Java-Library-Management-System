package model;

public class PrintedBook extends Book {
    private int pages;

    // Constructor using super()
    public PrintedBook(int id, String title, String author, double price, int pages) {
        super(id, title, author, price);
        this.pages = pages;
    }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    // Method Overriding
    @Override
    public String getBookType() {
        return "Printed Book";
    }

    // Method Overriding (Polymorphism)
    @Override
    public String toString() {
        return super.toString() + " | Pages: " + pages + " | Type: " + getBookType();
    }

    // Method Overloading (Bonus Concept)
    public void applyDiscount(double discountPercentage) {
        double newPrice = getPrice() - (getPrice() * (discountPercentage / 100));
        setPrice(newPrice);
    }

    // Overloaded method
    public void applyDiscount(double discountPercentage, boolean isStudent) {
        if (isStudent) discountPercentage += 5; // Extra 5% for students
        applyDiscount(discountPercentage);
    }
}
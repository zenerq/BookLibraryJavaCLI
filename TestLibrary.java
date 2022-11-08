import java.util.Scanner;

class Book {
    int id;
    String name;
    int stock;

    Book(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    void display() {
        System.out.println("Book ID: " + id);
        System.out.println("Book Name: " + name);
        System.out.println("Book Stock: " + stock);
    }
}


class History {
    Book book;
    String operationType;
    String date;

    History(Book book, String operationType, String date) {
        this.book = book;
        this.operationType = operationType;
        this.date = date;
    }

    void display() {
        System.out.println("Book ID: " + book.id);
        System.out.println("Book Name: " + book.name);
        System.out.println("Book Stock: " + book.stock);
        System.out.println("Operation Type: " + operationType);
        System.out.println("Date: " + date);
    }

    static History logReturnBook(Book book, String date) {
        return new History(book, "Return a book", date);
    }

    static History logGetBook(Book book, String date) {
        return new History(book, "Get a book", date);
    }

    static History reportMissing(Book book, String date) {
        return new History(book, "Report missing", date);
    }

    static History logStockUpdate(Book book, String date) {
        return new History(book, "Update stock", date);
    }

    static History log(String description, String date) {
        return new History(null, description, date);
    }
}

class Library {
    Book[] books;
    History[] history;

    Library(Book[] books, History[] history) {
        this.books = books;
        this.history = history;
    }

    void updateBookStock(String name, int stock) {
        for (Book book : books) {
            if (book.name.equals(name)) {
                book.stock = stock;
                return;
            }
        }
        books = addBook(books, new Book(books.length + 1, name, stock));
    }

    void listAllBooks(boolean haveStockInfo) {
        for (Book book : books) {
            if (haveStockInfo) {
                book.display();
            } else {
                System.out.println("Book ID: " + book.id);
                System.out.println("Book Name: " + book.name);
            }
        }
    }

    void listAllHistory(String fromDate, String toDate) {
        for (History history : history) {
            if (history.date.compareTo(fromDate) >= 0 && history.date.compareTo(toDate) <= 0) {
                history.display();
            }
        }
    }

    Book borrowBook(String name) {
        for (Book book : books) {
            if (book.name.equals(name)) {
                if (book.stock == 0) {
                    history = addHistory(history, History.reportMissing(book, "2020-01-01"));
                } else {
                    book.stock--;
                    history = addHistory(history, History.logGetBook(book, "2020-01-01"));
                }
                return book;
            }
        }
        return null;
    }

    Book returnBook(Book book) {
        for (Book b : books) {
            if (b.name.equals(book.name)) {
                b.stock++;
                history = addHistory(history, History.logReturnBook(book, "2020-01-01"));
                return b;
            }
        }
        return null;
    }

    Book[] addBook(Book[] books, Book book) {
        Book[] newBooks = new Book[books.length + 1];
        for (int i = 0; i < books.length; i++) {
            newBooks[i] = books[i];
        }
        newBooks[books.length] = book;
        return newBooks;
    }

    History[] addHistory(History[] history, History h) {
        History[] newHistory = new History[history.length + 1];
        for (int i = 0; i < history.length; i++) {
            newHistory[i] = history[i];
        }
        newHistory[history.length] = h;
        return newHistory;
    }
}

// Create the following methods in the TestLibrary class. Test all the functionalities.

public class TestLibrary {
    public static void main(String[] args) {
        Book[] books = new Book[0];
        History[] history = new History[0];
        Library library = new Library(books, history);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create a new book");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. Update the stock of a book");
            System.out.println("5. List all books");
            System.out.println("6. List all history");
            System.out.println("7. List all history between two dates");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter the name of the book: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the stock of the book: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();
                    library.updateBookStock(name, stock);
                    System.out.println("______________________________");
                    System.out.println("Book created successfully");
                    System.out.println("______________________________");
                    break;
                case 2:
                    System.out.print("Enter the name of the book: ");
                    name = scanner.nextLine();
                    Book book = library.borrowBook(name);
                    if (book == null) {
                        System.out.println("______________________________");
                        System.out.println("Book not found");
                    } else {
                        System.out.println("______________________________");
                        System.out.println("Book borrowed");
                    }
                    System.out.println("______________________________");
                    break;
                case 3:
                    System.out.print("Enter the name of the book: ");
                    name = scanner.nextLine();
                    System.out.print("Enter the stock of the book: ");
                    stock = scanner.nextInt();
                    scanner.nextLine();
                    book = new Book(0, name, stock);
                    book = library.returnBook(book);
                    if (book == null) {
                        System.out.println("______________________________");
                        System.out.println("Book not found");
                    } else {
                        System.out.println("______________________________");
                        System.out.println("Book returned");
                    }
                    System.out.println("______________________________");
                    break;
                case 4:
                    System.out.print("Enter the name of the book: ");
                    name = scanner.nextLine();
                    System.out.print("Enter the stock of the book: ");
                    stock = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("______________________________");
                    library.updateBookStock(name, stock);
                    System.out.println("Stock updated successfully");
                    System.out.println("______________________________");
                    break;
                case 5:
                    System.out.print("Do you want to see the stock information? (y/n): ");
                    String answer = scanner.nextLine();
                    boolean haveStockInfo = answer.equals("y");
                    System.out.println("______________________________");
                    library.listAllBooks(haveStockInfo);
                    System.out.println("______________________________");
                    break;
                case 6:
                    System.out.println("______________________________");
                    library.listAllHistory("2020-01-01", "2020-12-31");
                    System.out.println("______________________________");
                    break;
                case 7:
                    System.out.print("Enter the start date (yyyy-mm-dd): ");
                    String fromDate = scanner.nextLine();
                    System.out.print("Enter the end date (yyyy-mm-dd): ");
                    String toDate = scanner.nextLine();
                    System.out.println("______________________________");
                    library.listAllHistory(fromDate, toDate);
                    System.out.println("______________________________");
                    break;
                case 8:
                    System.exit(0);
            }
        }
    }
}


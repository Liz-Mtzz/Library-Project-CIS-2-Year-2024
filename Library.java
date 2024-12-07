import java.util.Scanner;
import com.sun.net.httpserver.Headers;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import javax.sound.sampled.Line;
import java.util.NoSuchElementException;
/**
 * A library management class. Has a simple shell that users can interact with to add/remove/checkout/list books in the library.
 * Also allows saving the library state to a file and reloading it from the file.
 */

public class Library {
    private BST bst = new BST();
    public UnorderedLinkedList<Book> BookList;
    public BST getBst() {
       return bst;  // initialize the BST
    }


    public Library() {
        this.BookList = new UnorderedLinkedList<Book>();
    }
    public UnorderedLinkedList<Book> getBookList() {
        return BookList;
    }
    
    
    /**
     * Adds a book to the library. If the library already has this book then it
     * adds to the number of copies the library has.
     *
     * @throws IllegalArgumentException no parts of book object can be empty or
     * null
     * @param book a book object to be added
     * @author Anzac Houchen
     * @author anzac.shelby@gmail.com Print statements are for Debugging.
     */
    public void addBook(Book book) {
        // Prevent bad data from being added.
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null value.");
        }
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("This book is missing a Title.");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Book is missing Author.");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("ISBN can't be emtpy");
        }
        if (book.getPublicationYear() > 2025
                || book.getPublicationYear() < 0) {
            throw new IllegalArgumentException("Book is published in future?");
        }
        if (book.getNumberOfCopies() < 1) {
            throw new IllegalArgumentException("Number of copies must be 1 or more");

        }
        // Search for the book using binary search tree(not yet implemented)
        Book alreadyAddedBook = bst.get(book.getIsbn());
        if (alreadyAddedBook != null) { // Book is already added
            alreadyAddedBook.addCopies(book.getNumberOfCopies());
        } else { // Adding new book not already in Library to linked list
            bst.add(book.getIsbn(),book);
            BookList.add(book);
        }
    }
        
     

    /**
     * Checks out the given book from the library. Throw the appropriate
     * exception if book doesnt exist or there are no more copies available.
     *
     * @param isbn The ISBN of the book to check out
     * @throws IllegalArgumentException if the ISBN is null or empty.
     * @throws IllegalArgumentException if the book with the specified ISBN is
     * not found in the library.
     * @throws IllegalArgumentException if there are no available copies of the
     * book to check out
     * @Author Elizabeth Martinez Mendoza
     */
    public void checkout(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }

        // search for the book 
        Book bookToCheckout = bst.get(isbn);

        if (bookToCheckout == null) {
            // we dont carry this book in our library
            throw new IllegalArgumentException(" We dont carry the book with ISBN " + isbn + " .");
        }

        // checking if we have enough copies avaliable. 
        if (bookToCheckout.getNumberOfCopies() <= 0) {
            throw new IllegalArgumentException("No copies of the book with ISBN " + isbn + " are available for checkout.");
        }

        // checkout the book, reduce the number of copies 
        bookToCheckout.addCopies(-1); // decrease copies by 1
        System.out.println("Successfully checked out: " + bookToCheckout.getTitle() + " by " + bookToCheckout.getAuthor());

    }

    
    /**
     * Returns a book to the library
     */
    public void returnBook(String isbn) {
        Book book = findByISBN(isbn);

        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " does not exist.");
        }

        if (book.getNumberOfCopies() <= 0) { /// 
            throw new IllegalStateException("No checked-out copies of the book with ISBN " + isbn + " to return.");
        }

        book.addCopies(1);
        
        System.out.println("Book with ISBN " + isbn + " has been successfully returned. Available copies: " + book.getNumberOfCopies());
    }

    /**
     * Finds this book in the library. Throws appropriate exception if the book
     * doesnt exist.
     *
     * @param title the title of the book the program is to find
     * @param author the author of the book the program is to find
     * @return the book object that specifically matches the provided title and
     * author
     * @throws IllegalArgumentException if author or title is incorrectly
     * provided
     * @throws UnsupportedOperationException if no book is found with the user's
     * input
     * @author Sreyas Kishore
     * @author sreyas.kishore@gmail.com
     */

    public Book findByTitleAndAuthor(String title, String author) {
        // If statements validate the user's input to the program and returns appropriate exceptions.
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty.");
        }
        
        // Correct inputs redirected towards the UnorderedLinkedList with the list of books.
        UnorderedLinkedList<Book>.Node<Book> current = BookList.getHead(); // Access the head of the linked list
        while (current != null) {
            Book book = current.data; // Access the book object
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                return book; // Returns or outputs book if a match with author or title is found.
            }
            current = current.next; // Move to the next node
        }

        // If no match is found with the user's input, throw exception. 
        throw new UnsupportedOperationException("Book not found: Title = " + title + ", Author = " + author);
    }

    /**
     * Finds a book in the library by its ISBN.
     *
     * @param isbn the ISBN of the book to find
     * @return the book object if found, or null if no book matches the given
     * ISBN
     * @throws IllegalArgumentException if the provided ISBN is null or empty
     * @throws UnsupportedOperationException if no book with the given ISBN is
     * found
     * @author Ethan Tran
     * @author ethantran0324@gmail.com
     */
    public Book findByISBN(String isbn) {
        // Validate input
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }

        // Search through the library's linked list for the book
        UnorderedLinkedList<Book>.Node<Book> current = BookList.getHead(); // Access the head of the linked list
        while (current != null) {
            Book book = current.data; // Access the book object
            if (book.getIsbn().equalsIgnoreCase(isbn)) { // Match ISBN (case-insensitive)
                System.out.println("Book found:" + book.getTitle() + " by " + book.getAuthor());
                return book; // Book found
            }
            current = current.next; // Move to the next node
        }

        // If no book is found, throw an exception
        throw new UnsupportedOperationException("Book not found with ISBN: " + isbn);
    }

    /**
     * Saves the contents of this library to the given file.
     *
     * @param filename the name of the file to save the library data into
     * @throws IllegalArgumentException if the filename is null or empty
     * @throws RuntimeException if an error occurs during file writing
     * @author Ethan Tran
     * @author ethantran0324@gmail.com
     */
    public void save(String filename) {
        // Validate the filename
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty.");
        }
        java.io.File outputFile = new java.io.File(filename); // Create the file object
        java.io.PrintWriter writer = null; // Initialize the writer outside try-catch
        try {
            writer = new java.io.PrintWriter(outputFile); // Create the PrintWriter
            // Iterate through the library's linked list
            UnorderedLinkedList<Book>.Node<Book> current = BookList.getHead(); // Access the head of the linked list
            while (current != null) {
                Book book = current.data;
                // Format the book data as a comma-separated string
                String bookData = book.getTitle() + "," +
                book.getAuthor() + "," +
                book.getIsbn() + "," +
                book.getPublicationYear() + "," +
                book.getNumberOfCopies();
                // Write the book data to the file
                writer.println(bookData);
                // Move to the next node in the list
                current = current.next;
            }
            System.out.println("Library saved successfully to " + filename);
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException("An error occurred while saving the library to file: " + filename, e);
        } finally {
            // Ensure the writer is closed to free resources
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Loads the contents of this library from the given file. All existing data
     * in this library is cleared before loading from the file.
     *
     * @author Diya Prasanth
     * @param filename
     * @throws FileNotFoundException if the provided filename is invalid
     * @throws NumberFormatException if publication year or number of copies are
     * invalid
     */
    public void load(String filename) {
        /**
         * TBD: clear bst/linked list
         */
        File fIn = new File(filename);
        try (Scanner inputFile = new Scanner(fIn)) {
            while (inputFile.hasNextLine()) {
                String content = inputFile.nextLine();
                /**
                 * Assume the different book info is seperated by commas
                 */
                String[] bookArray = content.split(",");
                System.out.println("Adding Book. Title: %s, Author: %s, ISBN: %s, Publication Year: %d, Copies: %d");
                Book newBook = new Book(bookArray[0], bookArray[1], bookArray[2],
                        Integer.parseInt(bookArray[3]), Integer.parseInt(bookArray[4]));
                addBook(newBook);
            }
        } catch (FileNotFoundException e) {
            /**
             * File exception: Input file was not found. Return error.
             */
            System.err.println("File not found.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid data in input file.");
        }
    }

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("library> ");
			String line = scanner.nextLine();
			// TODO: Implement code 
			if (line.startsWith("add")) {
				// TODO: Implement this case.
				// The format of the line is
				// add title author isbn publicationYear numberOfCopies
				// e.g. add Star_Trek Gene_Roddenberry ISBN-1234 1965 10
				// NOTE: If a book already exists in the library, then the number of copies should be incremented by this amount.
				// Do appropriate error checking here.
			} else if (line.startsWith("checkout")) {
				// TODO: Implement this case.
				// The format of the line is
				// checkout isbn
				// e.g. checkout ISBN-1234
				// NOTE: If the book doesnt exist in the library, then the code should print an error.
			} else if (line.startsWith("findByTitleAndAuthor")) {
				// TODO: Implement this case.
				// The format of the line is
				// findByTitleAndAuthor <title> <author>
				// e.g. findByTitleAndAuthor Star_Trek Gene_Roddenberry
				// NOTE: If the book doesnt exist in the library, then the code should print an error.
				// If the book exists in the library, this code should print the ISBN, number of copies in the library, and the number of copies availabvle
			} else if (line.startsWith("return")) {
				// TODO: Implement this case.
				// Format of the line is
				// return <isbn>
				// e.g. return ISBN-1234
				// NOTE: If the book was never checked out, this code should print an error.
			} else if (line.startsWith("list")) {
				// TODO: Implement this case.
				// Format of the line is 
				// list <isnb>
				// e.g. list ISBN-1234
				// NOTE: This code should print out the number of copies in the library and the number of copies available.
			} else if (line.startsWith("save")) {
				// TODO: Implement this case.
				// Format of the line is
				// save <filename>
				// e.g. save LbraryFile.dat
			} else if (line.startsWith("load")) {
				// TODO: Implement this case.
				// Format of the line is:
				// load <filename>
				// e.g. load LibraryFile.dat
			} else if (line.startsWith("exit")) {
				break;
			}
		}
	}
}

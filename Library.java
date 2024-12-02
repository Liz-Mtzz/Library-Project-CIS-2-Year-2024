import java.util.Scanner;
import java.util.Iterator;

/**
 * A library management class. Has a simple shell that users can interact with to add/remove/checkout/list books in the library.
 * Also allows saving the library state to a file and reloading it from the file.
 */
public class Library {


    /**
     * Adds a book to the library. If the library already has this 
     * book then it adds to the number of copies the library has.
     * @throws IllegalArgumentException no parts of book object can be empty or null
     * @param book a book object to be added
     * @author Anzac Houchen 
     * @author anzac.shelby@gmail.com
     * Print statements are for Debugging.
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
        if (book.getPublicaitonYear() > 2025 || 
        book.getPublicationYear() < 0) {
            throw new IllegalArgumentException("Book is published in future?");
        }
        if (book.getNumberOfCopies() < 1) {
            throw new IllegalArgumentException("Number of copies must be 1 or more");
        }
        // Search for the book using binary search tree(not yet implemented)
        Book alreadyAddedBook = bst.findByISBN(book.getIsbn());
        if (alreadyAddedBook != null) { // Book is already added
            // Update copies
            alreadyAddedBook.addCopies(book.getNumberOfCopies());
        }
        else { // Adding new book not already in Library to linked list
            Node newNode = new Node(book);
            newNode.next = head;
            head = newNode;
            // Add to binary search tree (not yet implemented)
            bst.add(book);
        }
        // Uncomment to Debug.
        // System.out.println();
        // System.out.println(book.getNumberOfCopies());
        // System.out.print(" copies of " + book.getTitle());
        // System.out.print(" were added. Total copies now ");
        // System.out.print(alreadyAddedBook.getNumberOfCopies() + ".");
        // System.out.println("ISBN: " + book.getIsbn());
    }

    /**
     * Checks out the given book from the library. Throw the appropriate
     * exception if book doesnt exist or there are no more copies available.
     * @param isbn The ISBN of the book to check out
     * @throws IllegalArgumentException if the ISBN is null or empty.
     * @throws IllegalArgumentException if the book with the specified ISBN is not found in the library.
     * @throws IllegalArgumentException if there are no available copies of the book to check out
     * @Author Elizabeth Martinez Mendoza
     */
	
    public void checkout(String isbn) {
      if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }
    
        // search for the book 
        Book bookToCheckout = bst.findByISBN(isbn);
    
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
    public void returnBook(String isnb) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Finds this book in the library. Throws appropriate exception if the book
     * doesnt exist.
     * @param title the title of the book the program is to find
     * @param author the author of the book the program is to find
     * @return the book object that specifically matches the provided title and author
     * @throws IllegalArgumentException if author or title is incorrectly provided 
     * @throws UnsupportedOperationException if no book is found with the user's input
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
    UnorderedLinkedList.Node<Book> current = bookList.head; // Access the head of the linked list
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
     * Finds this book in the library. Throws appropriate exception if the book
     * doesnt exist.
     */
     
    public Book findByISBN(String isbn) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Saves the contents of this library to the given file.
     */
    public void save(String filename) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Loads the contents of this library from the given file. All existing data
     * in this library is cleared before loading from the file.
     */
    public void load(String filename) {
        // TODO: Implement this method.
        throw new UnsupportedOperationException("not implemented");
    }

/**
 * An unordered linked list class.
 * This list allows for adding elements to the front of the list
 * @param<T> generic type elements in this list.
 * @author Anzac Houchen
 * @author anzac.shelby@gmail.com
 */
public class UnorderedLinkedList<T> {
    private Node<T> head;

    /**
     * Constructor. Creates empty list.
     * @author Anzac Houchen
     * @author anzac.shelby@gmail.com
     */
    public UnorderedLinkedList() {
        head = null;
    }

    /**
     * Adds a Node to the front of the list.
     * @param data A book or other element 
     * @author Anzac Houchen
     * @author anzac.shelby@gmail.com
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
    }


    /**
     * Inner inner class to create nodes for list.
     * @param <T> data stored in the node ie. book
     * @author Anzac Houchen
     * @author anzac.shelby@gmail.com
     */
    private class Node {
        T data;
        Node next;

        /**
         * Constructor. Makes nodes with data as input.
         * @param data will be a book object in the Library
         * @author Anzac Houchen
         * @author anzac.shelby@gmail.com
         */
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
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
    try {
        /**
         * Separates and interprets the title and author from the input.
         * Processes the "findByTitleAndAuthor" command from the user input.
         * @throws IllegalArgumentException if the title or author is not provided or the book is not found in the library.
         * @throws UnsupportedOperationException if the method in the library is not implemented.
         * @param line the input line containing the command and parameters.
         * @author Sreyas Kishore
         * @author sreyas.kishore@gmail.com
         */
        
        // Split the input into command, title, and author
        String[] parts = line.split(" ", 3); 
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: findByTitleAndAuthor <title> <author>");
        }

        String title = parts[1];
        String author = parts[2];

        // Call the findByTitleAndAuthor method
        Book foundBook = library.findByTitleAndAuthor(title, author);

        // Prints the details about the found book
        System.out.println("Book has been found:");
        System.out.println("ISBN: " + foundBook.getIsbn());
        System.out.println("Number of Copies in Library: " + foundBook.getNumberOfCopies());
        System.out.println("Number of Copies Available: " + 
                           (foundBook.getNumberOfCopies() - foundBook.getCheckedOutCopies()));
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (UnsupportedOperationException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
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

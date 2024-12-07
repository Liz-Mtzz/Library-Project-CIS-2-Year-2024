
public class Driver extends DriverBase {

    @SuppressWarnings("unchecked")
   public static void main(String[] args) {
      // This code currently doesnt test anything.
      // Print the output
      testOutput.put("Compilation succeeded", null);
      printJsonOutput();

      /* Test For AddBook */ 
      Library library = new Library();

      

      


      //Test : Book with all the correct info.

      Book book1 = new Book("Where The Red Fern Grows","Wilson Rawls", "780395775288", 1961, 10);
      library.addBook(book1);
      System.out.println("Adding book 1...");
      library.addBook(book1);
      testingAddBook(library, "780395775288", "Where The Red Fern Grows");
      
      //Checking if we were able to find book after adding it, making sure its being added/stored correctly 
      try {
         Book foundBook = library.findByTitleAndAuthor("Where The Red Fern Grows", "Wilson Rawls");
         System.out.println("Found book: " + foundBook.getTitle() + " by " + foundBook.getAuthor());
      } catch (Exception e) {
         System.out.println("Error: " + e.getMessage());
      }

      testingCheckoutBook(library, "780395775288");
      
      library.returnBook("780395775288");

      library.findByISBN("780395775288");
      


   }
   private static void testingCheckoutBook(Library library, String isbn) {
      Book book = library.getBst().get(isbn);  // Retrieve the book by ISBN
      if (book != null) {
          boolean success = book.checkout();  // Attempt to checkout the book
          if (success) {
              System.out.println("Checkout successful! Copies remaining: " + book.getNumberOfCopies());
          } else {
              System.out.println("Checkout failed! No copies left.");
          }
      } else {
          System.out.println("Book not found in the library.");
      }
  }
  

   private static void testingAddBook(Library library,  String isbn, String expectedTitle) {
      Book foundBook = library.getBst().get(isbn);  // You need a getBst() method to access the BST
      if (foundBook != null && foundBook.getTitle().equals(expectedTitle)) {
         System.out.println("Successfully added the book: " + foundBook.getTitle());
      } else {
         System.out.println("Failed to add book with ISBN " + foundBook.getTitle());
      }
   }
}

public class Book extends Record
{
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String title, String author, String bookID) 
    {
        super(bookID);
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getAuthor() 
    {
        return author;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getBookID() 
    {
        return ID;
    }

    public void setBookId(String bookID) 
    {
        this.ID = bookID;
    }

    public boolean isBorrowed() 
    {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) 
    {
        isBorrowed = borrowed;
    }

    public void displayInfo(){
        System.out.println("Book ID: " + ID + ", Title: " + title + ", Author: " + author + ", Status: " + (isBorrowed ? "Borrowed" : "Available"));
    }
    
    public String toString() 
    {
        return "ID: " + ID + ", Title: " + title + ", Author: " + author + ", Status: " + (isBorrowed ? "Borrowed" : "Available");
    }
}
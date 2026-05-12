import java.time.LocalDate; 

public class BorrowingRecord extends Record
{
    private Book borrowedBook;
    private User borrower;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; 

    public BorrowingRecord(String recordID, Book borrowedBook, User borrower, LocalDate borrowDate, LocalDate dueDate) 
    {
        super(recordID);
        this.borrowedBook = borrowedBook;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null; 
    }

    public String getRecordID() 
    {
        return ID;
    }

    public Book getBorrowedBook() 
    {
        return borrowedBook;
    }

    public User getBorrower() 
    {
        return borrower;
    }

    public LocalDate getBorrowDate() 
    {
        return borrowDate;
    }

    public LocalDate getDueDate() 
    {
        return dueDate;
    }

    public LocalDate getReturnDate() 
    {
        return returnDate;
    }

    public void setRecordID(String recordID) 
    {
        this.ID = recordID;
    }

    public void setBorrowedBook(Book borrowedBook) 
    {
        this.borrowedBook = borrowedBook;
    }

    public void setBorrower(User borrower) 
    {
        this.borrower = borrower;
    }

    public void setBorrowDate(LocalDate borrowDate) 
    {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(LocalDate dueDate) 
    {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) 
    {
        this.returnDate = returnDate;
    }

    public boolean isOverdue() 
    {
        if (returnDate == null) 
        { 
            return LocalDate.now().isAfter(dueDate);
        }
        return false;
    }

    public void displayInfo(){
        String status = (returnDate == null)?"OUT (Due: " + dueDate + ")" : "RETURNED (" + returnDate + ")";
        System.out.println("Record ID: " + ID + "\nBook: '" + borrowedBook.getTitle() + "' (ID: " + borrowedBook.getBookID()+
        ")" + "\nBorrower: '" + borrower.getName() + "' (ID: " + borrower.getUserID() + 
        ")" + "\nBorrow Date: " + borrowDate + "\nStatus: " + status);
    }
    
    public String toString(){
        String status = (returnDate == null) 
        ? "OUT (Due: " + dueDate + ")" 
        : "RETURNED (" + returnDate + ")";
        
        return "Record ID: " + ID 
        + ", Book: '" + borrowedBook.getTitle() + "' (ID: " + borrowedBook.getBookID() + ")" 
        + ", Borrower: '" + borrower.getName() + "' (ID: " + borrower.getUserID() + ")" 
        + ", Borrow Date: " + borrowDate 
        + ", Status: " + status;
    }
}

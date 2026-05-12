import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;

public class borrowBook extends JFrame {
    private JComboBox<String> userBox, bookBox;
    private JTextField borrowDateField, dueDateField;
    private JButton borrowButton;
    private JLabel resultLabel;

    public borrowBook() {
        setTitle("Borrow Book");
        setSize(400, 300);
        setLayout(null);

        JLabel userLabel = new JLabel("Select User: ");
        userLabel.setBounds(30, 30, 100, 25);
        add(userLabel);

        userBox = new JComboBox<>();
        userBox.setBounds(150, 30, 180, 25);
        add(userBox);

        JLabel bookLabel = new JLabel("Select Book: ");
        bookLabel.setBounds(30, 70, 100, 25);
        add(bookLabel);

        bookBox = new JComboBox<>();
        bookBox.setBounds(150, 70, 180, 25);
        add(bookBox);

        JLabel borrowDateLabel = new JLabel("Borrow Date: ");
        borrowDateLabel.setBounds(30, 110, 100, 25);
        add(borrowDateLabel);

        borrowDateField = new JTextField("2025-06-30");
        borrowDateField.setBounds(150, 110, 180, 25);
        add(borrowDateField);

        JLabel dueDateLabel = new JLabel("Due Date: ");
        dueDateLabel.setBounds(30, 150, 100, 25);
        add(dueDateLabel);

        dueDateField = new JTextField("2025-07-07");
        dueDateField.setBounds(150, 150, 180, 25);
        add(dueDateField);

        borrowButton = new JButton("Borrow");
        borrowButton.setBounds(140, 190, 100, 30);
        add(borrowButton);

        loadUsers();
        loadBooks();

        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = (String) userBox.getSelectedItem();
                String book = (String) bookBox.getSelectedItem();
                String borrowDateStr = borrowDateField.getText().trim();
                String dueDateStr = dueDateField.getText().trim();
                
                if (user == null || book == null || borrowDateStr.isEmpty() || dueDateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(borrowBook.this, "Fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] userParts = user.split(" - ");
                String[] bookParts = book.split(" - ");

                String userID = userParts[0];
                String userName = userParts[1];
                String bookID = bookParts[0];
                String bookTitle = bookParts[1];
                  

                try (BufferedReader br = new BufferedReader(new FileReader("borrowed.txt"))) {
                    String line;
                    while((line = br.readLine()) != null){
                        boolean isSameUser = line.contains("ID: " + userID) || line.contains("User ID: " + userID);
                        boolean isSameBook = line.contains("ID: " + bookID) || line.contains("Book ID: " + bookID);
                        boolean notReturned = !line.contains("RETURNED");
                        
                        if (isSameUser && isSameBook && notReturned) {
                            JOptionPane.showMessageDialog(borrowBook.this, "This user has already borrowed this book and has not returned it.", "Duplicate Borrowing", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                } catch (IOException ex){
                    ex.printStackTrace();
                }
                
                try{
                    User userObj = new User(userID, userName, "N/A");
                    Book bookObj = new Book(bookTitle, "N/A", bookID);
                    LocalDate borrowDate = LocalDate.parse(borrowDateStr);
                    LocalDate dueDate = LocalDate.parse(dueDateStr);
                    
                    String recordID = "BR" + System.currentTimeMillis();
                    BorrowingRecord record = new BorrowingRecord(recordID, bookObj, userObj, borrowDate, dueDate);
                    
                    BufferedWriter bw = new BufferedWriter(new FileWriter("borrowed.txt", true));
                    bw.write(record.toString());
                    bw.newLine();
                    bw.close();
                    
                    JOptionPane.showMessageDialog(borrowBook.this, "Book '" + bookObj.getTitle() + "' borrowed successfully by '" + userObj.getName() + "'.", "Borrow Success", JOptionPane.INFORMATION_MESSAGE);
                } catch(IOException ex){
                    JOptionPane.showMessageDialog(borrowBook.this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadUsers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    userBox.addItem(parts[0] + " - " + parts[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            userBox.addItem("No users found.");
        }
    }

    private void loadBooks() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    bookBox.addItem(parts[0] + " - " + parts[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            bookBox.addItem("No books found.");
        }
    }

    public static void main(String[] args) {
        new borrowBook();
    }
}

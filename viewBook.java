import javax.swing.*;
import java.io.*;

public class viewBook extends JFrame
{
    private JTextArea bookArea;
    
    public viewBook(){
        setTitle("View Books");
        setSize(400, 300);
        setLayout(null);
        
        JLabel titleLabel = new JLabel("Books in Library: ");
        titleLabel.setBounds(20, 20, 200, 25);
        add(titleLabel);
        
        bookArea = new JTextArea();
        bookArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookArea);
        scrollPane.setBounds(20, 50, 340, 180);
        add(scrollPane);
        
        loadBooksFromFile();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void loadBooksFromFile(){
        File file = new File("books.txt");
        if(!file.exists()){
            bookArea.setText("No books found.");
            return;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            StringBuilder allBooks = new StringBuilder();
            
            while((line = br.readLine())!=null){
                String [] parts = line.split(",", 3);
                
                if(parts.length >= 3){
                    String ID = parts[0].trim();
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    
                    allBooks.append("Book ID: ").append(ID).append("\n")
                    .append(" | Title: ").append(title).append("\n")
                    .append(" | Author: ").append(author).append("\n")
                    .append("----------------------------------------------------------------------------------\n");
                } else{
                    System.err.println("Skipping malformed book entry: " + line);
                }
            }
            
            if(allBooks.length() ==0){
                bookArea.setText("No books found in the file.");
            } else{
                bookArea.setText(allBooks.toString());
            }
        } catch(IOException e){
            bookArea.setText("Error reading books file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args) {
        new viewBook();
    }
}

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class deleteBook extends JFrame{
    private JTextField IDField;
    private JButton deleteButton;
    
    public deleteBook(){
        setTitle("Delete Book");
        setSize(400, 200);
        setLayout(null);
        
        JLabel IDLabel = new JLabel("Enter Book ID to Delete: ");
        IDLabel.setBounds(30, 30, 200, 25);
        add(IDLabel);
        
        IDField = new JTextField();
        IDField.setBounds(200, 30, 150, 25);
        add(IDField);
        
        deleteButton = new JButton("Delete Book");
        deleteButton.setBounds(120, 70, 140, 30);
        add(deleteButton);
        
        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                deleteBook();
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void deleteBook(){
        String bookID = IDField.getText().trim();
        if(bookID.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a Book ID", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File file = new File("books.txt");
        if(!file.exists()){
            JOptionPane.showMessageDialog(this, "books.txt not found.", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ArrayList<String> allBooks = new ArrayList<>();
        boolean found = false;
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",", 3);
                if(parts.length >= 1 && parts[0].equalsIgnoreCase(bookID)){
                    found = true;
                } else{
                    allBooks.add(line);
                }
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading file." + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        if(!found){
            JOptionPane.showMessageDialog(this, "Book ID not found.", "Delete Result", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for(String book : allBooks){
                bw.write(book);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Book deleted successfully.", "Delete Result", JOptionPane.INFORMATION_MESSAGE);
            
            IDField.setText("");
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Errow writing file: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[]args){
        new deleteBook();
    }
}
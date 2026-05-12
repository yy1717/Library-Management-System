import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class updateBook extends JFrame{
    private JTextField IDField, titleField, authorField;
    private JButton searchButton, updateButton;
    
    private ArrayList<String> allBooks = new ArrayList<>();
    private int foundIndex = -1;
    
    public updateBook(){
        setTitle("Update Book");
        setSize(450,300);
        setLayout(null);
        
        JLabel IDLabel = new JLabel("Enter Book ID to update: ");
        IDLabel.setBounds(30, 30, 200, 25);
        add(IDLabel);
        
        IDField = new JTextField();
        IDField.setBounds(220, 30, 150, 25);
        add(IDField);
        
        searchButton = new JButton("Search");
        searchButton.setBounds(150, 60, 100, 25);
        add(searchButton);
        
        JLabel titleLabel = new JLabel("New Title: ");
        titleLabel.setBounds(30, 100, 100, 25);
        add(titleLabel);
        
        titleField = new JTextField();
        titleField.setBounds(130, 100, 240, 25);
        add(titleField);
        
        JLabel authorLabel = new JLabel("New Author: ");
        authorLabel.setBounds(30, 140, 100, 25);
        add(authorLabel);
        
        authorField = new JTextField();
        authorField.setBounds(130, 140, 240, 25);
        add(authorField);
        
        updateButton = new JButton("Update Book");
        updateButton.setBounds(30, 220, 350, 30);
        add(updateButton);
        
        titleField.setEnabled(false);
        authorField.setEnabled(false);
        updateButton.setEnabled(false);
        
        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                searchBook();
            }
        });
        
        updateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateBook();
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void searchBook(){
        String bookID = IDField.getText().trim();
        foundIndex = -1;
        allBooks.clear();
        titleField.setText("");
        authorField.setText("");
        authorField.setEnabled(false);
        titleField.setEnabled(false);
        updateButton.setEnabled(false);
        
        try(BufferedReader br = new BufferedReader(new FileReader("data/books.txt"))){
            String line;
            int index = 0;
            while((line = br.readLine()) != null){
                allBooks.add(line);
                String [] parts = line.split(",");
                if(parts.length >= 3 && parts[0].equalsIgnoreCase(bookID)){
                    titleField.setText(parts[1]);
                    authorField.setText(parts[2]);
                    foundIndex = index;
                }
                index++;
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading books.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        if(foundIndex != -1){
            titleField.setEnabled(true);
            authorField.setEnabled(true);
            updateButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Book found. You may update.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(this, "Book ID not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateBook(){
        if(foundIndex == -1){
            JOptionPane.showMessageDialog(this, "Please search for a book first", "Update Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String newTitle = titleField.getText().trim();
        String newAuthor = authorField.getText().trim();
        
        if(newTitle.isEmpty() || newAuthor.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] parts = allBooks.get(foundIndex).split(",", 3);
        String updateLine;
        if (parts.length >= 1){
            updateLine = parts[0] + "," + newTitle + "," + newAuthor;
        } else {
            JOptionPane.showMessageDialog(this, "Error: Original book data format is invalid.", "Update Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        allBooks.set(foundIndex, updateLine);
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("data/books.txt"))){
            for(String book : allBooks){
                bw.write(book);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Book update successfully.", "Update Result", JOptionPane.INFORMATION_MESSAGE);
            
            IDField.setText("");
            titleField.setText("");
            authorField.setText("");
            titleField.setEnabled(false);
            authorField.setEnabled(false);
            updateButton.setEnabled(false);
            foundIndex = -1;
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error writing to books.txt: " + e.getMessage(), "File Write Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[]args){
        new updateBook();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class addBook extends JFrame {
    private JLabel IDLabel, titleLabel, authorLabel;
    private JTextField IDField, titleField, authorField;
    private JButton submitButton;
    
    public addBook(){
        setTitle("Add Book");
        setSize(350, 300);
        setLayout(null);
        
        IDLabel = new JLabel("Book ID: ");
        IDLabel.setBounds(30, 30, 100, 25);
        add(IDLabel);
        
        IDField = new JTextField();
        IDField.setBounds(140, 30, 150, 25);
        add(IDField);
        
        titleLabel = new JLabel("Book Title: ");
        titleLabel.setBounds(30, 70, 100, 25);
        add(titleLabel);
        
        titleField = new JTextField();
        titleField.setBounds(140, 70, 150, 25);
        add(titleField);
        
        authorLabel = new JLabel("Author: ");
        authorLabel.setBounds(30, 110, 100, 25);
        add(authorLabel);
        
        authorField = new JTextField();
        authorField.setBounds(140, 110, 150, 25);
        add(authorField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(120, 200, 100, 30);
        add(submitButton);
        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String ID = IDField.getText().trim();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                
                if(title.isEmpty()||author.isEmpty()){
                    JOptionPane.showMessageDialog(addBook.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else{
                    try{
                        FileWriter fw = new FileWriter("books.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        
                        bw.write(ID + "," + title + "," + author);
                        bw.newLine();
                        bw.close();
                        
                        JOptionPane.showMessageDialog(addBook.this, "Book Added Successfully!\nBook ID: " + ID + "\nTitle: " + title + "\nAuthor: " + author, "Success", JOptionPane.INFORMATION_MESSAGE);

                        IDField.setText("");
                        titleField.setText("");
                        authorField.setText("");
                    } catch(IOException ex){
                        JOptionPane.showMessageDialog(addBook.this, "Failed to add book.\n" + ex.getMessage(), "File Error.", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[]args){
        new addBook();
    }
    
    private String generateNextBookID(){
        int maxID = 0;
        File file = new File("books.txt");
        if(file.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split(",", 3);
                    if(parts.length > 0){
                        try{
                            int currentID = Integer.parseInt(parts[0].trim());
                            if(currentID > maxID){
                                maxID = currentID;
                            }
                        } catch(NumberFormatException e){
                            
                        }
                    }
                }
            } catch(IOException e){
                System.err.println("Error reading books.txt for ID generation: " + e.getMessage());
            }
        }
        return String.valueOf(maxID + 1);
    }
}

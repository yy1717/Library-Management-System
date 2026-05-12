import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class searchBook extends JFrame{
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    
    public searchBook(){
        setTitle("Search Book");
        setSize(500, 350);
        setLayout(null);
        
        JLabel promptLabel = new JLabel("Enter Book ID or Title: ");
        promptLabel.setBounds(30, 30, 200, 25);
        add(promptLabel);
        
        searchField = new JTextField();
        searchField.setBounds(200, 30, 200, 25);
        add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.setBounds(410, 30, 80, 25);
        add(searchButton);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(30, 70, 430, 200);
        add(scrollPane);
        
        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                searchBook();
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void searchBook(){
        String keyword = searchField.getText().trim().toLowerCase();
        resultArea.setText("");
        
        if(keyword.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a search keyword.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File file = new File("books.txt");
        if(!file.exists()){
            JOptionPane.showMessageDialog(this, "books.txt not found.", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean found = false;
            StringBuilder resultsBuilder = new StringBuilder();
            
            while((line = br.readLine()) != null){
                String[] parts = line.split(",", 3);
                if(parts.length >= 3){
                    String ID = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    
                    if(ID.toLowerCase().contains(keyword) || title.toLowerCase().contains(keyword)){
                        resultsBuilder.append("Book ID: ").append(ID).append("\n");
                        resultsBuilder.append("Title: ").append(title).append("\n");
                        resultsBuilder.append("Author: ").append(author).append("\n");
                        resultsBuilder.append("---------------------------\n");
                        found = true;
                    }
                }
            }
            
            if(found){
                resultArea.setText(resultsBuilder.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No matching books found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading books file: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new searchBook();
    }
}

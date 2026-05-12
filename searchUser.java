import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class searchUser extends JFrame{
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    
    public searchUser(){
        setTitle("Search User");
        setSize(500, 350);
        setLayout(null);
        
        JLabel promptLabel = new JLabel("Enter User ID or Name: ");
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
                searchUser();
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void searchUser(){
        String keyword = searchField.getText().trim().toLowerCase();
        resultArea.setText("");
        
        if(keyword.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a search keyword.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File file = new File("user.txt");
        if(!file.exists()){
            JOptionPane.showMessageDialog(this, "user.txt not found.", "File Errro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean found = false;
            StringBuilder resultsBuilder = new StringBuilder();
            
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length >= 3){
                    String ID = parts[0];
                    String name = parts[1];
                    String contact = parts[2];
                    
                    if(ID.toLowerCase().contains(keyword) || name.toLowerCase().contains(keyword)){
                        resultsBuilder.append("User ID: ").append(ID).append("\n");
                        resultsBuilder.append("Name: ").append(name).append("\n");
                        resultsBuilder.append("Contact: ").append(contact).append("\n");
                        resultsBuilder.append("---------------------------\n");
                        found = true;
                    }
                }
            }
            
            if(found){
                resultArea.setText(resultsBuilder.toString());
            } else{
                JOptionPane.showMessageDialog(this, "No matching users found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error reading users.txt: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new searchUser();
    }
}
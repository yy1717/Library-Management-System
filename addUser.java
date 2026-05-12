import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class addUser extends JFrame
{
    private JLabel IDLabel, nameLabel, phoneLabel;
    private JTextField IDField, nameField, phoneField;
    private JButton submitButton;
    
    
    public addUser()
    {
        setTitle("Add User");
        setSize(380, 300);
        setLayout(null);
        
        IDLabel = new JLabel("User ID: ");
        IDLabel.setBounds(30, 30, 150, 25);
        add(IDLabel);
        
        IDField = new JTextField();
        IDField.setBounds(130, 30, 150, 25);
        add(IDField);
        
        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 70, 100, 25);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(130, 70, 150, 25);
        add(nameField);
        
        phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(30, 110, 100, 25);
        add(phoneLabel);
        
        phoneField = new JTextField();
        phoneField.setBounds(130, 110, 150, 25);
        add(phoneField);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(110, 160, 100, 30);
        add(submitButton);
        
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText().trim();
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                
                if(ID.isEmpty() || name.isEmpty() || phone.isEmpty()){
                    JOptionPane.showMessageDialog(addUser.this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = new User(ID, name, phone);
                    
                    try{
                        FileWriter fw = new FileWriter("User.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(ID + "," + name + "," + phone);
                        bw.newLine();
                        bw.close();
                    
                        JOptionPane.showMessageDialog(addUser.this, "User Added Successfully:\nID: " + ID + "\nName: " + name + "\nPhone: " + phone, "Success", JOptionPane.INFORMATION_MESSAGE);                        
                        IDField.setText("");
                        nameField.setText("");
                        phoneField.setText("");
                    } catch(IOException ex){
                        JOptionPane.showMessageDialog(addUser.this, "Failed to save user: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String [] agrs){
        new addUser();
    }
    
}
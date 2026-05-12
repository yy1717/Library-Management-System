import javax.swing.*;
import java.io.*;

public class viewUser extends JFrame
{
    private JTextArea userArea;
    
    public viewUser(){
        setTitle("View Users");
        setSize(400, 300);
        setLayout(null);
        
        JLabel titleLabel = new JLabel("Registered Users: ");
        titleLabel.setBounds(20, 20, 200, 25);
        add(titleLabel);
        
        userArea = new JTextArea();
        userArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(userArea);
        scrollPane.setBounds(20, 50, 340, 180);
        add(scrollPane);
        
        loadUserFromFile();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void loadUserFromFile(){
        File file = new File("user.txt");
        if (!file.exists()){
            userArea.setText("No users found.");
            return;
        }
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder allUser = new StringBuilder();
            
            while ((line = br.readLine()) != null){
                String [] parts = line.split(",");
                if(parts.length >= 3) {
                    String ID = parts[0];
                    String name = parts[1];
                    String phone = parts[2];
                    allUser.append("User ID: ").append(ID).append("\n")
                    .append(" | Name: ").append(name).append("\n")
                    .append(" | Contact: ").append(phone).append("\n")
                    .append("----------------------------------------------------------------------------------\n");
                }
            }
            
            br.close();
            userArea.setText(allUser.toString());
                            
            } catch (IOException e){
                userArea.setText("Error reading users file.");
                e.printStackTrace();
            }
    }
    
    public static void main(String[]agrs){
        new viewUser();
    }
}

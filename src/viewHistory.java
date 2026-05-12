import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

public class viewHistory extends JFrame
{
    private JTextArea historyArea;
    
    public viewHistory(){
        setTitle("Borrowing History");
        setSize(400,300);
        setLayout(null);
        
        JLabel titleLabel = new JLabel("Borrowing History: ");
        titleLabel.setBounds(20, 20, 300, 25);
        add(titleLabel);
        
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(20, 50, 360, 200);
        add(scrollPane);
        
        loadHistoryRecords();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void loadHistoryRecords(){
        File file = new File("data/borrowed.txt");
        
        if(!file.exists()){
            historyArea.setText("No borrowed records found.");
            return;
        }
        
        StringBuilder current = new StringBuilder("Currently Borrowed Books: \n----------------------------\n");
        StringBuilder returned = new StringBuilder("\n Returned Books:\n----------------------------\n");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            boolean hasCurrent = false, hasReturned = false;
            
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                
                StringBuilder formattedRecord = new StringBuilder();
                for (String part : parts){
                    formattedRecord.append(part).append("\n");
                }
                
                if(line.contains("Status: OUT")){
                    current.append(formattedRecord.toString()).append("\n");
                    hasCurrent = true;
                } else if(line.contains("Status: RETURNED")){
                    returned.append(formattedRecord.toString()).append("\n");
                    hasReturned = true;
                }
            }
            StringBuilder full = new StringBuilder();
            if(hasCurrent) {
                full.append(current);
            } else{
                full.append("Currently Borrowed Books: \n----------------------------\nNo current borrowed records.\n");
            }
            
            if(hasReturned) {
                full.append(returned);
            } else{
                full.append("\nReturned Books:\n----------------------------\nNo returned records.\n");
            }
            
            historyArea.setText(full.toString());
        } catch(IOException e){
            historyArea.setText("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String [] args){
        new viewHistory();
    }
}

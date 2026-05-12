import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class returnBook extends JFrame{
    private JComboBox<String> borrowList;
    private JButton returnButton;
    private ArrayList<String> borrowedRecords = new ArrayList<>();
    
    public returnBook(){
        setTitle("Return Book");
        setSize(900, 250);
        setLayout(null);
        
        JLabel label = new JLabel("Select a borrowed record to return: ");
        label.setBounds(30, 30, 300, 25);
        add(label);
        
        borrowList = new JComboBox<>();
        borrowList.setBounds(30, 70, 800, 25);
        add(borrowList);
        
        returnButton = new JButton("Return Book");
        returnButton.setBounds(400, 110, 120, 30);
        add(returnButton);
        
        loadBorrowedRecords();
        
        returnButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int index = borrowList.getSelectedIndex();
                if(index >= 0 && index < borrowedRecords.size()){
                    String oldRecord = borrowedRecords.get(index);
                    
                    String returnedRecord = oldRecord.replaceAll("Status: OUT.*", "Status: RETURNED (" + LocalDate.now() + ")");
                    
                    borrowedRecords.set(index, returnedRecord);
                    
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/borrowed.txt"))) {
                        for (String record : borrowedRecords) {
                            bw.write(record);
                            bw.newLine();
                        }

                        JOptionPane.showMessageDialog(returnBook.this, "Book returned successfully.","Return Success",JOptionPane.INFORMATION_MESSAGE);

                        borrowList.removeItemAt(index);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(returnBook.this, "Error updating file.\n" + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(returnBook.this, "Please select a valid record to return.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void loadBorrowedRecords(){
        File file = new File("data/borrowed.txt");
        if(!file.exists()){
            borrowList.addItem("No borrowed records.");
            return;
        }
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while((line = br.readLine()) != null){
                if(line.contains("Status: OUT")){
                    borrowedRecords.add(line);
                    borrowList.addItem(line);
                }
            }
            
            if(borrowedRecords.isEmpty()){
                borrowList.addItem("No books currently borrowed.");
            }
            
        } catch(IOException e){
            borrowList.addItem("Error reading file.");
            e.printStackTrace();
        }
    }
    
    private void updateFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("data/borrowed.txt", false));
            for(String record : borrowedRecords){
                bw.write(record);
                bw.newLine();
            }
            bw.close();
        } catch(IOException e){
            JOptionPane.showMessageDialog(returnBook.this, "Error reading borrowed records file:\n" + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String [] agrs){
        new returnBook();
    }
}

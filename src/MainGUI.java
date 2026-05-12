import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {
    public static void main(String[]agrs){
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        } catch(Exception e){
            System.out.println("Look and Feel not set.");
        }
        
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(900, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        
        JLabel bgLabel = new JLabel(new ImageIcon("bg.jpg"));
        bgLabel.setBounds(0, 0, 900, 700);
        frame.setContentPane(bgLabel);
        bgLabel.setLayout(null);
        
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setBounds(260, 40, 500, 60);
        bgLabel.add(titleLabel);
        
        String[] buttonNames = {
            "Add Book", "View Books", "Update Book", 
            "Delete Book", "Search Book", "Add User",
            "View Users", "Search User", "Borror Book",
            "Return Book", "View History", "Exit"
        };
        
        JButton[] buttons = new JButton[buttonNames.length];
        
        int x1 = 160, x2 = 480, y = 130, gapY = 70, buttonWidth = 240, buttonHeight = 60;
        
        for (int i = 0; i < buttonNames.length; i++){
            buttons[i] = new JButton(buttonNames[i]);
            styleButton(buttons[i]);
            
            int bx = (i % 2 == 0)? x1 : x2;
            int by = y + (i/2) * gapY;
            
            buttons[i].setBounds(bx, by, buttonWidth, buttonHeight);
            bgLabel.add(buttons[i]);
        }
        
        buttons[0].addActionListener(e -> new addBook());
        buttons[1].addActionListener(e -> new viewBook());
        buttons[2].addActionListener(e -> new updateBook());
        buttons[3].addActionListener(e -> new deleteBook());
        buttons[4].addActionListener(e -> new searchBook());
        buttons[5].addActionListener(e -> new addUser());
        buttons[6].addActionListener(e -> new viewUser());
        buttons[7].addActionListener(e -> new searchUser());
        buttons[8].addActionListener(e -> new borrowBook());
        buttons[9].addActionListener(e -> new returnBook());
        buttons[10].addActionListener(e -> new viewHistory());
        buttons[11].addActionListener(e -> frame.dispose());

        frame.setVisible(true);        
    }
    
    public static void styleButton(JButton button){
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        button.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt){
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });
    }
}

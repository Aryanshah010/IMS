import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Login Page");
        frame.setSize(900, 500); // Increased frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create and place the photo (logo)
        JLabel photoLabel = new JLabel();
        ImageIcon icon = new ImageIcon("PROJECT1.jpg"); // Path to your image file
        photoLabel.setIcon(icon);
        photoLabel.setBounds(350, 20, 600, 450); // Adjust bounds as needed
        frame.add(photoLabel);

        // Create and place the username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(150, 110, 80, 25);
        frame.add(usernameLabel);

        JLabel LoginLabel = new JLabel("Login");
        LoginLabel.setBounds(150, 35, 80, 25);
        frame.add(LoginLabel);

        JLabel ORLabel = new JLabel("OR");
        ORLabel.setBounds(150, 320, 80, 25);
        frame.add(ORLabel);
    

        // JLabel NewLabel = new JLabel("Already have account?");
        // NewLabel.setBounds(150, 350, 150, 25);
        // frame.add(NewLabel);
        
        JButton NewButton = new JButton("Already have account?");
        NewButton.setBounds(150, 370, 170, 25);
        frame.add(NewButton);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 150, 200, 25);
        frame.add(usernameField);

        // Create and place the password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 180, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 220, 200, 25);
        frame.add(passwordField);

        // Create and place the login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(175, 270, 150, 25);
        frame.add(loginButton);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Here, you can add your verification logic (e.g., checking against a database)
                if (username.equals("user") && password.equals("pass")) {  // Sample verification
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}


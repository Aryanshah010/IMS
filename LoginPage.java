import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Login Page");
        frame.setSize(820, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        JLabel photoLabel = new JLabel();
        ImageIcon icon = new ImageIcon("/Users/aryanshah/Desktop/Inventify/PROJECT1.jpg");

    
        Image img = icon.getImage().getScaledInstance(530, 500, Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(img));
        imagePanel.add(photoLabel, BorderLayout.CENTER);

        // Create and configure the login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(245, 164, 241));
        loginPanel.setPreferredSize(new Dimension(300, 500));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(118, 25, 89, 30);
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 23));
        loginPanel.add(loginLabel);

        JLabel orLabel = new JLabel("OR");
        orLabel.setBounds(30, 330, 80, 25);
        orLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        loginPanel.add(orLabel);

        JLabel signUpLabel = new JLabel("Don't have Account?");
        signUpLabel.setBounds(30, 370, 150, 25);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 15));
        loginPanel.add(signUpLabel);

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(185, 368, 100, 25);
        signUp.setFont(new Font("Arial", Font.BOLD, 15));
        signUp.setBackground(new Color(5, 245, 209));
        signUp.setOpaque(true);
        signUp.setUI(new BasicButtonUI());
        loginPanel.add(signUp);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 90, 150, 25);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 130, 200, 25);
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 175, 150, 25);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 215, 200, 25);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(80, 276, 100, 25);
        loginButton.setBackground(new Color(5, 97, 245));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setOpaque(true);
        loginButton.setUI(new BasicButtonUI());
        loginPanel.add(loginButton);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Both username and password must be filled in.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Perform login action here
                    if (username.equals("user") && password.equals("pass")) { // Sample verification
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add panels to the frame
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(loginPanel, BorderLayout.WEST);

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}


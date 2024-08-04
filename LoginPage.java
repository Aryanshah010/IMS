import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 

    public static void main(String[] args) {

        JFrame frame = new JFrame("Login Page");
        frame.setSize(820, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        JLabel photoLabel = new JLabel();
        ImageIcon icon = new ImageIcon("/Users/aryanshah/Desktop/Inventify/resources/PROJECT1.jpg");

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

        JLabel mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(30, 90, 150, 25);
        mobileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loginPanel.add(mobileLabel);

        JTextField mobileField = new JTextField();
        mobileField.setBounds(30, 130, 200, 25);
        loginPanel.add(mobileField);

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
                String username = mobileField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Both mobile number and password must be filled in.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!isTableExists()) {
                    JOptionPane.showMessageDialog(frame, "No registered accounts found. Please sign up first.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (authenticateUser(username, password)) {
                        frame.dispose(); 
                        new Inv();
                        Inv.main(null);
                        
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid mobile number or password", "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Add action listener for the sign-up button
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                new SignUp();
                SignUp.main(null); 
            }
        });

        // Add panels to the frame
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(loginPanel, BorderLayout.WEST);
        
        frame.setVisible(true);
    }

    // Method to check if the users table exists
    private static boolean isTableExists() {
        String checkTableSQL = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'inventory' AND table_name = 'users'";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(checkTableSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to authenticate user credentials
    private static boolean authenticateUser(String mobile, String password) {
        String query = "SELECT * FROM users WHERE mobile = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mobile);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

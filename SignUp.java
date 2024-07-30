import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class SignUp {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root"; // replace with your DB username
    private static final String DB_PASSWORD = ""; // replace with your DB password

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 550);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel for the image
        JPanel leftPanel = new ImagePanel("/resources/PROJECT1.jpg");
        leftPanel.setPreferredSize(new Dimension(500, 0));
        leftPanel.setBackground(new Color(0xFFFFED));

        // Right panel container
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setBackground(new Color(0xADD8E6));

        // Right panel for form
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.PINK);
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(400, 450));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Form components
        JLabel registerLabel = new JLabel("Register Your Account", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        rightPanel.add(registerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        JTextField firstNameField = new JTextField(10);
        rightPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        JTextField lastNameField = new JTextField(10);
        rightPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(10);
        emailField.setBounds(30, 215, 200, 25);
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(mobileLabel, gbc);

        gbc.gridx = 1;
        JTextField mobileField = new JTextField(10);
        rightPanel.add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setBounds(30, 215, 200, 25);
        rightPanel.add(passwordField, gbc);

        // Load icons from the classpath
        ImageIcon eyeIcon = loadIcon("/resources/eye.png");
        ImageIcon eyeSlashIcon = loadIcon("/resources/eye_slash.png");

        // Add eye button to show/hide password
        JButton eyeButton = new JButton(eyeSlashIcon);
        eyeButton.setPreferredSize(new Dimension(20, 20));
        eyeButton.setContentAreaFilled(false);
        eyeButton.setBorderPainted(false);
        eyeButton.setFocusPainted(false);
        gbc.gridx = 2;
        rightPanel.add(eyeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(10);
        confirmPasswordField.setBounds(30, 215, 200, 25);
        rightPanel.add(confirmPasswordField, gbc);

        // Add eye button to show/hide confirm password
        JButton eyeButtonConfirm = new JButton(eyeSlashIcon);
        eyeButtonConfirm.setPreferredSize(new Dimension(20, 20));
        eyeButtonConfirm.setContentAreaFilled(false);
        eyeButtonConfirm.setBorderPainted(false);
        eyeButtonConfirm.setFocusPainted(false);
        gbc.gridx = 2;
        rightPanel.add(eyeButtonConfirm, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;

        // Create panel for buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setBackground(Color.PINK); // Match the right panel background

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(145, 75, 250));
        signUpButton.setPreferredSize(new Dimension(105, 30));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        signUpButton.setOpaque(true);
        signUpButton.setUI(new BasicButtonUI());
        buttonPanel.add(signUpButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(252, 76, 23));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setOpaque(true);
        cancelButton.setUI(new BasicButtonUI());
        buttonPanel.add(cancelButton);

        rightPanel.add(buttonPanel, gbc);

        // Center the right panel in the right container
        rightContainer.add(rightPanel, new GridBagConstraints());

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightContainer, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Action listener to toggle password visibility
        ActionListener togglePasswordVisibility = new ActionListener() {
            private boolean isPasswordVisible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                if (source == eyeButton) {
                    if (isPasswordVisible) {
                        passwordField.setEchoChar('*');
                        eyeButton.setIcon(eyeSlashIcon);
                    } else {
                        passwordField.setEchoChar((char) 0);
                        eyeButton.setIcon(eyeIcon);
                    }
                } else if (source == eyeButtonConfirm) {
                    if (isPasswordVisible) {
                        confirmPasswordField.setEchoChar('*');
                        eyeButtonConfirm.setIcon(eyeSlashIcon);
                    } else {
                        confirmPasswordField.setEchoChar((char) 0);
                        eyeButtonConfirm.setIcon(eyeIcon);
                    }
                }
                isPasswordVisible = !isPasswordVisible;
            }
        };

        eyeButton.addActionListener(togglePasswordVisibility);
        eyeButtonConfirm.addActionListener(togglePasswordVisibility);

        // Action listener to check mandatory fields and show dialog if necessary
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String mobile = mobileField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || mobile.isEmpty()
                        || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are mandatory.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!isValidMobileNumber(mobile)) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid 10-digit mobile number.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Proceed with the registration process
                    if (saveToDatabase(firstName, lastName, email, mobile, password)) {
                        JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose(); // Close the SignUp frame
                        new LoginPage();
                        LoginPage.main(null); // Open the LoginPage
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration Failed. Mobile number already taken!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the SignUp frame
                new LoginPage();
                LoginPage.main(null); // Open the LoginPage
            }
        });

        // Create the user table if it doesn't exist
        createTableIfNotExists();
    }

    // Method to validate email using regular expression
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate mobile number
    private static boolean isValidMobileNumber(String mobile) {
        return mobile.matches("\\d{10}");
    }

    // Helper method to load and scale icons
    private static ImageIcon loadIcon(String path) {
        try {
            BufferedImage img = ImageIO.read(SignUp.class.getResource(path));
            Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Inner class for custom JPanel to display image
    static class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(String imagePath) {
            try {
                image = ImageIO.read(SignUp.class.getResource(imagePath));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Method to create the user table if it doesn't exist
    private static void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "first_name VARCHAR(255), " +
                "last_name VARCHAR(255), " +
                "email VARCHAR(255), " +
                "mobile VARCHAR(10) PRIMARY KEY, " +
                "password VARCHAR(255))";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save user data to the database
    private static boolean saveToDatabase(String firstName, String lastName, String email, String mobile,
            String password) {
        if (isMobileNumberTaken(mobile)) {
            return false;
        }
        String insertSQL = "INSERT INTO users (first_name, last_name, email, mobile, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, mobile);
            preparedStatement.setString(5, password);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if mobile number is already taken
    private static boolean isMobileNumberTaken(String mobile) {
        String query = "SELECT mobile FROM users WHERE mobile = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mobile);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

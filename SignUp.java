import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full screen

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel for the image
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout()); // Use BorderLayout to fit the image to the panel
        leftPanel.setPreferredSize(new Dimension(599, 0)); // Set width for the left panel

        // Add the image to the left panel
        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\DELL\\Desktop\\inventory.jpg").getImage().getScaledInstance(600, -1, Image.SCALE_SMOOTH)));
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Right panel container
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setBackground(Color.WHITE); // Set background to white

        // Right panel for form
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.PINK);
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(400, 400)); // Set preferred size for the form panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;  // Center the form components

        // Form components
        JLabel registerLabel = new JLabel("Register Your Account", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        rightPanel.add(registerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        rightPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(new JTextField(10), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(new JTextField(10), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Your Personal E-mail:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(10);
        rightPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Mobile No.:"), gbc);
        gbc.gridx = 1;
        rightPanel.add(new JTextField(10), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(10);
        rightPanel.add(passwordField, gbc);

        // Load icons
        ImageIcon eyeIcon = new ImageIcon(new ImageIcon("C:\\Users\\DELL\\Desktop\\eye.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon eyeSlashIcon = new ImageIcon(new ImageIcon("C:\\Users\\DELL\\Desktop\\eye_slash.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // Add eye button to show/hide password
        JButton eyeButton = new JButton(eyeIcon);
        eyeButton.setPreferredSize(new Dimension(20, 20));
        gbc.gridx = 2;
        rightPanel.add(eyeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(10);
        rightPanel.add(confirmPasswordField, gbc);

        // Add eye button to show/hide confirm password
        JButton eyeButtonConfirm = new JButton(eyeIcon);
        eyeButtonConfirm.setPreferredSize(new Dimension(20, 20));
        gbc.gridx = 2;
        rightPanel.add(eyeButtonConfirm, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.decode("#ADD8E6")); // Set the background color using a hex color code
        signUpButton.setForeground(Color.WHITE); // Set the text color
        rightPanel.add(signUpButton, gbc);

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
                        eyeButton.setIcon(eyeIcon);
                    } else {
                        passwordField.setEchoChar((char) 0);
                        eyeButton.setIcon(eyeSlashIcon);
                    }
                } else if (source == eyeButtonConfirm) {
                    if (isPasswordVisible) {
                        confirmPasswordField.setEchoChar('*');
                        eyeButtonConfirm.setIcon(eyeIcon);
                    } else {
                        confirmPasswordField.setEchoChar((char) 0);
                        eyeButtonConfirm.setIcon(eyeSlashIcon);
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
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Email, Password, and Confirm Password fields are mandatory.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Proceed with the registration process
                    JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    // Method to validate email using regular expression
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
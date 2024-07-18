import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProduct extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

    public EditProduct() {

        setTitle("INVENTIFY");
        setSize(580, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(new Color(245, 164, 241));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE; // Do not allow components to fill space

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Add Product ID label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setFont(labelFont); // Set font
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(175, 30)); // Set preferred size for width
        panel.add(idField, gbc);

        // Add Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 17));
        searchButton.setPreferredSize(new Dimension(105, 30)); // Set preferred size
        searchButton.setBackground(new Color(12, 232, 111)); // Set background color
        searchButton.setOpaque(true);
        searchButton.setUI(new BasicButtonUI());

        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        // Add Product Name label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setFont(labelFont); // Set font
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(175, 30)); // Set preferred size for width
        panel.add(nameField, gbc);

        // Add Price label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont); // Set font
        panel.add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(175, 30)); // Set preferred size for width
        panel.add(priceField, gbc);

        // Add Quantity label and text field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont); // Set font
        panel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(175, 30)); // Set preferred size for width
        panel.add(quantityField, gbc);

        // Add Update button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 17));
        updateButton.setPreferredSize(new Dimension(105, 30));
        updateButton.setBackground(new Color(7, 187, 242));
        updateButton.setOpaque(true);
        updateButton.setUI(new BasicButtonUI());

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(updateButton, gbc);

        // Add Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 17));
        deleteButton.setPreferredSize(new Dimension(105, 30));
        deleteButton.setBackground(new Color(255, 41, 41));
        deleteButton.setOpaque(true);
        deleteButton.setUI(new BasicButtonUI());

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(deleteButton, gbc);

        // Add Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(100, 25));
        backButton.setBackground(new Color(255, 119, 41));
        backButton.setOpaque(true);
        backButton.setUI(new BasicButtonUI());

        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(backButton, gbc);

        // Add panel to the frame and position it at the top
        add(panel, BorderLayout.NORTH);

        // Add action listener to validate and proceed
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    JOptionPane.showMessageDialog(EditProduct.this, "All inputs are valid. Proceeding...");
                }
            }
        };

        updateButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);

        // Add action listener to back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                SwingUtilities.invokeLater(() -> {
                    Inv InvFrame = new Inv(); // Replace Inv with your class name
                    InvFrame.setVisible(true); // Open the Inv window
                });
            }
        });
    }

    private boolean validateInputs() {
        if (isFieldEmpty(idField.getText()) || isFieldEmpty(nameField.getText()) || isFieldEmpty(priceField.getText())
                || isFieldEmpty(quantityField.getText())) {
            showError("All fields must be filled.");
            return false;
        }
        if (!isInteger(idField.getText())) {
            showError("Product ID must be an integer.");
            return false;
        }
        if (!isTextNumberWithSpace(nameField.getText())) {
            showError("Product Name must be text, numbers, and spaces only.");
            return false;
        }
        if (!isFloat(priceField.getText())) {
            showError("Price must be a float or integer.");
            return false;
        }
        if (!isInteger(quantityField.getText())) {
            showError("Quantity must be an integer.");
            return false;
        }
        return true;
    }

    private boolean isFieldEmpty(String text) {
        return text.trim().isEmpty();
    }

    private boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isTextNumberWithSpace(String text) {
        return text.matches("[a-zA-Z0-9 ]+");
    }

    private boolean isFloat(String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditProduct frame = new EditProduct();
            frame.setVisible(true);
        });
    }
}
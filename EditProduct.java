import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        gbc.insets = new Insets(15, 15, 15, 15); 
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE; 

        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Add Product ID label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Product ID:");
        idLabel.setFont(labelFont); 
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(175, 30)); 
        panel.add(idField, gbc);

        // Add Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 17));
        searchButton.setPreferredSize(new Dimension(105, 30)); 
        searchButton.setBackground(new Color(12, 232, 111)); 
        searchButton.setOpaque(true);
        searchButton.setUI(new BasicButtonUI());

        gbc.gridx = 2;
        panel.add(searchButton, gbc);

        // Add Product Name label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setFont(labelFont); 
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(175, 30)); 
        panel.add(nameField, gbc);

        // Add Price label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont); 
        panel.add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(175, 30)); 
        panel.add(priceField, gbc);

        // Add Quantity label and text field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont); 
        panel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(175, 30)); 
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

        // Add action listener to search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });

        // Add action listener to update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndUpdateProduct();
            }
        });

        // Add action listener to delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndDeleteProduct();
            }
        });

        // Add action listener to back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                SwingUtilities.invokeLater(() -> {
                    Inv invFrame = new Inv();
                    invFrame.setVisible(true);
                });
            }
        });
    }

    private void searchProduct() {
        String id = idField.getText();
        if (id.isEmpty() || !isInteger(id)) {
            showError("Please enter a valid Product ID.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM Product WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    nameField.setText(resultSet.getString("Product_Name"));
                    priceField.setText(resultSet.getString("Price"));
                    quantityField.setText(resultSet.getString("Quantity"));

                    idField.setEditable(false);
                } else {
                    showError("Product not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmAndUpdateProduct() {
        if (isFieldEmpty(idField.getText())) {
            showError("Product ID cannot be empty.");
            return;
        }

        if (!validateInputs()) {
            return;
        }

        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this product?",
                "Confirm Update", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            updateProduct();
        }
    }

    private void updateProduct() {
        String id = idField.getText();
        String name = nameField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();

        if (!productExists(Integer.parseInt(id))) {
            showError("Product not found.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE Product SET Product_Name = ?, Price = ?, Quantity = ? WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setDouble(2, Double.parseDouble(price));
                statement.setInt(3, Integer.parseInt(quantity));
                statement.setInt(4, Integer.parseInt(id));
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Product updated successfully.");
                dispose(); 
                SwingUtilities.invokeLater(() -> {
                    Inv invFrame = new Inv();
                    invFrame.setVisible(true);
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmAndDeleteProduct() {
        if (isFieldEmpty(idField.getText())) {
            showError("Product ID cannot be empty.");
            return;
        }

        String id = idField.getText();
        if (!isInteger(id)) {
            showError("Product ID must be an integer.");
            return;
        }

        // Check if the product exists before asking for confirmation
        if (!productExists(Integer.parseInt(id))) {
            showError("Product not found.");
            return;
        }

        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            deleteProduct();
        }
    }

    private void deleteProduct() {
        String id = idField.getText();
    
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";
    
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Check if there are associated orders
            String checkQuery = "SELECT COUNT(*) FROM Orders WHERE Product_ID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = checkStatement.executeQuery();
    
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    showError("Product cannot be deleted as it has associated to orders.");
                    return;
                }
            }
    
            // Proceed with deletion
            String deleteQuery = "DELETE FROM Product WHERE Product_ID = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, Integer.parseInt(id));
                deleteStatement.executeUpdate();
    
                JOptionPane.showMessageDialog(this, "Product deleted successfully.");
                dispose(); 
                SwingUtilities.invokeLater(() -> {
                    Inv invFrame = new Inv();
                    invFrame.setVisible(true);
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private boolean productExists(int productId) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT COUNT(*) FROM Product WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, productId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditProduct frame = new EditProduct();
            frame.setVisible(true);
        });
    }
}

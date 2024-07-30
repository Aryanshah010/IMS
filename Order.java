import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Order {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("INVENTIFY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(545, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(245, 164, 241));
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;

        Font labelFont = new Font("Arial", Font.BOLD, 15);

        JLabel orderIdLabel = new JLabel("Order ID:");
        JTextField orderIdField = new JTextField(15);
        orderIdField.setEditable(false);
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField(15);
        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField(15);
        productNameField.setEditable(false);
        JLabel productQuantityLabel = new JLabel("Product Quantity:");
        JTextField productQuantityField = new JTextField(15);
        JLabel productPriceLabel = new JLabel("Total Price:");
        JTextField productPriceField = new JTextField(15);

        setLabelFont(orderIdLabel, labelFont);
        setLabelFont(productIdLabel, labelFont);
        setLabelFont(productNameLabel, labelFont);
        setLabelFont(productQuantityLabel, labelFont);
        setLabelFont(productPriceLabel, labelFont);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(orderIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(orderIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(productIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(productIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(productNameLabel, gbc);
        gbc.gridx = 1;
        panel.add(productNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(productQuantityLabel, gbc);
        gbc.gridx = 1;
        panel.add(productQuantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(productPriceLabel, gbc);
        gbc.gridx = 1;
        panel.add(productPriceField, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(100, 25));
        searchButton.setBackground(new Color(12, 232, 111));
        searchButton.setOpaque(true);
        searchButton.setUI(new BasicButtonUI());

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(searchButton, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = productIdField.getText().trim();

                if (isInteger(productId)) {
                    fillProductDetails(productId, productNameField, productQuantityField, productPriceField);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Product ID format. Please enter a valid integer.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton confirmButton = new JButton("Confirm Order");
        confirmButton.setBackground(new Color(56, 127, 242));
        confirmButton.setPreferredSize(new Dimension(155, 30));
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setOpaque(true);
        confirmButton.setUI(new BasicButtonUI());

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = orderIdField.getText().trim();
                String productId = productIdField.getText().trim();
                String productName = productNameField.getText().trim();
                String productPrice = productPriceField.getText().trim();
                String productQuantity = productQuantityField.getText().trim();

                if (orderId.isEmpty() || productId.isEmpty() || productName.isEmpty() ||
                        productPrice.isEmpty() || productQuantity.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the fields!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isInteger(orderId) || !isInteger(productId) || !isFloat(productPrice)
                        || !isInteger(productQuantity)) {
                    JOptionPane.showMessageDialog(frame, "Invalid input format. Please check your input.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isProductDetailsFetched(productId, productName)) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter Product ID and press Search button to fill the form.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int enteredQuantity = Integer.parseInt(productQuantity);
                int availableQuantity = getAvailableQuantity(productId);

                if (enteredQuantity <= 0) {
                    JOptionPane.showMessageDialog(frame, "Product quantity must be greater than zero.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (enteredQuantity > availableQuantity) {
                    JOptionPane.showMessageDialog(frame, "Quantity cannot exceed inventory product's quantity.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double enteredPrice = Double.parseDouble(productPrice);

                if (enteredPrice <= 0) {
                    JOptionPane.showMessageDialog(frame, "Product price must be greater than zero.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int response = JOptionPane.showConfirmDialog(frame, "Do you want to confirm this order?", "Confirm Order",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    // Insert order into the database
                    if (insertOrder(Integer.parseInt(orderId), Integer.parseInt(productId), productName, enteredQuantity, enteredPrice)) {
                        JOptionPane.showMessageDialog(frame, "Order confirmed successfully!");
                        frame.dispose();
                        SwingUtilities.invokeLater(() -> {
                            Inv invFrame = new Inv();
                            invFrame.setVisible(true);
                        });
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to confirm order. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(confirmButton, gbc);

        JButton cancelButton = new JButton("Back");
        cancelButton.setBackground(new Color(242, 48, 68));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setOpaque(true);
        cancelButton.setUI(new BasicButtonUI());

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(cancelButton, gbc);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> {
                    Inv invFrame = new Inv();
                    invFrame.setVisible(true);
                });
            }
        });

        frame.add(panel, BorderLayout.CENTER);

        // Initialize database and create Orders table if not exists
        initializeDatabase();

        // Fetch and set the latest Order ID when the frame is shown
        SwingUtilities.invokeLater(() -> fetchAndSetOrderId(orderIdField));

        frame.setVisible(true);
    }

    private void setLabelFont(JLabel label, Font font) {
        label.setFont(font);
    }

    private boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void fillProductDetails(String productId, JTextField nameField, JTextField quantityField,
            JTextField priceField) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT Product_Name, Price, Quantity FROM Product WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(productId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nameField.setText(resultSet.getString("Product_Name"));
                        quantityField.setText(String.valueOf(resultSet.getInt("Quantity")));
                        priceField.setText(String.valueOf(resultSet.getDouble("Price")));
                    } else {
                        JOptionPane.showMessageDialog(null, "Product ID not found.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getAvailableQuantity(String productId) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT Quantity FROM Product WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(productId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("Quantity");
                    } else {
                        return 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private boolean isProductDetailsFetched(String productId, String productName) {
        if (productName.isEmpty()) {
            return false;
        }

        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT Product_Name FROM Product WHERE Product_ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(productId));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return productName.equals(resultSet.getString("Product_Name"));
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initializeDatabase() {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Orders (" +
                    "Order_ID INT PRIMARY KEY, " +
                    "Product_ID INT, " +
                    "Product_Name VARCHAR(50), " +
                    "Quantity INT, " +
                    "Price DECIMAL(10, 2), " +
                    "FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID))";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchAndSetOrderId(JTextField orderIdField) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement()) {
            String query = "SELECT MAX(Order_ID) AS max_order_id FROM Orders";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    int maxOrderId = resultSet.getInt("max_order_id");
                    orderIdField.setText(String.valueOf(maxOrderId + 1));
                } else {
                    orderIdField.setText("1");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean insertOrder(int orderId, int productId, String productName, int quantity, double price) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Orders (Order_ID, Product_ID, Product_Name, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderId);
                statement.setInt(2, productId);
                statement.setString(3, productName);
                statement.setInt(4, quantity);
                statement.setDouble(5, price);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Order orderForm = new Order();
            orderForm.createAndShowGUI();
        });
    }
}

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Order {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("INVENTIFY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 400);
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

        // Define the font
        Font labelFont = new Font("Arial", Font.BOLD, 15);

        // Create labels and text fields
        JLabel orderIdLabel = new JLabel("Order ID:");
        JTextField orderIdField = new JTextField(15);
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField(15);
        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField(15);
        JLabel productQuantityLabel = new JLabel("Product Quantity:");
        JTextField productQuantityField = new JTextField(15);
        JLabel productPriceLabel = new JLabel("Total Price:");
        JTextField productPriceField = new JTextField(15);

        // Set the font for all labels
        setLabelFont(orderIdLabel, labelFont);
        setLabelFont(productIdLabel, labelFont);
        setLabelFont(productNameLabel, labelFont);
        setLabelFont(productQuantityLabel, labelFont);
        setLabelFont(productPriceLabel, labelFont);

        // Add components to the panel
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

        // Confirm Order Button
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

                // Validate fields
                if (orderId.isEmpty() || productId.isEmpty() || productName.isEmpty() ||
                        productPrice.isEmpty() || productQuantity.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the fields!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate input formats
                if (!isInteger(orderId) || !isInteger(productId) || !isFloat(productPrice)
                        || !isInteger(productQuantity)) {
                    JOptionPane.showMessageDialog(frame, "Invalid input format. Please check your input.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If all validations pass
                JOptionPane.showMessageDialog(frame, "Order confirmed successfully!");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST; // Align the button to the left
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
        gbc.anchor = GridBagConstraints.WEST; // Align the button to the left
        panel.add(cancelButton, gbc);

        // Add action listener to back button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose(); // Close the current window
                SwingUtilities.invokeLater(() -> {
                    Inv InvFrame = new Inv(); // Replace Inv with your class name
                    InvFrame.setVisible(true); // Open the Inv window
                });
            }
        });

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Set frame to be visible
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

    public Component createSalesPanel() {

        throw new UnsupportedOperationException("Unimplemented method 'createSalesPanel'");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Order order = new Order();
            order.createAndShowGUI();

        });
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagement extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public InventoryManagement() {
        // Set up the frame
        setTitle("Inventory Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton dashboardButton = createSidebarButton("Dashboard");
        JButton salesOrderButton = createSidebarButton("Sales Orders");
        JButton suppliersButton = createSidebarButton("Suppliers");

        sidebar.add(dashboardButton);
        sidebar.add(salesOrderButton);
        sidebar.add(suppliersButton);

        // Create the table
        String[] columnNames = {"Product ID", "Product Name", "Price", "Quantity"};
        Object[][] data = {
                {"1", "Product 1", "100.00", "20"},
                {"2", "Product 2", "200.00", "15"},
                {"3", "Product 3", "150.00", "30"},
                {"4", "Product 4", "250.00", "10"},
                {"5", "Product 5", "300.00", "5"},
                {"6", "Product 6", "350.00", "8"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBackground(new Color(230, 230, 230));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(700, 400));
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create the form to add new products
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));
        formPanel.setBackground(new Color(173, 216, 230)); // Light blue background

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String price = priceField.getText();
                String quantity = quantityField.getText();

                if (!id.isEmpty() && !name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                    tableModel.addRow(new Object[]{id, name, price, quantity});
                    idField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(InventoryManagement.this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(addButton);

        // Add logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        JLabel logoLabel = new JLabel(new ImageIcon("inventify_logo.png"));
        logoPanel.add(logoLabel);

        // Add components to the frame
        add(sidebar, BorderLayout.WEST);
        add(logoPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setBackground(new Color(100, 100, 100));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(new NavigationButtonListener());
        return button;
    }

    private class NavigationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(InventoryManagement.this, "Navigate to " + source.getText());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagement frame = new InventoryManagement();
            frame.setVisible(true);
        });
    }
}

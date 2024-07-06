import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class inv extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public inv() {

        setTitle("INVENTIFY");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(129, 219, 200));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel dashboardLabel = createSidebarLabel("Dashboard");
        JLabel salesLabel = createSidebarLabel("Sales");
        JLabel inventoryLabel = createSidebarLabel("Inventory");
        JLabel orderLabel = createSidebarLabel("Order");

        sidebar.add(dashboardLabel);
        sidebar.add(salesLabel);
        sidebar.add(inventoryLabel);
        sidebar.add(orderLabel);

        // Main content area with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createDashboardPanel(), "Dashboard");
        mainPanel.add(createSalesPanel(), "Sales");
        mainPanel.add(createInventoryPanel(), "Inventory");
        mainPanel.add(createOrderPanel(), "Order");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(150);
        splitPane.setEnabled(false);

        // Add components to the frame
        add(splitPane, BorderLayout.CENTER);
    }

    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        label.setForeground(Color.BLACK);
        label.setOpaque(true);
        label.setBackground(new Color(129, 219, 200));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(23, 169, 227));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(129, 219, 200));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, text);
            }
        });
        return label;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // You can customize dashboard panel contents here if needed
        panel.add(new JLabel("Dashboard Page"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // You can customize dashboard panel contents here if needed
        panel.add(new JLabel("Order page"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create the table
        String[] columnNames = { "Product ID", "Product Name", "Price", "Quantity" };
        Object[][] data = {
                { "1", "Product 1", "100.00", "20" },
                { "2", "Product 2", "200.00", "15" },
                { "3", "Product 3", "150.00", "30" },
                { "4", "Product 4", "250.00", "10" },
                { "5", "Product 5", "300.00", "5" },
                { "6", "Product 6", "350.00", "8" }
        };

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBackground(new Color(240, 240, 240));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create the form to add new products
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));
        formPanel.setBackground(new Color(230, 240, 255));

        idField = new JTextField();
        nameField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();

        JLabel addButton = createFormLabel("Add Product");
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String price = priceField.getText();
                String quantity = quantityField.getText();

                if (!id.isEmpty() && !name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                    tableModel.addRow(new Object[] { id, name, price, quantity });
                    idField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(inv.this, "Please fill in all fields", "Error",
                            JOptionPane.ERROR_MESSAGE);
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

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSalesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Sales  Page"), BorderLayout.CENTER);
        return panel;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(new Color(0, 120, 215));
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(0, 150, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(0, 120, 215));
            }
        });
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            inv frame = new inv();
            frame.setVisible(true);
        });
    }
}

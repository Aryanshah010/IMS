import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inv extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

    public CardLayout cardLayout;
    JPanel mainPanel;

    public Inv() {

        setTitle("INVENTIFY");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(new Color(245, 164, 241));

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(247, 124, 247));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel dashboardLabel = createSidebarLabel("Dashboard");
        JLabel inventoryLabel = createSidebarLabel("Inventory");
        JLabel salesLabel = createSidebarLabel("Sales Order");

        sidebar.add(dashboardLabel);
        sidebar.add(inventoryLabel);
        sidebar.add(salesLabel);

        // Main content area with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createInventoryPanel(), "Inventory");

        // Add the Sales panel
        Sales sales = new Sales();
        mainPanel.add(sales.createSalesPanel(this), "Sales Order");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(150);
        splitPane.setEnabled(false);

        // Add components to the frame
        add(splitPane, BorderLayout.CENTER);

        // Add action listeners for the sidebar labels
        dashboardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Switch to dashboard panel
                cardLayout.show(mainPanel, "Dashboard");
            }
        });

        inventoryLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Switch to inventory panel
                cardLayout.show(mainPanel, "Inventory");
            }
        });

        salesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Switch to sales panel
                cardLayout.show(mainPanel, "Sales Order");
            }
        });
    }

    private JLabel createSidebarLabel(String text) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        label.setForeground(Color.BLACK);
        label.setOpaque(true);
        label.setBackground(new Color(247, 124, 247));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(23, 169, 227));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(247, 124, 247));
            }

        });
        return label;
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

        // Create a non-editable table model
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 19));
        tableHeader.setReorderingAllowed(false);

        table.setBackground(new Color(240, 240, 240));
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 17));
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create the form to add new products
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        formPanel.setBackground(new Color(230, 240, 255));

        idField = new JTextField();
        nameField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();

        JLabel editButton = createFormLabel("Edit Product", new Color(255, 184, 108));
        JLabel addButton = createFormLabel("Add Product", new Color(0, 120, 215));

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String price = priceField.getText();
                String quantity = quantityField.getText();

                if (!id.isEmpty() && !name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                    if (id.matches("\\d+") && name.matches("[\\w\\s]+") && price.matches("\\d*\\.?\\d+")
                            && quantity.matches("\\d+")) {
                        tableModel.addRow(new Object[] { id, name, price, quantity });
                        idField.setText("");
                        nameField.setText("");
                        priceField.setText("");
                        quantityField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(Inv.this,
                                "Invalid input format. Please ensure:\n- Product ID and Quantity are integers\n- Price is a float\n- Product Name is alphanumeric",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Inv.this, "Please fill in all fields", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Close the current window
                SwingUtilities.invokeLater(() -> {
                    EditProduct editProductFrame = new EditProduct();
                    editProductFrame.setVisible(true);
                });
            }
        });

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel(""));
        formPanel.add(editButton);
        formPanel.add(addButton);

        // Set the font for all form panel components
        Font formFont = new Font("Arial", Font.BOLD, 16);
        setFormPanelFont(formPanel, formFont);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createFormLabel(String text, Color bgColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(bgColor);
            }
        });
        return label;
    }

    private void setFormPanelFont(Container container, Font font) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel || component instanceof JTextField) {
                component.setFont(font);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Inv frame = new Inv();
            frame.setVisible(true);
        });
    }
}
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;

public class Dashboard extends JPanel {

    private JLabel productRevenueLabel;
    private JLabel totalSalesLabel;
    private JTable table;
    private DefaultTableModel model;
    private Timer timer;

    public Dashboard() {
        setLayout(new BorderLayout());

        // Top panel for total sales and product revenue
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(224, 119, 209));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        infoPanel.setBackground(new Color(224, 119, 209));

        totalSalesLabel = new JLabel("Total Sales: Rs.0.00");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalSalesLabel.setForeground(Color.BLACK);
        totalSalesLabel.setOpaque(true);
        totalSalesLabel.setBackground(new Color(173, 216, 230));
        totalSalesLabel.setPreferredSize(new Dimension(400, 50));
        totalSalesLabel.setHorizontalAlignment(SwingConstants.LEFT);

        productRevenueLabel = new JLabel("Product Purchased: Rs.0.00");
        productRevenueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        productRevenueLabel.setForeground(Color.BLACK);
        productRevenueLabel.setOpaque(true);
        productRevenueLabel.setBackground(new Color(173, 216, 230));
        productRevenueLabel.setPreferredSize(new Dimension(400, 50));
        productRevenueLabel.setHorizontalAlignment(SwingConstants.LEFT);

        infoPanel.add(totalSalesLabel);
        infoPanel.add(productRevenueLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(242, 48, 68));
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setOpaque(true);
        logoutButton.setUI(new BasicButtonUI());

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 25));
        logoutPanel.setBackground(new Color(224, 119, 209));
        logoutPanel.add(logoutButton);

        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(logoutPanel, BorderLayout.EAST);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(Dashboard.this, "Do you want to logout?", "Confirm Logout",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    SwingUtilities.getWindowAncestor(Dashboard.this).dispose();
                    SwingUtilities.invokeLater(() -> {
                        new LoginPage();
                        LoginPage.main(null);
                    });
                }
            }
        });

        // Center panel for table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(134, 136, 252));

        JLabel topSellingProductLabel = new JLabel("Top Selling Products");
        topSellingProductLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topSellingProductLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(topSellingProductLabel, BorderLayout.NORTH);

        String[] columnNames = { "Product Id", "Product Name", "Quantity", "Total Price" };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes all cells non-editable
            }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 17));
        table.setSelectionBackground(new Color(250, 160, 75));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.getTableHeader().setReorderingAllowed(false);

        // Apply currency formatting to the "Total Price" column
        table.getColumnModel().getColumn(3).setCellRenderer(new CurrencyCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Fetch and display the product purchased amount and total sales amount initially
        updateProductPurchasedAmount();
        updateTotalSalesAmount();
        updateTopSellingProducts();

        // Set up a timer to update the amounts periodically (every 5 seconds)
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductPurchasedAmount();
                updateTotalSalesAmount();
                updateTopSellingProducts();
            }
        });
        timer.start();
    }

    private void updateProductPurchasedAmount() {
        double totalAmount = 0.0;
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT Price, Quantity FROM Product";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    double price = resultSet.getDouble("Price");
                    int quantity = resultSet.getInt("Quantity");
                    totalAmount += price * quantity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        productRevenueLabel.setText(String.format("Product Purchased: Rs.%.2f", totalAmount));
    }

    private void updateTotalSalesAmount() {
        double totalSales = 0.0;
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT Price FROM Orders";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    double price = resultSet.getDouble("Price");
                    totalSales += price;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        totalSalesLabel.setText(String.format("Total Sales: Rs.%.2f", totalSales));
    }

    private void updateTopSellingProducts() {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT p.Product_ID, p.Product_Name, SUM(o.Quantity) AS TotalQuantity, SUM(o.Price) AS TotalPrice " +
                    "FROM Orders o " +
                    "JOIN Product p ON o.Product_ID = p.Product_ID " +
                    "GROUP BY p.Product_ID, p.Product_Name " +
                    "ORDER BY TotalPrice DESC " +
                    "LIMIT 5";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                // Clear the existing data in the table model
                model.setRowCount(0);

                // Add new rows to the table model
                while (resultSet.next()) {
                    int productId = resultSet.getInt("Product_ID");
                    String productName = resultSet.getString("Product_Name");
                    int quantity = resultSet.getInt("TotalQuantity");
                    double totalPrice = resultSet.getDouble("TotalPrice");

                    model.addRow(new Object[] { productId, productName, quantity, totalPrice });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Custom cell renderer for formatting numbers as currency
    private static class CurrencyCellRenderer extends DefaultTableCellRenderer {
        private static final DecimalFormat format = new DecimalFormat("#,##0.00");
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Number) {
                value = format.format((Number) value);
            }
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(table.getFont()); 
            return c;
        }
    }
}

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Sales {

    public JPanel createSalesPanel(JFrame parentFrame) {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel with search and add orders
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(searchPanel, BorderLayout.CENTER);

        JButton addOrderButton = new JButton("Add Orders");
        addOrderButton.setFont(new Font("Arial", Font.BOLD, 15));
        addOrderButton.setPreferredSize(new Dimension(120, 30));
        addOrderButton.setBackground(new Color(128, 52, 235));
        addOrderButton.setOpaque(true);
        addOrderButton.setUI(new BasicButtonUI());
        addOrderButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.dispose(); // Close the current Sales window
                SwingUtilities.invokeLater(() -> {
                    Order order = new Order();
                    order.createAndShowGUI(); // Open the Order form
                });
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addOrderButton);
        buttonPanel.setBackground(topPanel.getBackground());

        topPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center panel with table
        String[] columns = { "Order Id", "Product Id", "Product Name", "Quantity", "Total Price" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        JTable salesTable = new JTable(tableModel);

        // Disable column reordering
        salesTable.getTableHeader().setReorderingAllowed(false);

        // Apply custom font to the header
        Font headerFont = new Font("Arial", Font.BOLD, 16);
        salesTable.getTableHeader().setFont(headerFont);

        // Create and set custom cell renderer for the table cells
        Font cellFont = new Font("Arial", Font.PLAIN, 14);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(cellFont);
                return c;
            }
        };

        // Apply the renderer to all columns
        for (int i = 0; i < salesTable.getColumnCount(); i++) {
            salesTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Set preferred viewport size to enable vertical scroll bar
        salesTable.setPreferredScrollableViewportSize(new Dimension(700, 200));

        JScrollPane tableScrollPane = new JScrollPane(salesTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Fetch and set data for the table
        fetchAndSetSalesData(tableModel);

        return panel;
    }

    private void fetchAndSetSalesData(DefaultTableModel tableModel) {
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM Orders";
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {

                // Clear the existing data in the table model
                tableModel.setRowCount(0);

                // Add new rows to the table model
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("Order_ID");
                    int productId = resultSet.getInt("Product_ID");
                    String productName = resultSet.getString("Product_Name");
                    int quantity = resultSet.getInt("Quantity");
                    float price = resultSet.getFloat("Price");

                    tableModel.addRow(new Object[] { orderId, productId, productName, quantity, price });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaySalesPage() {
        JFrame frame = new JFrame("Sales Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(createSalesPanel(frame));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Sales().displaySalesPage();
        });
    }
}

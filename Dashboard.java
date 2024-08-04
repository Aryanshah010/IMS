import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JPanel {

    public Dashboard() {
        setLayout(new BorderLayout());

        // Top panel for total sales and product revenue
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(224, 119, 209));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        infoPanel.setBackground(new Color(224, 119, 209));

        JLabel totalSalesLabel = new JLabel("Total Sales: Rs.36,631.89");
        totalSalesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalSalesLabel.setForeground(Color.BLACK);
        totalSalesLabel.setOpaque(true);
        totalSalesLabel.setBackground(new Color(173, 216, 230));
        totalSalesLabel.setPreferredSize(new Dimension(250, 50));
        totalSalesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel productRevenueLabel = new JLabel("Product Purchased: Rs.6,342.27");
        productRevenueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        productRevenueLabel.setForeground(Color.BLACK);
        productRevenueLabel.setOpaque(true);
        productRevenueLabel.setBackground(new Color(173, 216, 230));
        productRevenueLabel.setPreferredSize(new Dimension(300, 50));
        productRevenueLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
                    // Dispose of the current frame
                    SwingUtilities.getWindowAncestor(Dashboard.this).dispose();
        
                    // Open the login page
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

        String[] columnNames = { "Product Id", "Product Name", "Quantity", "Profit" };
        Object[][] data = {
                { "1", "Poco", "92", "28.27" },
                { "1002", "Mi", "77", "21.75" },
                { "1004", "Macbook", "21", "19.30" },
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes all cells non-editable
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 17));
        table.setSelectionBackground(new Color(250, 160, 75));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}

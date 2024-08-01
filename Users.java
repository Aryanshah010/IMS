import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Users {

    public JPanel createUsersPanel(JFrame parentFrame) {
        JPanel panel = new JPanel(new BorderLayout());

        // Top panel with search and add orders
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(searchPanel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

        // Center panel with table
        String[] columns = { "First Name", "Last Name", "E-mail", "Mobile Number" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        JTable usersTable = new JTable(tableModel);

        // Disable column reordering
        usersTable.getTableHeader().setReorderingAllowed(false);

        // Apply custom font to the header
        Font headerFont = new Font("Arial", Font.BOLD, 16);
        usersTable.getTableHeader().setFont(headerFont);

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
        for (int i = 0; i < usersTable.getColumnCount(); i++) {
            usersTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Set preferred viewport size to enable vertical scroll bar
        usersTable.setPreferredScrollableViewportSize(new Dimension(700, 200));

        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Fetch and set data for the table
        // Add code to fetch data from database and populate the table

        return panel;
    }

    public void displayPage() {
        JFrame frame = new JFrame("Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(createUsersPanel(frame));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Users().displayPage();
        });
    }
}

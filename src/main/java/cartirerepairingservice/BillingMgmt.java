package cartirerepairingservice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mycompany.cartirerepairingservice.Cartirerepairingservice;


public class BillingMgmt extends JFrame {
    // Class variables (components)
    private JButton addButton, editButton, deleteButton;
    private JTextField jobIdField, amountField, paymentStatusField, paymentMethodField;
    private JTable billingsTable;
    private DefaultTableModel model;

    public BillingMgmt() {
        initUIComponents();
        setTitle("Billing Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        refreshTable(); 
    }
    
    private void refreshTable() {
        try {
            // Fetch the updated billing list from the database
            List<Billing> allBillings = Cartirerepairingservice.listAllBillings();
            model.setRowCount(0); // Clear the existing table content

            // Populate the table model with billing data
            for (Billing bill : allBillings) {
                model.addRow(new Object[]{
                        bill.getBillId(),
                        bill.getJobId(),
                        bill.getAmount(),
                        bill.getPaymentStatus(),
                        bill.getPaymentMethod()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error refreshing billing table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    private void initUIComponents() {
        // Set Layout
        setLayout(new BorderLayout());

        // Initialize UI Components
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        jobIdField = new JTextField(15);
        amountField = new JTextField(15);
        paymentStatusField = new JTextField(15);
        paymentMethodField = new JTextField(15);

        // Initialize JTable and model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Bill ID", "Job ID", "Amount", "Payment Status", "Payment Method"});
        billingsTable = new JTable(model);

        // Add selection model listener
        billingsTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = billingsTable.getSelectedRow();
            if (selectedRow >= 0) {
                jobIdField.setText(model.getValueAt(selectedRow, 1).toString());
                amountField.setText(model.getValueAt(selectedRow, 2).toString());
                paymentStatusField.setText((String) model.getValueAt(selectedRow, 3));
                paymentMethodField.setText((String) model.getValueAt(selectedRow, 4));
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Job ID:"));
        formPanel.add(jobIdField);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Payment Status:"));
        formPanel.add(paymentStatusField);
        formPanel.add(new JLabel("Payment Method:"));
        formPanel.add(paymentMethodField);


        // Add components to JFrame
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(billingsTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addBilling());
        editButton.addActionListener(e -> editBilling());
        deleteButton.addActionListener(e -> deleteBilling());
    }

    private void addBilling() {
        try {
            // Parse user input from text fields
            int jobId = Integer.parseInt(jobIdField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String paymentStatus = paymentStatusField.getText();
            String paymentMethod = paymentMethodField.getText();

            // Add the billing to the database
            Cartirerepairingservice.addBilling(jobId, amount, paymentStatus, paymentMethod);

            // Refresh the table to show the new billing
            refreshTable();

            JOptionPane.showMessageDialog(this, "Billing added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Job ID and Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }





private void editBilling() {
    int selectedRow = billingsTable.getSelectedRow();
    if (selectedRow != -1) { // Ensuring a row is selected
        try {
            int billId = (int) model.getValueAt(selectedRow, 0);
            int jobId = Integer.parseInt(jobIdField.getText());
            double amount = Double.parseDouble(amountField.getText());
            String paymentStatus = paymentStatusField.getText();
            String paymentMethod = paymentMethodField.getText();

            // Update the billing record
            Cartirerepairingservice.editBilling(billId, jobId, amount, paymentStatus, paymentMethod);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Billing record updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Job ID and Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editing billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a billing record to edit.");
    }
}




private void deleteBilling() {
    int selectedRow = billingsTable.getSelectedRow();
    if (selectedRow != -1) {  // Checking if a row is selected
        int billId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this billing record?", "Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Cartirerepairingservice.deleteBilling(billId);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Billing record deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting billing: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a billing record to delete.");
    }
}





public static void main(String[] args) {
    Cartirerepairingservice.initializeBillings(); // Ensure this initializes the billings correctly
    EventQueue.invokeLater(() -> {
        new BillingMgmt().setVisible(true);
    });
}



}

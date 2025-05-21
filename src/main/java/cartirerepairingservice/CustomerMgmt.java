package cartirerepairingservice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mycompany.cartirerepairingservice.Cartirerepairingservice;

public class CustomerMgmt extends JFrame {
    // Class variables (components)
    private JButton addButton, editButton, deleteButton;
    private JTextField nameField, contactField, vehicleField;
    private JTable customersTable;
    private DefaultTableModel model;

    public CustomerMgmt() {
    initUIComponents(); 
    setTitle("Customer Management");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    pack(); 
    setLocationRelativeTo(null); // Center on screen
    setVisible(true); // Make it visible
    refreshTable();
    }



    private void initUIComponents() {
        // Set Layout
        setLayout(new BorderLayout());

        // Initialize UI Components
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        nameField = new JTextField(15);
        contactField = new JTextField(15);
        vehicleField = new JTextField(15);

        // Initialize JTable and model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Customer ID", "Name", "Contact Number", "Vehicle Details"});
        customersTable = new JTable(model);

        // Add selection model listener
        customersTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = customersTable.getSelectedRow();
            if (selectedRow >= 0) {
                nameField.setText((String) model.getValueAt(selectedRow, 1));
                contactField.setText((String) model.getValueAt(selectedRow, 2));
                vehicleField.setText((String) model.getValueAt(selectedRow, 3));
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Vehicle:"));
        formPanel.add(vehicleField);

        // Add components to JFrame
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(customersTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
    }

private void addCustomer() {
    // Gather data from text fields
    String name = nameField.getText();
    String contact = contactField.getText();
    String vehicle = vehicleField.getText();
    
    // Insert customer into database
    // Assuming the database is set up to auto-increment ID, you don't need to provide it
    Cartirerepairingservice.addCustomer(name, contact, vehicle);

    // Update the table model
    refreshTable();
}





private void editCustomer() {
    int selectedRow = customersTable.getSelectedRow();
    if (selectedRow >= 0) {
        int customerId = (int) model.getValueAt(selectedRow, 0);
        String name = nameField.getText();
        String contact = contactField.getText();
        String vehicle = vehicleField.getText();

        // Update the customer in the database
        Cartirerepairingservice.editCustomer(customerId, name, contact, vehicle);

        // Update the table model
        refreshTable();
    } else {
        JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
    }
}




private void deleteCustomer() {
    int selectedRow = customersTable.getSelectedRow();
    if (selectedRow >= 0) {
        int customerId = (int) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Delete the customer from the database
            Cartirerepairingservice.deleteCustomer(customerId);

            // Update the table model
            refreshTable();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
    }
}



    // Refresh the JTable with updated customer list
private void refreshTable() {
    // Clear the existing data
    model.setRowCount(0);
    // Fetch updated list of customers from the database
    List<Customer> allCustomers = Cartirerepairingservice.listAllCustomers();
    // Add all customers to the model
    for (Customer cust : allCustomers) {
        model.addRow(new Object[]{cust.getCustomerId(), cust.getName(), cust.getContactNumber(), cust.getVehicleDetails()});
    }
}

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new CustomerMgmt().setVisible(true);
        });
    }
}

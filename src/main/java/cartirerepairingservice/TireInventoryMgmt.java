package cartirerepairingservice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mycompany.cartirerepairingservice.Cartirerepairingservice;

public class TireInventoryMgmt extends JFrame {
    // Class variables (components)
    private JButton addButton, editButton, deleteButton;
    private JTextField brandField, sizeField, typeField, quantityField;
    private JTable inventoryTable;
    private DefaultTableModel model;

    public TireInventoryMgmt() {
    initUIComponents(); 
    setTitle("Tire Inventory Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack(); 
    setLocationRelativeTo(null); 
    setVisible(true); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    refreshTable();
    }


    private void initUIComponents() {
        // Set Layout
        setLayout(new BorderLayout());

        // Initialize UI Components
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        brandField = new JTextField(15);
        sizeField = new JTextField(15);
        typeField = new JTextField(15);
        quantityField = new JTextField(15);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Tire ID", "Brand", "Size", "Type", "Quantity"});
        inventoryTable = new JTable(model);

        // Add selection model listener
        inventoryTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                brandField.setText((String) model.getValueAt(selectedRow, 1));
                sizeField.setText((String) model.getValueAt(selectedRow, 2));
                typeField.setText((String) model.getValueAt(selectedRow, 3));
                quantityField.setText(model.getValueAt(selectedRow, 4).toString());
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5)); 
        formPanel.add(new JLabel("Brand:"));
        formPanel.add(brandField);
        formPanel.add(new JLabel("Size:"));
        formPanel.add(sizeField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        // Add components to JFrame
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addTire());
        editButton.addActionListener(e -> editTire());
        deleteButton.addActionListener(e -> deleteTire());
    }

private void addTire() {
    try {
        String brand = brandField.getText();
        String size = sizeField.getText();
        String type = typeField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        // Assuming TireInventory.addTire() adds a tire to the database and auto-generates the ID
        Cartirerepairingservice.addTireInventory(new TireInventory(0, brand, size, type, quantity)); // 0 for auto-generated ID
        refreshTable();
        JOptionPane.showMessageDialog(this, "Tire added successfully!");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error adding tire: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}



private void editTire() {
    int selectedRow = inventoryTable.getSelectedRow();
    if (selectedRow != -1) { // Ensure a row is selected
        try {
            int tireId = (int) model.getValueAt(selectedRow, 0);
            String brand = brandField.getText();
            String size = sizeField.getText();
            String type = typeField.getText();
            int quantity = Integer.parseInt(quantityField.getText());

            Cartirerepairingservice.editTireInventory(tireId, brand, size, type, quantity); // Call the method to update the tire
            refreshTable();
            JOptionPane.showMessageDialog(this, "Tire updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editing tire: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a tire to edit.");
    }
}



private void deleteTire() {
    int selectedRow = inventoryTable.getSelectedRow();
    if (selectedRow != -1) { // Ensure a row is selected
        int tireId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this tire?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Cartirerepairingservice.deleteTireInventory(tireId); // Call the method to delete the tire
                refreshTable();
                JOptionPane.showMessageDialog(this, "Tire deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting tire: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a tire to delete.");
    }
}




private void refreshTable() {
    try {
        List<TireInventory> allTires = Cartirerepairingservice.listAllTireInventory();
        model.setRowCount(0); // Clear previous data
        for (TireInventory tire : allTires) {
            model.addRow(new Object[]{tire.getTireId(), tire.getBrand(), tire.getSize(), tire.getType(), tire.getQuantity()});
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error refreshing data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TireInventoryMgmt().setVisible(true);
        });
    }
}


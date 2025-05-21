package cartirerepairingservice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.mycompany.cartirerepairingservice.Cartirerepairingservice;

public class TechnicianMgmt extends JFrame {
    // Class variables (components)
    private JButton addButton, editButton, deleteButton;
    private JTextField nameField, specializationField, hoursWorkedField;
    private JTable techniciansTable;
    private DefaultTableModel model;

    public TechnicianMgmt() {
    initUIComponents(); 
    setTitle("Technician Management");
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

        nameField = new JTextField(15);
        specializationField = new JTextField(15);
        hoursWorkedField = new JTextField(15);

 
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Technician ID", "Name", "Specialization", "Hours Worked"});
        techniciansTable = new JTable(model);


        techniciansTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = techniciansTable.getSelectedRow();
            if (selectedRow >= 0) {
                nameField.setText((String) model.getValueAt(selectedRow, 1));
                specializationField.setText((String) model.getValueAt(selectedRow, 2));
                hoursWorkedField.setText(model.getValueAt(selectedRow, 3).toString());
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5)); 
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Specialization:"));
        formPanel.add(specializationField);
        formPanel.add(new JLabel("Hours Worked:"));
        formPanel.add(hoursWorkedField);

        // Add components to JFrame
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(techniciansTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addTechnician());
        editButton.addActionListener(e -> editTechnician());
        deleteButton.addActionListener(e -> deleteTechnician());
    }

private void addTechnician() {
    try {
        String name = nameField.getText();
        String specialization = specializationField.getText();
        int hoursWorked = Integer.parseInt(hoursWorkedField.getText());

        Technician technician = new Technician(0, name, specialization, hoursWorked); // 0 for auto-generated ID
        Cartirerepairingservice.addTechnician(technician);
        refreshTable();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter a valid number for hours worked.");
    }
}



private void editTechnician() {
    int selectedRow = techniciansTable.getSelectedRow();
    if (selectedRow >= 0) {
        try {
            int technicianId = (int) model.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String specialization = specializationField.getText();
            int hoursWorked = Integer.parseInt(hoursWorkedField.getText());

            Cartirerepairingservice.editTechnician(technicianId, name, specialization, hoursWorked);
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for hours worked.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a technician to edit.");
    }
}



private void deleteTechnician() {
    int selectedRow = techniciansTable.getSelectedRow();
    if (selectedRow >= 0) {
        int technicianId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this technician?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Cartirerepairingservice.deleteTechnician(technicianId);
            refreshTable();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a technician to delete.");
    }
}



    // Refresh the JTable with updated technician list
private void refreshTable() {
    model.setRowCount(0); // Clear the table
    List<Technician> allTechnicians = Cartirerepairingservice.listAllTechnicians();
    for (Technician tech : allTechnicians) {
        model.addRow(new Object[]{tech.getTechnicianId(), tech.getName(), tech.getSpecialization(), tech.getHoursWorked()});
    }
}



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TechnicianMgmt().setVisible(true);
        });
    }
}

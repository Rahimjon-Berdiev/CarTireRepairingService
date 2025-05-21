package cartirerepairingservice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import com.mycompany.cartirerepairingservice.Cartirerepairingservice;

public class RepairJobMgmt extends JFrame {
    // Class variables (components)
    private JButton addButton, editButton, deleteButton;
    private JTextField appointmentIdField, tireIdField, technicianIdField, startTimeField, endTimeField, statusField;
    private JTable jobsTable;
    private DefaultTableModel model;

    public RepairJobMgmt() {
    initUIComponents(); 
    setTitle("Repair Job Management");
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

        appointmentIdField = new JTextField(10);
        tireIdField = new JTextField(10);
        technicianIdField = new JTextField(10);
        startTimeField = new JTextField(10);
        endTimeField = new JTextField(10);
        statusField = new JTextField(10);

    
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Job ID", "Appointment ID", "Tire ID", "Technician ID", "Start Time", "End Time", "Status"});
        jobsTable = new JTable(model);

        jobsTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = jobsTable.getSelectedRow();
            if (selectedRow >= 0) {
                appointmentIdField.setText(model.getValueAt(selectedRow, 1).toString());
                tireIdField.setText(model.getValueAt(selectedRow, 2).toString());
                technicianIdField.setText(model.getValueAt(selectedRow, 3).toString());
                startTimeField.setText((String) model.getValueAt(selectedRow, 4));
                endTimeField.setText((String) model.getValueAt(selectedRow, 5));
                statusField.setText((String) model.getValueAt(selectedRow, 6));
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // Use GridLayout for a better organized form
        formPanel.add(new JLabel("Appointment ID:"));
        formPanel.add(appointmentIdField);
        formPanel.add(new JLabel("Tire ID:"));
        formPanel.add(tireIdField);
        formPanel.add(new JLabel("Technician ID:"));
        formPanel.add(technicianIdField);
        formPanel.add(new JLabel("Start Time:"));
        formPanel.add(startTimeField);
        formPanel.add(new JLabel("End Time:"));
        formPanel.add(endTimeField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusField);

        // Add components to JFrame
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(jobsTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> addJob());
        editButton.addActionListener(e -> editJob());
        deleteButton.addActionListener(e -> deleteJob());
    }

private void addJob() {
    try {
        // Get values from text fields
        int appointmentId = Integer.parseInt(appointmentIdField.getText());
        int tireId = Integer.parseInt(tireIdField.getText());
        int technicianId = Integer.parseInt(technicianIdField.getText());
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        String status = statusField.getText();

        // Create a new RepairJob object
        RepairJob newJob = new RepairJob(0, appointmentId, tireId, technicianId, startTime, endTime, status); // jobId is assumed to be auto-generated

        // Call the addJob method from Cartirerepairingservice
        Cartirerepairingservice.addJob(newJob);

        // Refresh the table to show the new job
        refreshTable();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for IDs.", "Input Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) { // Catching Exception instead of SQLException
        JOptionPane.showMessageDialog(this, "Error in database operation: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void editJob() {
        int selectedRow = jobsTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int jobId = (int) model.getValueAt(selectedRow, 0);
                int appointmentId = Integer.parseInt(appointmentIdField.getText());
                int tireId = Integer.parseInt(tireIdField.getText());
                int technicianId = Integer.parseInt(technicianIdField.getText());
                String startTime = startTimeField.getText();
                String endTime = endTimeField.getText();
                String status = statusField.getText();

                RepairJob.editJob(jobId, appointmentId, tireId, technicianId, startTime, endTime, status);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for IDs.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a job to edit.");
        }
    }

private void deleteJob() {
    int selectedRow = jobsTable.getSelectedRow();
    if (selectedRow >= 0) {
        int jobId = (int) model.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this job?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Cartirerepairingservice.deleteJob(jobId);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Job deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting job: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a job to delete.");
    }
}



private void refreshTable() {
    try {
        List<RepairJob> allJobs = Cartirerepairingservice.listAllJobs();
        model.setRowCount(0); // Clear the table first
        for (RepairJob job : allJobs) {
            model.addRow(new Object[]{
                    job.getJobId(),
                    job.getAppointmentId(),
                    job.getTireId(),
                    job.getTechnicianId(),
                    job.getStartTime(),
                    job.getEndTime(),
                    job.getStatus()
            });
        }
    } catch (Exception ex) { // Catch general exception if listAllJobs() does not throw SQLException specifically
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching data: " + ex.getMessage());
    }
}



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new RepairJobMgmt().setVisible(true);
        });
    }
}


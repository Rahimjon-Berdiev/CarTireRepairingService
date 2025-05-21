package cartirerepairingservice;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

public class MainMenu extends JFrame {

    public MainMenu() {
        // Set up the JFrame
        setTitle("Car Tire Repair Service System");
        setSize(300, 200); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize popup menu
        JPopupMenu popupMenu = initPopupMenu();

        // A label just to show something in the window
        JLabel label = new JLabel("Right click or press the button for menu", SwingConstants.CENTER);
        getContentPane().add(label);

        // Button to trigger popup menu
        JButton button = new JButton("Open Menu");
        button.addActionListener(e -> popupMenu.show(button, button.getWidth()/2, button.getHeight()/2));
        getContentPane().add(button, BorderLayout.SOUTH);

        // Adding MouseListener to the JFrame itself for right-click
        getContentPane().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPopupMenu initPopupMenu() {
    // Create the popup menu
    JPopupMenu popupMenu = new JPopupMenu();

    // Menu items
    JMenuItem customerItem = new JMenuItem("Customer Management");
    customerItem.addActionListener(e -> openCustomerMgmt());
    popupMenu.add(customerItem);

    JMenuItem billingItem = new JMenuItem("Billing Management");
    billingItem.addActionListener(e -> openBillingMgmt());
    popupMenu.add(billingItem);

    // Adding RepairJob Management menu item
    JMenuItem repairJobItem = new JMenuItem("Repair Job Management");
    repairJobItem.addActionListener(e -> openRepairJobMgmt());
    popupMenu.add(repairJobItem);

    // Adding ServiceAppointment Management menu item
    JMenuItem serviceAppointmentItem = new JMenuItem("Service Appointment Management");
    serviceAppointmentItem.addActionListener(e -> openServiceAppointmentMgmt());
    popupMenu.add(serviceAppointmentItem);

    // Adding Technician Management menu item
    JMenuItem technicianItem = new JMenuItem("Technician Management");
    technicianItem.addActionListener(e -> openTechnicianMgmt());
    popupMenu.add(technicianItem);

    // Adding TireInventory Management menu item
    JMenuItem tireInventoryItem = new JMenuItem("Tire Inventory Management");
    tireInventoryItem.addActionListener(e -> openTireInventoryMgmt());
    popupMenu.add(tireInventoryItem);

    return popupMenu;
}

    // Methods to open different management sections
    private void openCustomerMgmt() {
    new CustomerMgmt().setVisible(true);
    }

    private void openBillingMgmt() {
    new BillingMgmt().setVisible(true);
    }

    private void openRepairJobMgmt() {
    new RepairJobMgmt().setVisible(true);
    }

    private void openServiceAppointmentMgmt() {
    new ServiceAppointmentMgmt().setVisible(true);
    }

    private void openTechnicianMgmt() {
    new TechnicianMgmt().setVisible(true);
    }

    private void openTireInventoryMgmt() {
    new TireInventoryMgmt().setVisible(true);
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}

package com.mycompany.cartirerepairingservice;
import cartirerepairingservice.CustomerMgmt;
import cartirerepairingservice.BillingMgmt;
import cartirerepairingservice.RepairJobMgmt;
import cartirerepairingservice.ServiceAppointmentMgmt;
import cartirerepairingservice.TechnicianMgmt;
import cartirerepairingservice.TireInventoryMgmt;
import cartirerepairingservice.Customer;
import cartirerepairingservice.Billing;
import cartirerepairingservice.RepairJob;
import cartirerepairingservice.ServiceAppointment;
import cartirerepairingservice.Technician;
import cartirerepairingservice.TireInventory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.*;
import java.sql.ResultSet;




// The main class that starts the application
public class Cartirerepairingservice {
    private static Connection connection;
    public static void main(String[] args) {
        initializeDatabaseConnection();
        initializeData();
        SwingUtilities.invokeLater(() -> createAndShowGUI()); // Then display GUI
        
    }
    private static void initializeDatabaseConnection() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/car", "root", "");

            // Optional: Print a message for successful connection
            System.out.println("Database connected successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(Cartirerepairingservice.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Failed to connect to the database.");
        }
    }
    
    public static int getNextCustomerId() {
    String sql = "SELECT MAX(customer_id) as max_id FROM customers;";
    int maxId = 0;

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        if (resultSet.next()) {  // If there's a result
            maxId = resultSet.getInt("max_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return maxId + 1;  // Return the next ID
}
public static void addCustomer(String name, String contactNumber, String vehicleDetails) {
    int nextCustomerId = getNextCustomerId();  // Get the next ID

    try {
        String sql = "INSERT INTO customers (customer_id, name, contact_number, vehicle_details) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, nextCustomerId);  // Set the next ID
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, contactNumber);
        preparedStatement.setString(4, vehicleDetails);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new customer with ID " + nextCustomerId + " was inserted successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}











public static void editCustomer(int customerId, String name, String contactNumber, String vehicleDetails) {
    try {
        String sql = "UPDATE customers SET name = ?, contact_number = ?, vehicle_details = ? WHERE customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, contactNumber);
        preparedStatement.setString(3, vehicleDetails);
        preparedStatement.setInt(4, customerId);

        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Customer with ID " + customerId + " was updated successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


public static void deleteCustomer(int customerId) {
    try {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, customerId);

        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Customer with ID " + customerId + " was deleted successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


public static List<Customer> listAllCustomers() {
    List<Customer> customers = new ArrayList<>();
    String sql = "SELECT * FROM customers";

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            // Fetching data from the resultSet and creating Customer objects
            int id = resultSet.getInt("customer_id"); // or whatever your column name is
            String name = resultSet.getString("name");
            String contactNumber = resultSet.getString("contact_number");
            String vehicleDetails = resultSet.getString("vehicle_details");
            customers.add(new Customer(id, name, contactNumber, vehicleDetails));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customers;
}

public static int getNextBillingId() {
    String sql = "SELECT MAX(bill_id) AS max_id FROM billings;";
    int maxId = 0;

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        if (resultSet.next()) {
            maxId = resultSet.getInt("max_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return maxId + 1;  // Next ID
}

public static void addBilling(int jobId, double amount, String paymentStatus, String paymentMethod) {
    int nextBillingId = getNextBillingId();  // Get the next ID

    String sql = "INSERT INTO billings (bill_id, job_id, amount, payment_status, payment_method) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, nextBillingId);  // Set the next ID
        preparedStatement.setInt(2, jobId);
        preparedStatement.setDouble(3, amount);
        preparedStatement.setString(4, paymentStatus);
        preparedStatement.setString(5, paymentMethod);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new billing record with ID " + nextBillingId + " was inserted successfully!");
        }
    } catch (SQLException ex) {
        System.err.println("Error adding billing: " + ex.getMessage());
        ex.printStackTrace();
    }
}




public static void editBilling(int billingId, int jobId, double amount, String paymentStatus, String paymentMethod) {
    // Adjusted SQL with correct column name: "bill_id" instead of "billing_id"
    String sql = "UPDATE billings SET job_id = ?, amount = ?, payment_status = ?, payment_method = ? WHERE bill_id = ?;";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, jobId);
        statement.setDouble(2, amount);
        statement.setString(3, paymentStatus);
        statement.setString(4, paymentMethod);
        statement.setInt(5, billingId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Billing record updated successfully!");
        } else {
            System.out.println("No record found to update for ID: " + billingId);
        }
    } catch (SQLException e) {
        System.err.println("Error updating billing: " + e.getMessage());
    }
}




public static void deleteBilling(int billingId) {
    String sql = "DELETE FROM billings WHERE bill_id = ?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, billingId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Billing record deleted successfully!");
        } else {
            System.out.println("No record was found with ID: " + billingId);
        }
    } catch (SQLException e) {
        System.err.println("Error deleting billing: " + e.getMessage());
    }
}



public static List<Billing> listAllBillings() {
    List<Billing> billings = new ArrayList<>();
    String sql = "SELECT * FROM billings;";
    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            // Replace "billing_id" with the actual column name for billing ID in your database
            int id = resultSet.getInt("bill_id");  
            int jobId = resultSet.getInt("job_id");  // Replace with actual column name
            double amount = resultSet.getDouble("amount");  // Replace with actual column name
            String paymentStatus = resultSet.getString("payment_status");  // Replace with actual column name
            String paymentMethod = resultSet.getString("payment_method");  // Replace with actual column name
            Billing billing = new Billing(id, jobId, amount, paymentStatus, paymentMethod);
            billings.add(billing);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return billings;
}




public static void addJob(RepairJob job) {
    String sql = "INSERT INTO repair_jobs (appointment_id, tire_id, technician_id, start_time, end_time, status) VALUES (?, ?, ?, ?, ?, ?);";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, job.getAppointmentId());
        statement.setInt(2, job.getTireId());
        statement.setInt(3, job.getTechnicianId());
        statement.setString(4, job.getStartTime());
        statement.setString(5, job.getEndTime());
        statement.setString(6, job.getStatus());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new repair job was inserted successfully!");
        }
    } catch (SQLException ex) {
        System.err.println("Error in inserting repair job: " + ex.getMessage());
    }
}




    public static void editJob(Connection connection, int jobId, int newAppointmentId, int newTireId, int newTechnicianId, String newStartTime, String newEndTime, String newStatus) {
    String sql = "UPDATE repair_jobs SET appointmentId=?, tireId=?, technicianId=?, startTime=?, endTime=?, status=? WHERE jobId=?;";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, newAppointmentId);
        statement.setInt(2, newTireId);
        statement.setInt(3, newTechnicianId);
        statement.setString(4, newStartTime);
        statement.setString(5, newEndTime);
        statement.setString(6, newStatus);
        statement.setInt(7, jobId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing repair job was updated successfully!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public static void deleteJob(int jobId) {
    String sql = "DELETE FROM repair_jobs WHERE job_id = ?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, jobId);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A repair job was deleted successfully!");
        }
    } catch (SQLException e) {
        System.err.println("Error deleting job: " + e.getMessage());
        // Potentially rethrow as a RuntimeException or handle accordingly
    }
}



    // Method to list all repair jobs
public static List<RepairJob> listAllJobs() {
    List<RepairJob> jobs = new ArrayList<>();
    String sql = "SELECT * FROM repair_jobs;";

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            RepairJob job = new RepairJob(
                    resultSet.getInt("job_id"), // Adjusted to job_id
                    resultSet.getInt("appointment_id"),
                    resultSet.getInt("tire_id"),
                    resultSet.getInt("technician_id"),
                    resultSet.getString("start_time"),
                    resultSet.getString("end_time"),
                    resultSet.getString("status")
            );
            jobs.add(job);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching jobs: " + e.getMessage());
    }
    return jobs;
}

    
public static void addServiceAppointment(ServiceAppointment appointment) {
    String sql = "INSERT INTO service_appointments (customer_id, service_date, issues_reported, is_completed) VALUES (?, ?, ?, ?);"; // Use actual database column names

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, appointment.getCustomerId());
        statement.setString(2, appointment.getServiceDate());
        statement.setString(3, appointment.getIssuesReported());
        statement.setBoolean(4, appointment.isCompleted());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new service appointment was inserted successfully!");
        }
    } catch (SQLException ex) {
        System.err.println("Error adding service appointment: " + ex.getMessage());
    }
}

public static void editServiceAppointment(int appointmentId, int customerId, String serviceDate, String issuesReported, boolean isCompleted) {
    String sql = "UPDATE service_appointments SET customer_id=?, service_date=?, issues_reported=?, is_completed=? WHERE appointment_id=?;";
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, customerId);
        statement.setString(2, serviceDate);
        statement.setString(3, issuesReported);
        statement.setBoolean(4, isCompleted);
        statement.setInt(5, appointmentId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated == 0) {
            System.out.println("No appointment found with ID: " + appointmentId);
        }
    } catch (SQLException e) {
        System.err.println("Error updating service appointment: " + e.getMessage());
    }
}

public static void deleteServiceAppointment(int appointmentId) {
    String sql = "DELETE FROM service_appointments WHERE appointment_id=?;";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, appointmentId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted == 0) {
            System.out.println("No appointment found with ID: " + appointmentId);
        }
    } catch (SQLException e) {
        System.err.println("Error deleting service appointment: " + e.getMessage());
    }
}


public static List<ServiceAppointment> listAllServiceAppointments() {
    List<ServiceAppointment> appointments = new ArrayList<>();
    String sql = "SELECT * FROM service_appointments;"; // Adjust the table name as necessary

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            ServiceAppointment appointment = new ServiceAppointment(
                    resultSet.getInt("appointment_id"), // adjust the column name as necessary
                    resultSet.getInt("customer_id"), // adjust the column name as necessary
                    resultSet.getString("service_date"), // adjust the column name as necessary
                    resultSet.getString("issues_reported"), // adjust the column name as necessary
                    resultSet.getBoolean("is_completed") // adjust the column name as necessary
            );
            appointments.add(appointment);
        }
    } catch (SQLException e) {
        System.err.println("Error fetching service appointments: " + e.getMessage());
    }
    return appointments;
}

public static void addTechnician(Technician technician) {
    String sql = "INSERT INTO technicians (name, specialization, hours_worked) VALUES (?, ?, ?);";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, technician.getName());
        statement.setString(2, technician.getSpecialization());
        statement.setInt(3, technician.getHoursWorked());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new technician was inserted successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


public static void editTechnician(int technicianId, String newName, String newSpecialization, int newHoursWorked) {
    String sql = "UPDATE technicians SET name=?, specialization=?, hours_worked=? WHERE technician_id=?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, newName);
        statement.setString(2, newSpecialization);
        statement.setInt(3, newHoursWorked);
        statement.setInt(4, technicianId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing technician was updated successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


public static void deleteTechnician(int technicianId) {
    String sql = "DELETE FROM technicians WHERE technician_id=?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, technicianId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A technician was deleted successfully!");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


public static List<Technician> listAllTechnicians() {
    List<Technician> technicians = new ArrayList<>();
    String sql = "SELECT * FROM technicians;";
    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            Technician technician = new Technician(
                    resultSet.getInt("technician_id"),
                    resultSet.getString("name"),
                    resultSet.getString("specialization"),
                    resultSet.getInt("hours_worked")
            );
            technicians.add(technician);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return technicians;
}


public static void addTireInventory(TireInventory tireInventory) {
    String sql = "INSERT INTO tire_inventory (brand, size, type, quantity) VALUES (?, ?, ?, ?);";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, tireInventory.getBrand());
        statement.setString(2, tireInventory.getSize());
        statement.setString(3, tireInventory.getType());
        statement.setInt(4, tireInventory.getQuantity());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new tire inventory record was inserted successfully!");
        }
    } catch (SQLException e) {
        System.err.println("Error adding tire inventory: " + e.getMessage());
        // Might want to throw this exception or handle it depending on your error handling strategy
    }
}


public static void editTireInventory(int tireId, String brand, String size, String type, int quantity) {
    String sql = "UPDATE tire_inventory SET brand = ?, size = ?, type = ?, quantity = ? WHERE tire_id = ?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, brand);
        statement.setString(2, size);
        statement.setString(3, type);
        statement.setInt(4, quantity);
        statement.setInt(5, tireId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Tire inventory record updated successfully for ID: " + tireId);
        } else {
            System.out.println("No record found to update for ID: " + tireId);
        }
    } catch (SQLException e) {
        System.err.println("Error updating tire inventory: " + e.getMessage());
        // Consider handling the exception more gracefully or rethrowing it as needed
    }
}


public static void deleteTireInventory(int tireId) {
    String sql = "DELETE FROM tire_inventory WHERE tire_id = ?;";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, tireId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Tire inventory record deleted successfully for ID: " + tireId);
        } else {
            System.out.println("No record found to delete for ID: " + tireId);
        }
    } catch (SQLException e) {
        System.err.println("Error deleting tire inventory: " + e.getMessage());
        // Consider handling the exception more gracefully or rethrowing it as needed
    }
}


public static List<TireInventory> listAllTireInventory() {
    List<TireInventory> tires = new ArrayList<>();
    String sql = "SELECT * FROM tire_inventory;"; // Ensure table and column names are correct

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        while (resultSet.next()) {
            int id = resultSet.getInt("tire_id"); // Adjust column name as per your DB
            String brand = resultSet.getString("brand");
            String size = resultSet.getString("size");
            String type = resultSet.getString("type");
            int quantity = resultSet.getInt("quantity");

            tires.add(new TireInventory(id, brand, size, type, quantity));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions or maybe rethrow them to be handled elsewhere
    }
    return tires;
}








    private static void createAndShowGUI() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setTitle("Car Tire Repair Service System");
        mainMenu.setSize(800, 600); 
        mainMenu.setLocationRelativeTo(null); 
        mainMenu.setVisible(true); 
    }

        public static void initializeData() {
        initializeCustomers();
        initializeBillings();
        initializeRepairJobs();
        initializeServiceAppointments();
        initializeTechnicians();
        initializeTireInventory();
    }
        public static void initializeCustomers() {
        Customer.addCustomer(new Customer(1, "John Doe", "123-456-7890", "Toyota Corolla"));
        Customer.addCustomer(new Customer(2, "Jane Smith", "987-654-3210", "Honda Civic"));
        Customer.addCustomer(new Customer(3, "Emily Davis", "555-666-7777", "Ford Focus"));
        Customer.addCustomer(new Customer(4, "Michael Brown", "222-333-4444", "Chevy Malibu"));
        Customer.addCustomer(new Customer(5, "Sarah Wilson", "111-222-3333", "Nissan Sentra"));
    }
        public static void initializeBillings() {
        Billing.addBilling(new Billing(1, 1, 100.0, "Unpaid", "Credit Card"));
        Billing.addBilling(new Billing(2, 2, 200.0, "Paid", "Cash"));
        Billing.addBilling(new Billing(3, 3, 150.0, "Pending", "Credit Card"));
        Billing.addBilling(new Billing(4, 4, 250.0, "Unpaid", "Debit"));
        Billing.addBilling(new Billing(5, 5, 300.0, "Paid", "Credit Card"));
    }
        public static void initializeRepairJobs() {
        RepairJob.addJob(new RepairJob(1, 101, 201, 301, "09:00", "11:00", "Completed"));
        RepairJob.addJob(new RepairJob(2, 102, 202, 302, "10:00", "12:00", "InProgress"));
        RepairJob.addJob(new RepairJob(3, 103, 203, 303, "11:00", "13:00", "Completed"));
        RepairJob.addJob(new RepairJob(4, 104, 204, 304, "12:00", "14:00", "InProgress"));
        RepairJob.addJob(new RepairJob(5, 105, 205, 305, "13:00", "15:00", "Pending"));
    }
        public static void initializeServiceAppointments() {
        ServiceAppointment.addAppointment(new ServiceAppointment(1, 501, "2023-01-01", "Tire replacement", true));
        ServiceAppointment.addAppointment(new ServiceAppointment(2, 502, "2023-01-02", "Oil change", false));
        ServiceAppointment.addAppointment(new ServiceAppointment(3, 503, "2023-01-03", "General inspection", true));
        ServiceAppointment.addAppointment(new ServiceAppointment(4, 504, "2023-01-04", "Brake repair", false));
        ServiceAppointment.addAppointment(new ServiceAppointment(5, 505, "2023-01-05", "Engine tuning", true));
    }
        public static void initializeTechnicians() {
        Technician.addTechnician(new Technician(1, "John Tech", "Tire Specialist", 40));
        Technician.addTechnician(new Technician(2, "Jane Mechanic", "Engine Specialist", 35));
        Technician.addTechnician(new Technician(3, "Mike Repairer", "Brake Specialist", 38));
        Technician.addTechnician(new Technician(4, "Susan Fixer", "Transmission Specialist", 42));
        Technician.addTechnician(new Technician(5, "Bob Builder", "Generalist", 30));
    }
        public static void initializeTireInventory() {
        TireInventory.addTire(new TireInventory(1, "Michelin", "205/55R16", "All-Season", 50));
        TireInventory.addTire(new TireInventory(2, "Goodyear", "215/65R15", "Winter", 30));
        TireInventory.addTire(new TireInventory(3, "Bridgestone", "225/45R17", "Summer", 40));
        TireInventory.addTire(new TireInventory(4, "Pirelli", "235/40R18", "Performance", 20));
        TireInventory.addTire(new TireInventory(5, "Continental", "245/35R19", "Touring", 25));
    }
}

// The MainMenu class that provides the main interface
class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Car Tire Repair Service System");
        setSize(800, 600); 
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUIComponents();
    }

private void initUIComponents() {
    JMenuBar menuBar = new JMenuBar();

    // File menu with exit, backup, and retrieve options
    JMenu fileMenu = new JMenu("File");

    // Create menu items
    JMenuItem backupItem = new JMenuItem("Backup");
    backupItem.addActionListener(e -> performBackup()); 

    JMenuItem retrieveItem = new JMenuItem("Retrieve");
    retrieveItem.addActionListener(e -> performRetrieve()); 

    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> System.exit(0));

    // Add items to file menu
    fileMenu.add(backupItem);
    fileMenu.add(retrieveItem);
    fileMenu.addSeparator(); 
    fileMenu.add(exitItem);
    // Management menu for different system functions
    JMenu managementMenu = new JMenu("Management");

    // Adding Customer Management menu item
    JMenuItem customerItem = new JMenuItem("Customer Management");
    customerItem.addActionListener(e -> openCustomerMgmt());
    managementMenu.add(customerItem);

    // Adding Billing Management menu item
    JMenuItem billingItem = new JMenuItem("Billing Management");
    billingItem.addActionListener(e -> openBillingMgmt());
    managementMenu.add(billingItem);

    // Adding RepairJob Management menu item
    JMenuItem repairJobItem = new JMenuItem("Repair Job Management");
    repairJobItem.addActionListener(e -> openRepairJobMgmt());
    managementMenu.add(repairJobItem);

    // Adding ServiceAppointment Management menu item
    JMenuItem serviceAppointmentItem = new JMenuItem("Service Appointment Management");
    serviceAppointmentItem.addActionListener(e -> openServiceAppointmentMgmt());
    managementMenu.add(serviceAppointmentItem);

    // Adding Technician Management menu item
    JMenuItem technicianItem = new JMenuItem("Technician Management");
    technicianItem.addActionListener(e -> openTechnicianMgmt());
    managementMenu.add(technicianItem);

    // Adding TireInventory Management menu item
    JMenuItem tireInventoryItem = new JMenuItem("Tire Inventory Management");
    tireInventoryItem.addActionListener(e -> openTireInventoryMgmt());
    managementMenu.add(tireInventoryItem);

    menuBar.add(fileMenu);
    menuBar.add(managementMenu);

    // Set the menu bar for the JFrame
    setJMenuBar(menuBar);
}


private void openCustomerMgmt() {
    CustomerMgmt customerMgmt = new CustomerMgmt(); 
    customerMgmt.setVisible(true);
}

private void openBillingMgmt() {
    BillingMgmt billingMgmt = new BillingMgmt(); 
    billingMgmt.setVisible(true);
}

private void openRepairJobMgmt() {
    RepairJobMgmt repairJobMgmt = new RepairJobMgmt(); 
    repairJobMgmt.setVisible(true);
}

private void openServiceAppointmentMgmt() {
    ServiceAppointmentMgmt serviceAppointmentMgmt = new ServiceAppointmentMgmt(); 
    serviceAppointmentMgmt.setVisible(true);
}

private void openTechnicianMgmt() {
    TechnicianMgmt technicianMgmt = new TechnicianMgmt(); 
    technicianMgmt.setVisible(true);
}

private void openTireInventoryMgmt() {
    TireInventoryMgmt tireInventoryMgmt = new TireInventoryMgmt(); 
    tireInventoryMgmt.setVisible(true);
}
private void performBackup() {
    // Code to perform backup
    System.out.println("Backup performed");
}

private void performRetrieve() {
    // Code to perform retrieve
    System.out.println("Data retrieved");
}

}

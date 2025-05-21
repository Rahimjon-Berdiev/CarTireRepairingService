package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {
    // Attributes
    private int customerId;
    private String name;
    private String contactNumber;
    private String vehicleDetails;

    private static List<Customer> customerList = new ArrayList<>();

    // Constructor
    public Customer(int customerId, String name, String contactNumber, String vehicleDetails) {
        this.customerId = customerId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.vehicleDetails = vehicleDetails;
    }
    
    @Override
    public String toString() {
    return customerId + ", " + name + ", " + contactNumber + ", " + vehicleDetails;
}




   // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

   // CRUDL Methods
    // Method to add a customer
    public static void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    // Method to edit a customer
    public static void editCustomer(int customerId, String name, String contactNumber, String vehicleDetails) {
        for (Customer cust : customerList) {
            if (cust.getCustomerId() == customerId) {
                cust.setName(name);
                cust.setContactNumber(contactNumber);
                cust.setVehicleDetails(vehicleDetails);
                break;
            }
        }
    }

    // Method to delete a customer
    public static void deleteCustomer(int customerId) {
        customerList.removeIf(cust -> cust.getCustomerId() == customerId);
    }

    // Method to list a customer's details
    public static Customer listCustomer(int customerId) {
        for (Customer cust : customerList) {
            if (cust.getCustomerId() == customerId) {
                return cust;
            }
        }
        return null;
    }

    // Method to list all customers
    public static List<Customer> listAllCustomers() {
        return new ArrayList<>(customerList);
    }

    // Method to backup customer records
    public static List<Customer> backupCustomers() {
        return customerList.stream()
                .map(cust -> new Customer(cust.getCustomerId(), cust.getName(), cust.getContactNumber(), cust.getVehicleDetails()))
                .collect(Collectors.toList());
    }

    // Method to restore customer records from backup
    public static void restoreCustomers(List<Customer> backup) {
        customerList = new ArrayList<>(backup);
    }

}
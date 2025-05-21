package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAppointment {
    // Attributes
    private int appointmentId;
    private int customerId;
    private String serviceDate;
    private String issuesReported;
    private boolean isCompleted;

    private static List<ServiceAppointment> appointmentList = new ArrayList<>();

    // Constructor
    public ServiceAppointment(int appointmentId, int customerId, String serviceDate, String issuesReported, boolean isCompleted) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.serviceDate = serviceDate;
        this.issuesReported = issuesReported;
        this.isCompleted = isCompleted;
    }
    
    @Override
    public String toString() {
    return appointmentId + ", " + customerId + ", " + serviceDate + ", " + issuesReported + ", " + isCompleted;
}


    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getIssuesReported() {
        return issuesReported;
    }

    public void setIssuesReported(String issuesReported) {
        this.issuesReported = issuesReported;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    // CRUDL Methods
    public static void addAppointment(ServiceAppointment appointment) {
        appointmentList.add(appointment);
    }

    public static void editAppointment(int appointmentId, int newCustomerId, String newServiceDate, String newIssuesReported, boolean newIsCompleted) {
        for (ServiceAppointment appointment : appointmentList) {
            if (appointment.getAppointmentId() == appointmentId) {
                appointment.setCustomerId(newCustomerId);
                appointment.setServiceDate(newServiceDate);
                appointment.setIssuesReported(newIssuesReported);
                appointment.setCompleted(newIsCompleted);
                break;
            }
        }
    }

    public static void deleteAppointment(int appointmentId) {
        appointmentList.removeIf(appointment -> appointment.getAppointmentId() == appointmentId);
    }

   public static ServiceAppointment listAppointment(int appointmentId) {
        for (ServiceAppointment appointment : appointmentList) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    public static List<ServiceAppointment> listAllAppointments() {
        return new ArrayList<>(appointmentList);
    }

    public static List<ServiceAppointment> backupAppointments() {
        return appointmentList.stream()
                .map(appointment -> new ServiceAppointment(appointment.getAppointmentId(), appointment.getCustomerId(), appointment.getServiceDate(), appointment.getIssuesReported(), appointment.isCompleted()))
                .collect(Collectors.toList());
    }

    public static void restoreAppointments(List<ServiceAppointment> backup) {
        appointmentList = new ArrayList<>(backup);
    }

}

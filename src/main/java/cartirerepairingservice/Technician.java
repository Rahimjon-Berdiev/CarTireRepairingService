package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Technician {
    // Attributes
    private int technicianId;
    private String name;
    private String specialization;
    private int hoursWorked;

    private static List<Technician> technicianList = new ArrayList<>();

    // Constructor
    public Technician(int technicianId, String name, String specialization, int hoursWorked) {
        this.technicianId = technicianId;
        this.name = name;
        this.specialization = specialization;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String toString() {
    return technicianId + ", " + name + ", " + specialization + ", " + hoursWorked;
}


    // Getters and Setters
    // Getters
    public int getTechnicianId() {
        return technicianId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }


    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }


    // CRUDL Methods
    public static void addTechnician(Technician technician) {
        technicianList.add(technician);
    }

    public static void editTechnician(int technicianId, String newName, String newSpecialization, int newHoursWorked) {
        for (Technician tech : technicianList) {
            if (tech.getTechnicianId() == technicianId) {
                tech.setName(newName);
                tech.setSpecialization(newSpecialization);
                tech.setHoursWorked(newHoursWorked);
                break;
            }
        }
    }

    public static void deleteTechnician(int technicianId) {
        technicianList.removeIf(tech -> tech.getTechnicianId() == technicianId);
    }

    public static Technician listTechnician(int technicianId) {
        for (Technician tech : technicianList) {
            if (tech.getTechnicianId() == technicianId) {
                return tech;
            }
        }
        return null;
    }

    public static List<Technician> listAllTechnicians() {
        return new ArrayList<>(technicianList);
    }

    public static List<Technician> backupTechnicians() {
        return technicianList.stream()
                .map(tech -> new Technician(tech.getTechnicianId(), tech.getName(), tech.getSpecialization(), tech.getHoursWorked()))
                .collect(Collectors.toList());
    }

    public static void restoreTechnicians(List<Technician> backup) {
        technicianList = new ArrayList<>(backup);
    }

}


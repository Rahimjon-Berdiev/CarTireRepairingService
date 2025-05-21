package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepairJob {
    // Attributes
    private int jobId;
    private int appointmentId;
    private int tireId;
    private int technicianId;
    private String startTime;
    private String endTime;
    private String status;

    private static List<RepairJob> jobList = new ArrayList<>();

    // Constructor
    public RepairJob(int jobId, int appointmentId, int tireId, int technicianId, String startTime, String endTime, String status) {
        this.jobId = jobId;
        this.appointmentId = appointmentId;
        this.tireId = tireId;
        this.technicianId = technicianId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    @Override
    public String toString() {
    return jobId + ", " + appointmentId + ", " + tireId + ", " + technicianId + ", " + startTime + ", " + endTime + ", " + status;
}


// Getters
public int getJobId() {
    return jobId;
}

public int getAppointmentId() {
    return appointmentId;
}

public int getTireId() {
    return tireId;
}

public int getTechnicianId() {
    return technicianId;
}

public String getStartTime() {
    return startTime;
}

public String getEndTime() {
    return endTime;
}

public String getStatus() {
    return status;
}

// Setters
public void setJobId(int jobId) {
    this.jobId = jobId;
}

public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
}

public void setTireId(int tireId) {
    this.tireId = tireId;
}

public void setTechnicianId(int technicianId) {
    this.technicianId = technicianId;
}

public void setStartTime(String startTime) {
    this.startTime = startTime;
}

public void setEndTime(String endTime) {
    this.endTime = endTime;
}

public void setStatus(String status) {
    this.status = status;
}


    // CRUDL Methods
    public static void addJob(RepairJob job) {
        jobList.add(job);
    }

    public static void editJob(int jobId, int newAppointmentId, int newTireId, int newTechnicianId, String newStartTime, String newEndTime, String newStatus) {
        for (RepairJob job : jobList) {
            if (job.getJobId() == jobId) {
                job.setAppointmentId(newAppointmentId);
                job.setTireId(newTireId);
                job.setTechnicianId(newTechnicianId);
                job.setStartTime(newStartTime);
                job.setEndTime(newEndTime);
                job.setStatus(newStatus);
                break;
            }
        }
    }

    public static void deleteJob(int jobId) {
        jobList.removeIf(job -> job.getJobId() == jobId);
    }

    public static RepairJob listJob(int jobId) {
        for (RepairJob job : jobList) {
            if (job.getJobId() == jobId) {
                return job;
            }
        }
        return null;
    }

    public static List<RepairJob> listAllJobs() {
        return new ArrayList<>(jobList);
    }

    public static List<RepairJob> backupJobs() {
        return jobList.stream()
                .map(job -> new RepairJob(job.getJobId(), job.getAppointmentId(), job.getTireId(), job.getTechnicianId(), job.getStartTime(), job.getEndTime(), job.getStatus()))
                .collect(Collectors.toList());
    }

    public static void restoreJobs(List<RepairJob> backup) {
        jobList = new ArrayList<>(backup);
    }


}

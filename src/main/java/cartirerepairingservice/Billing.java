package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Billing {
    // Attributes
    private int billId;
    private int jobId;
    private double amount;
    private String paymentStatus;
    private String paymentMethod;

    private static List<Billing> billingList = new ArrayList<>();

    // Constructor
    public Billing(int billId, int jobId, double amount, String paymentStatus, String paymentMethod) {
        this.billId = billId;
        this.jobId = jobId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public String toString() {
    return billId + ", " + jobId + ", " + amount + ", " + paymentStatus + ", " + paymentMethod;
}

    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // CRUDL Methods
    public static void addBilling(Billing billing) {
        billingList.add(billing);
    }

    public static void editBilling(int billId, int newJobId, double newAmount, String newPaymentStatus, String newPaymentMethod) {
        for (Billing bill : billingList) {
            if (bill.getBillId() == billId) {
                bill.setJobId(newJobId);
                bill.setAmount(newAmount);
                bill.setPaymentStatus(newPaymentStatus);
                bill.setPaymentMethod(newPaymentMethod);
                break;
            }
        }
    }

    public static void deleteBilling(int billId) {
        billingList.removeIf(bill -> bill.getBillId() == billId);
    }

    public static Billing listBilling(int billId) {
        for (Billing bill : billingList) {
            if (bill.getBillId() == billId) {
                return bill;
            }
        }
        return null;
    }

    public static List<Billing> listAllBillings() {
        return new ArrayList<>(billingList);
    }

    public static List<Billing> backupBillings() {
        return billingList.stream()
                .map(bill -> new Billing(bill.getBillId(), bill.getJobId(), bill.getAmount(), bill.getPaymentStatus(), bill.getPaymentMethod()))
                .collect(Collectors.toList());
    }

    public static void restoreBillings(List<Billing> backup) {
        billingList = new ArrayList<>(backup);
    }

}


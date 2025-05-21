package cartirerepairingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TireInventory {
    // Attributes
    private int tireId;
    private String brand;
    private String size;
    private String type;
    private int quantity;

    private static List<TireInventory> inventoryList = new ArrayList<>();

    // Constructor
    public TireInventory(int tireId, String brand, String size, String type, int quantity) {
        this.tireId = tireId;
        this.brand = brand;
        this.size = size;
        this.type = type;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
    return tireId + ", " + brand + ", " + size + ", " + type + ", " + quantity;
    }


    // Getters
    public int getTireId() {
    return tireId;
    }

    public String getBrand() {
    return brand;
    }

    public String getSize() {
    return size;
    }

    public String getType() {
    return type;
    }

    public int getQuantity() {
    return quantity;
    }

    // Setters
    public void setTireId(int tireId) {
    this.tireId = tireId;
    }

    public void setBrand(String brand) {
    this.brand = brand;
    }

    public void setSize(String size) {
    this.size = size;
    }

    public void setType(String type) {
    this.type = type;
    }

    public void setQuantity(int quantity) {
    this.quantity = quantity;
    }


    // CRUDL Methods
    public static void addTire(TireInventory tire) {
        inventoryList.add(tire);
    }

    public static void editTire(int tireId, String newBrand, String newSize, String newType, int newQuantity) {
        for (TireInventory tire : inventoryList) {
            if (tire.getTireId() == tireId) {
                tire.setBrand(newBrand);
                tire.setSize(newSize);
                tire.setType(newType);
                tire.setQuantity(newQuantity);
                break;
            }
        }
    }

    public static void deleteTire(int tireId) {
        inventoryList.removeIf(tire -> tire.getTireId() == tireId);
    }

    public static TireInventory listTire(int tireId) {
        for (TireInventory tire : inventoryList) {
            if (tire.getTireId() == tireId) {
                return tire;
            }
        }
        return null;
    }

    public static List<TireInventory> listAllTires() {
        return new ArrayList<>(inventoryList);
    }

    public static List<TireInventory> backupTires() {
        return inventoryList.stream()
                .map(tire -> new TireInventory(tire.getTireId(), tire.getBrand(), tire.getSize(), tire.getType(), tire.getQuantity()))
                .collect(Collectors.toList());
    }

    public static void restoreTires(List<TireInventory> backup) {
        inventoryList = new ArrayList<>(backup);
    }

}


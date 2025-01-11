import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Garment {
    private String id;
    private String name;
    private String description;
    private String size;
    private String color;
    private double price;
    private int stockQuantity;
    private Fabric fabric;

    public Garment(String id, String name, String description, String size,
                   String color, double price, Fabric fabric) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.fabric = fabric;
        this.stockQuantity = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public Fabric getFabric() { return fabric; }

    // Setters (only for modifiable properties)
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double calculateDiscountPrice(double discountPercentage) {
        return price * (1 - discountPercentage / 100);
    }
}

class Fabric {
    private String id;
    private String type;
    private String color;
    private double pricePerMeter;

    public Fabric(String id, String type, String color, double pricePerMeter) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.pricePerMeter = pricePerMeter;
    }

    // Getters
    public String getId() { return id; }
    public String getType() { return type; }
    public String getColor() { return color; }
    public double getPricePerMeter() { return pricePerMeter; }

    // Setter only for price as it might change
    public void setPricePerMeter(double pricePerMeter) {
        this.pricePerMeter = pricePerMeter;
    }

    public double calculateCost(double meters) {
        return pricePerMeter * meters;
    }
}

class Supplier {
    private String id;
    private String name;
    private String contactInfo;
    private List<Fabric> suppliedFabrics;

    public Supplier(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.suppliedFabrics = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContactInfo() { return contactInfo; }
    public List<Fabric> getSuppliedFabrics() { return new ArrayList<>(suppliedFabrics); }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public void addFabric(Fabric fabric) {
        suppliedFabrics.add(fabric);
    }

    public void removeFabric(Fabric fabric) {
        suppliedFabrics.remove(fabric);
    }
}

class Order {
    private String orderId;
    private Date orderDate;
    private List<Garment> garments;
    private double totalAmount;
    private String status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.orderDate = new Date();
        this.garments = new ArrayList<>();
        this.status = "PENDING";
    }

    // Getters
    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return new Date(orderDate.getTime()); }
    public List<Garment> getGarments() { return new ArrayList<>(garments); }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    public void addGarment(Garment garment) {
        garments.add(garment);
        calculateTotalAmount();
    }

    public void removeGarment(Garment garment) {
        garments.remove(garment);
        calculateTotalAmount();
    }

    private double calculateTotalAmount() {
        totalAmount = garments.stream()
                .mapToDouble(Garment::getPrice)
                .sum();
        return totalAmount;
    }

    public void printOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("Items:");
        garments.forEach(g -> System.out.println("- " + g.getName() + " ($" + g.getPrice() + ")"));
        System.out.println("Total: $" + totalAmount);
    }
}

class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private List<Order> orders;

    public Customer(String customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.orders = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<Order> getOrders() { return new ArrayList<>(orders); }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public void placeOrder(Order order) {
        orders.add(order);
    }
}

class Inventory {
    private List<Garment> garments;

    public Inventory() {
        this.garments = new ArrayList<>();
    }

    // Getter
    public List<Garment> getGarments() { return new ArrayList<>(garments); }

    public void addGarment(Garment garment) {
        garments.add(garment);
    }

    public void removeGarment(String id) {
        garments.removeIf(g -> g.getId().equals(id));
    }

    public Garment findGarment(String id) {
        return garments.stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Garment> findGarmentsByColor(String color) {
        return garments.stream()
                .filter(g -> g.getColor().equalsIgnoreCase(color))
                .toList();
    }

    public List<Garment> findGarmentsBySize(String size) {
        return garments.stream()
                .filter(g -> g.getSize().equalsIgnoreCase(size))
                .toList();
    }
}

public class RMGSystem {
    public static void main(String[] args) {
        // Create fabric
        Fabric cotton = new Fabric("F1", "Cotton", "White", 10.0);

        // Create supplier and add fabric
        Supplier supplier = new Supplier("S1", "TextileCo", "contact@textileco.com");
        supplier.addFabric(cotton);

        // Create garment
        Garment tshirt = new Garment("G1", "T-Shirt", "Basic Tee", "M",
                "White", 29.99, cotton);
        tshirt.updateStock(100);

        // Add to inventory
        Inventory inventory = new Inventory();
        inventory.addGarment(tshirt);

        // Create customer
        Customer customer = new Customer("C1", "John Doe", "john@email.com", "1234567890");

        // Create and process order
        Order order = new Order("O1");
        order.addGarment(tshirt);
        order.setStatus("CONFIRMED");
        customer.placeOrder(order);

        // Print order details
        order.printOrderDetails();

        // Example of using getters
        System.out.println("\nCustomer Details:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Total Orders: " + customer.getOrders().size());
    }
}
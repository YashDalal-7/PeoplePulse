import java.util.*;


public class Customer {
    private String name;
    private List<Item> items;
    
    public Customer(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public String toFileString() {
        StringBuilder sb = new StringBuilder(name);
        for (Item item : items) {
            sb.append(",").append(item.getName()).append(",")
                    .append(item.getQuantity()).append(",")
                    .append(item.getPrice());
        }
        return sb.toString();
    }
    
    public static Customer fromFileString(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        List<Item> items = new ArrayList<>();
        for (int i = 1; i < parts.length; i += 3) {
            String itemName = parts[i];
            int quantity = Integer.parseInt(parts[i + 1]);
            double price = Double.parseDouble(parts[i + 2]);
            items.add(new Item(itemName, quantity, price));
        }
        return new Customer(name, items);
    }
}

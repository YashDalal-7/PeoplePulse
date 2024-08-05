import java.util.*;


public class Customer {
    private String name;
    private List<Item> items;

    // Constructor that takes a name and a list of items
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

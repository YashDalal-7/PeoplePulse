import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerGUI {
    private static final String FILE_NAME = "customers.txt";
    private JTextField nameField;
    private JComboBox<Integer> numItemsComboBox;
    private JPanel itemsPanel;
    private JTextField[] itemNameFields;
    private JTextField[] itemQuantityFields;
    private JTextField[] itemPriceFields;
    private JFrame frame;

    public CustomerGUI() {
        frame = new JFrame("Customer Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField(20);

        JLabel numItemsLabel = new JLabel("Number of Items:");
        numItemsComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        numItemsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItemFields();
            }
        });

        JButton saveButton = new JButton("Save Customer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer();
            }
        });

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateBill();
            }
        });

        JButton viewCustomersButton = new JButton("View Customers");
        viewCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomers();
            }
        });

        JButton deleteCustomersButton = new JButton("Delete Customers");
        deleteCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomers();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(numItemsLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(numItemsComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(generateBillButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(viewCustomersButton, gbc);

        gbc.gridx = 1;
        mainPanel.add(deleteCustomersButton, gbc);

        frame.add(mainPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void updateItemFields() {
        int numItems = (Integer) numItemsComboBox.getSelectedItem();
        itemsPanel.removeAll();

        itemNameFields = new JTextField[numItems];
        itemQuantityFields = new JTextField[numItems];
        itemPriceFields = new JTextField[numItems];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        itemsPanel.setLayout(new GridBagLayout());

        for (int i = 0; i < numItems; i++) {
            gbc.gridx = 0;
            gbc.gridy = i * 3;
            itemsPanel.add(new JLabel("Item " + (i + 1) + " Name:"), gbc);

            gbc.gridx = 1;
            itemNameFields[i] = new JTextField(15);
            itemsPanel.add(itemNameFields[i], gbc);

            gbc.gridx = 0;
            gbc.gridy = i * 3 + 1;
            itemsPanel.add(new JLabel("Quantity:"), gbc);

            gbc.gridx = 1;
            itemQuantityFields[i] = new JTextField(5);
            itemsPanel.add(itemQuantityFields[i], gbc);

            gbc.gridx = 0;
            gbc.gridy = i * 3 + 2;
            itemsPanel.add(new JLabel("Price:"), gbc);

            gbc.gridx = 1;
            itemPriceFields[i] = new JTextField(10);
            itemsPanel.add(itemPriceFields[i], gbc);
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void saveCustomer() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name cannot be empty.");
            return;
        }

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < itemNameFields.length; i++) {
            String itemName = itemNameFields[i].getText();
            String quantityText = itemQuantityFields[i].getText();
            String priceText = itemPriceFields[i].getText();

            if (!itemName.isEmpty() && !quantityText.isEmpty() && !priceText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    double price = Double.parseDouble(priceText);
                    items.add(new Item(itemName, quantity, price));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity or price.");
                    return;
                }
            }
        }

        Customer customer = new Customer(name, items);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(customer.toFileString());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving customer.");
            e.printStackTrace();
        }

        // Clear fields after saving
        nameField.setText("");
        numItemsComboBox.setSelectedIndex(0);
        updateItemFields();  // Clear item fields
    }

    private void generateBill() {
        String name = JOptionPane.showInputDialog(frame, "Enter customer name:");
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name cannot be empty.");
            return;
        }

        Customer customer = loadCustomer(name);
        if (customer == null) {
            JOptionPane.showMessageDialog(frame, "Customer not found.");
            return;
        }

        JFrame billFrame = new JFrame("Customer Bill");
        billFrame.setSize(400, 300);
        billFrame.setLayout(new BorderLayout());

        JPanel billPanel = new JPanel();
        billPanel.setLayout(new GridLayout(0, 4));
        billPanel.add(new JLabel("Item Name"));
        billPanel.add(new JLabel("Quantity"));
        billPanel.add(new JLabel("Price"));
        billPanel.add(new JLabel("Total"));

        double totalAmount = 0.0;
        for (Item item : customer.getItems()) {
            billPanel.add(new JLabel(item.getName()));
            billPanel.add(new JLabel(String.valueOf(item.getQuantity())));
            billPanel.add(new JLabel(String.valueOf(item.getPrice())));
            double itemTotal = item.getQuantity() * item.getPrice();
            billPanel.add(new JLabel(String.format("%.2f", itemTotal)));
            totalAmount += itemTotal;
        }

        billPanel.add(new JLabel("Total Amount"));
        billPanel.add(new JLabel());
        billPanel.add(new JLabel());
        billPanel.add(new JLabel(String.format("%.2f", totalAmount)));

        billFrame.add(billPanel, BorderLayout.CENTER);
        billFrame.setVisible(true);
    }

    private void viewCustomers() {
        JFrame viewFrame = new JFrame("View Customers");
        viewFrame.setSize(300, 400);
        viewFrame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromFileString(line);
                textArea.append(customer.getName() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(viewFrame, "Error reading customer data.");
            e.printStackTrace();
        }

        viewFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        viewFrame.setVisible(true);
    }

    private void deleteCustomers() {
        JFrame deleteFrame = new JFrame("Delete Customers");
        deleteFrame.setSize(300, 400);
        deleteFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        List<JCheckBox> checkBoxes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromFileString(line);
                JCheckBox checkBox = new JCheckBox(customer.getName());
                panel.add(checkBox);
                checkBoxes.add(checkBox);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(deleteFrame, "Error reading customer data.");
            e.printStackTrace();
        }

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> toDelete = new ArrayList<>();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    if (checkBoxes.get(i).isSelected()) {
                        toDelete.add(checkBoxes.get(i).getText());
                    }
                }

                if (toDelete.isEmpty()) {
                    JOptionPane.showMessageDialog(deleteFrame, "No customers selected.");
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
                     BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        Customer customer = Customer.fromFileString(line);
                        if (!toDelete.contains(customer.getName())) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(deleteFrame, "Error deleting customers.");
                    ex.printStackTrace();
                }

                File originalFile = new File(FILE_NAME);
                File tempFile = new File("temp.txt");
                if (originalFile.delete()) {
                    tempFile.renameTo(originalFile);
                }

                deleteFrame.dispose();
                JOptionPane.showMessageDialog(frame, "Selected customers deleted.");
            }
        });

        deleteFrame.add(new JScrollPane(panel), BorderLayout.CENTER);
        deleteFrame.add(deleteButton, BorderLayout.SOUTH);
        deleteFrame.setVisible(true);
    }

    private Customer loadCustomer(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromFileString(line);
                if (customer.getName().equals(name)) {
                    return customer;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading customer data.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerGUI::new);
    }
}

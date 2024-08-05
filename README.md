# PeoplePulse

PeoplePulse is a Java-based customer management system designed to manage customer records and generate billing information. The application leverages core Java Swing components and file I/O techniques to provide a graphical interface for interacting with customer data.

## Overview

### Key Features

- **Customer Management**: Create and save customer profiles with multiple items.
- **Billing System**: Generate itemized bills and calculate totals.
- **Customer List Management**: View all customers and delete selected records.

## Java Techniques Used

### Swing for GUI

- **JFrame**: The main window of the application is created using `JFrame`, which serves as the primary container for all GUI components.
- **JPanel**: Used to organize components in a grid layout or other specified layouts.
- **JTextField**: Provides text input fields for entering customer names, item details (name, quantity, price), and other data.
- **JButton**: Creates interactive buttons for operations like saving customers, generating bills, viewing, and deleting customers.
- **JComboBox**: Allows users to select the number of items from a dropdown menu.
- **JScrollPane**: Enables scrolling capabilities for panels containing dynamic content such as the list of items or customers.

### Layout Management

- **GridBagLayout**: Used for arranging components in a flexible grid. This layout is applied to position labels, text fields, and buttons in a structured manner within the main panel.
- **GridLayout**: Employed for the items panel to uniformly arrange item fields (name, quantity, price) in a grid.

### File I/O

- **BufferedWriter**: Used for writing customer data to a text file, allowing for efficient writing of text data.
- **BufferedReader**: Reads customer data from a text file to load customer profiles and handle operations such as viewing and deleting customers.
- **FileWriter** and **FileReader**: Handle file operations, including appending data to an existing file and reading from a file.

### Data Handling

- **ArrayList**: Utilized to store dynamic lists of items and checkboxes. This collection provides flexible management of items and customers, accommodating changes in size.
- **List**: Interface used for managing and manipulating collections of `Item` objects associated with each customer.

### Error Handling

- **Exception Handling**: The application uses try-catch blocks to handle potential `IOException` and `NumberFormatException`, providing user-friendly messages and ensuring the stability of file operations and input parsing.

### Event Handling

- **ActionListener**: Implements event handling for buttons and dropdowns. Action listeners are used to respond to user interactions, such as clicking buttons to save data or generate bills.

### Miscellaneous

- **JOptionPane**: Used for displaying dialog boxes that show error messages, prompts, or confirmation dialogs, enhancing user interaction and feedback.
- **GridLayout**: Applied in the bill display panel to align item details and billing information neatly.

# Project Setup and Execution

## Open the Project in Your IDE:
* Import the project into your preferred IDE.

## Build and Run the Application:
* Locate the `CustomerGUI.java` file.
* Execute the `CustomerGUI` class to launch the application.

## Code Structure
* **Customer.java**: Manages customer data and interactions. Handles saving and loading customer data from files.
* **Item.java**: Represents individual items with attributes such as name, quantity, and price.
* **CustomerGUI.java**: Provides the user interface for adding customers, generating bills, viewing, and deleting records.

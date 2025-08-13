package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class registerCustomerController {

    @FXML private TextField nameField;
    @FXML private TextField numberField;
    @FXML private ComboBox<String> productComboBox;
    @FXML private Label productLabel;
    @FXML private TextField priceField;
    @FXML private Button addButton;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> numberColumn;
    @FXML private TableColumn<Customer, String> productColumn;
    @FXML private TableColumn<Customer, Double> priceColumn;

    private final Map<String, Double> productPriceMap = new LinkedHashMap<>();
    private final List<String> selectedProducts = new ArrayList<>();
    private double totalPrice = 0.0;
    private final File customerFile = new File("customer.bin");

    @FXML
    public void initialize() {
        setupProductPrices();
        productComboBox.setItems(FXCollections.observableArrayList(productPriceMap.keySet()));
        setupTableColumns();
        loadTableData();
    }

    private void setupProductPrices() {
        productPriceMap.put("Moong Dal", 100.0);
        productPriceMap.put("Chana Dal", 90.0);
        productPriceMap.put("Masoor Dal", 85.0);
        productPriceMap.put("Alu", 40.0);
        productPriceMap.put("Rajma", 110.0);
        productPriceMap.put("Chaaler Ata", 60.0);
        productPriceMap.put("Pulao Chal", 120.0);
        productPriceMap.put("Mouri", 95.0);
        productPriceMap.put("Holud Guro", 75.0);
        productPriceMap.put("Oil", 160.0);
        productPriceMap.put("Chanachur", 55.0);
        productPriceMap.put("Suji", 50.0);
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void handleProductSelection() {
        String selected = productComboBox.getValue();
        if (selected != null && productPriceMap.containsKey(selected)) {
            double price = productPriceMap.get(selected);
            productLabel.setText(selected + " price: " + price + " taka");
        }
    }

    @FXML
    private void handleAddProduct() {
        String selected = productComboBox.getValue();
        if (selected == null) {
            showAlert(AlertType.WARNING, "No Product Selected", "Please select a product.");
            return;
        }
        double price = productPriceMap.get(selected);
        selectedProducts.add(selected);
        totalPrice += price;

        productLabel.setText(String.join(", ", selectedProducts));
        priceField.setText(String.valueOf(totalPrice));
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String number = numberField.getText();
        String product = String.join(", ", selectedProducts);

        if (name.isEmpty() || number.isEmpty() || product.isEmpty()) {
            showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields and add at least one product.");
            return;
        }

        Customer customer = new Customer(name, number, product, totalPrice);
        List<Customer> customers = BinaryFileHelper.readAllObjects(customerFile);
        customers.add(customer);
        BinaryFileHelper.writeAllObjects(customerFile, customers);

        loadTableData();
        clearForm();
        showAlert(AlertType.INFORMATION, "Success", "Customer saved successfully.");
    }

    private void loadTableData() {
        List<Customer> customers = BinaryFileHelper.readAllObjects(customerFile);
        customerTable.setItems(FXCollections.observableArrayList(customers));
    }

    private void clearForm() {
        nameField.clear();
        numberField.clear();
        productComboBox.getSelectionModel().clearSelection();
        selectedProducts.clear();
        productLabel.setText("Added Product");
        totalPrice = 0.0;
        priceField.clear();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/Cashier/Cashierdashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open dashboard.");
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

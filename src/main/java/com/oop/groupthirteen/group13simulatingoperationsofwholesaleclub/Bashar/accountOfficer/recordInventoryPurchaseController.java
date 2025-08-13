package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager.DailySales;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class recordInventoryPurchaseController {

    @FXML private ComboBox<String> productIdCombo;
    @FXML private TextField productNameField;
    @FXML private TextField priceField;
    @FXML private TextField customerNameField;
    @FXML private TextField customerNumberField;
    @FXML private Button saveButton;
    @FXML private Button backButton;
    @FXML private TableView<InventoryPurchase> inventoryTable;
    @FXML private TableColumn<InventoryPurchase, String> productIdColumn;
    @FXML private TableColumn<InventoryPurchase, String> productNameColumn;
    @FXML private TableColumn<InventoryPurchase, Double> priceColumn;
    @FXML private TableColumn<InventoryPurchase, String> customerNameColumn;
    @FXML private TableColumn<InventoryPurchase, String> customerNumberColumn;

    private final File salesFile = new File("dailySales.bin");
    private final File purchaseFile = new File("InventoryPurchase.bin");

    private List<DailySales> salesData;
    private ObservableList<InventoryPurchase> purchaseData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        salesData = BinaryFileHelper.readAllObjects(salesFile);
        for (DailySales ds : salesData) {
            productIdCombo.getItems().add(ds.getProductId());
        }

        productIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductId()));
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        customerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerName()));
        customerNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerNumber()));

        List<InventoryPurchase> saved = BinaryFileHelper.readAllObjects(purchaseFile);
        purchaseData.addAll(saved);
        inventoryTable.setItems(purchaseData);
    }

    @FXML
    private void handleProductSelection() {
        String selectedId = productIdCombo.getValue();
        if (selectedId == null) return;

        for (DailySales ds : salesData) {
            if (ds.getProductId().equals(selectedId)) {
                productNameField.setText(ds.getProductName());
                priceField.setText(String.valueOf(ds.getTotalPrice()));
                break;
            }
        }
    }

    @FXML
    private void handleSave() {
        String productId = productIdCombo.getValue();
        String productName = productNameField.getText();
        String priceText = priceField.getText();
        String customerName = customerNameField.getText();
        String customerNumber = customerNumberField.getText();

        if (productId == null || productName.isEmpty() || priceText.isEmpty() ||
                customerName.isEmpty() || customerNumber.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Input", "Please fill all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            InventoryPurchase purchase = new InventoryPurchase(productId, productName, price, customerName, customerNumber);
            purchaseData.add(purchase);
            BinaryFileHelper.writeAllObjects(purchaseFile, purchaseData);
            inventoryTable.refresh();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Price must be a number.");
        }
    }

    private void clearFields() {
        productIdCombo.getSelectionModel().clearSelection();
        productNameField.clear();
        priceField.clear();
        customerNameField.clear();
        customerNumberField.clear();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/accountOfficer");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not go back.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

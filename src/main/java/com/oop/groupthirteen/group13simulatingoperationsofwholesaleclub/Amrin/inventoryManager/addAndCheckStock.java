package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addAndCheckStock {

    @FXML private ComboBox<String> shipmentIdComboBox;
    @FXML private TextField productNameField;
    @FXML private TextField quantityField;
    @FXML private TableView<AddedStock> stockTableView;
    @FXML private TableColumn<AddedStock, String> shipmentIdColumn;
    @FXML private TableColumn<AddedStock, String> productNameColumn;
    @FXML private TableColumn<AddedStock, String> quantityColumn;

    private final File shipmentFile = new File("shipment.bin");
    private final File addedStockFile = new File("addProductShipment.bin");

    private final ObservableList<AddedStock> stockList = FXCollections.observableArrayList();
    private final Map<String, Shipment> shipmentMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Load existing shipments into ComboBox
        List<Shipment> shipments = BinaryFileHelper.readAllObjects(shipmentFile);
        for (Shipment s : shipments) {
            shipmentMap.put(s.getShipmentId(), s);
        }
        shipmentIdComboBox.setItems(FXCollections.observableArrayList(shipmentMap.keySet()));

        // Setup TableView columns
        shipmentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getShipmentId()));
        productNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProductName()));
        quantityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));

        // Load existing stock records
        List<AddedStock> savedStocks = BinaryFileHelper.readAllObjects(addedStockFile);
        stockList.setAll(savedStocks);
        stockTableView.setItems(stockList);
    }

    @FXML
    private void handleShipmentSelection() {
        String selectedId = shipmentIdComboBox.getValue();
        if (selectedId != null && shipmentMap.containsKey(selectedId)) {
            productNameField.setText(shipmentMap.get(selectedId).getProductName());
        }
    }

    @FXML
    private void handleSaveAction() {
        String shipmentId = shipmentIdComboBox.getValue();
        String productName = productNameField.getText();
        String quantityText = quantityField.getText();

        if (shipmentId == null || productName.isEmpty() || quantityText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Input", "Please fill in all fields.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Enter a positive number for quantity.");
            return;
        }

        AddedStock addedStock = new AddedStock(shipmentId, productName, quantity);
        BinaryFileHelper.saveObject(addedStockFile, addedStock);
        stockList.add(addedStock);
        stockTableView.refresh();
        clearForm();
    }

    @FXML
    private void handleBackAction() {
        try {
            SceneSwitcher.switchTo("Amrin/inventoryManager/inventoryManagerDashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not go back to dashboard.");
        }
    }

    private void clearForm() {
        shipmentIdComboBox.setValue(null);
        productNameField.clear();
        quantityField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

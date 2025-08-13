package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class trackAddDamaged {

    @FXML private ComboBox<String> shipmentIdComboBox;
    @FXML private TextField productNameField;
    @FXML private TextField nowQuantityField;
    @FXML private TextField damagedQuantityField;

    @FXML private TreeTableView<AddedStock> damageTreeTableView;
    @FXML private TreeTableColumn<AddedStock, String> shipmentIdColumn;
    @FXML private TreeTableColumn<AddedStock, String> productNameColumn;
    @FXML private TreeTableColumn<AddedStock, String> nowQuantityColumn;
    @FXML private TreeTableColumn<AddedStock, String> damageQuantityColumn;

    private final File addedStockFile = new File("addProductShipment.bin");
    private List<AddedStock> addedStockList = new ArrayList<>();
    private final Map<String, AddedStock> shipmentMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Load data from file
        addedStockList = BinaryFileHelper.readAllObjects(addedStockFile);

        // Map by shipment ID and load ComboBox
        for (AddedStock stock : addedStockList) {
            shipmentMap.put(stock.getShipmentId(), stock);
        }
        shipmentIdComboBox.setItems(FXCollections.observableArrayList(shipmentMap.keySet()));

        // Set up TreeTableView columns
        shipmentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getShipmentId()));

        productNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getProductName()));

        nowQuantityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getQuantity())));
        damageQuantityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getDamagedQuantity())));

        refreshTreeTable();
    }

    @FXML
    private void handleShipmentSelection() {
        String selectedId = shipmentIdComboBox.getValue();
        if (selectedId != null && shipmentMap.containsKey(selectedId)) {
            AddedStock stock = shipmentMap.get(selectedId);
            productNameField.setText(stock.getProductName());
            nowQuantityField.setText(String.valueOf(stock.getQuantity()));
        }
    }

    @FXML
    private void handleSaveAction() {
        String shipmentId = shipmentIdComboBox.getValue();
        String damagedText = damagedQuantityField.getText();

        if (shipmentId == null || damagedText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a shipment and enter damage quantity.");
            return;
        }

        int damage;
        try {
            damage = Integer.parseInt(damagedText);
            if (damage < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Damage must be a non-negative number.");
            return;
        }

        AddedStock stock = shipmentMap.get(shipmentId);
        int originalQty = stock.getQuantity();
        int remainingQty = Math.max(0, originalQty - damage);

        // Update values
        stock.setDamagedQuantity(damage);
        stock.setQuantity(remainingQty);

        // Save all back to file
        BinaryFileHelper.writeAllObjects(addedStockFile, new ArrayList<>(shipmentMap.values()));
        refreshTreeTable();
        clearForm();
    }

    private void refreshTreeTable() {
        TreeItem<AddedStock> root = new TreeItem<>();
        root.setExpanded(true);

        for (AddedStock stock : shipmentMap.values()) {
            TreeItem<AddedStock> item = new TreeItem<>(stock);
            root.getChildren().add(item);
        }

        damageTreeTableView.setRoot(root);
        damageTreeTableView.setShowRoot(false);
    }

    private void clearForm() {
        shipmentIdComboBox.setValue(null);
        productNameField.clear();
        nowQuantityField.clear();
        damagedQuantityField.clear();
    }

    @FXML
    private void handleBackAction() {
        try {
            SceneSwitcher.switchTo("Amrin/inventoryManager/inventoryManagerDashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to go back to dashboard.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

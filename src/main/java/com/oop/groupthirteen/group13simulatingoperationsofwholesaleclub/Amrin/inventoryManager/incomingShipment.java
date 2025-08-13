package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class incomingShipment {

    // === FXML UI Components ===
    @FXML private TextField shipmentIdField;
    @FXML private TextField productNameField;
    @FXML private DatePicker importDatePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private DatePicker expiryDatePicker;

    @FXML private TableView<Shipment> shipmentTableView;
    @FXML private TableColumn<Shipment, String> shipmentIdColumn;
    @FXML private TableColumn<Shipment, String> productNameColumn;
    @FXML private TableColumn<Shipment, String> importDateColumn;
    @FXML private TableColumn<Shipment, String> categoryColumn;
    @FXML private TableColumn<Shipment, String> expiryDateColumn;

    private final File shipmentFile = new File("shipment.bin");
    private final ObservableList<Shipment> shipmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        shipmentIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getShipmentId()));

        productNameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));

        importDateColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getImportDate() != null ?
                                cellData.getValue().getImportDate().toString() : ""));

        categoryColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));

        expiryDateColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getExpiryDate() != null ?
                                cellData.getValue().getExpiryDate().toString() : ""));

        categoryComboBox.setItems(FXCollections.observableArrayList("Beverages", "Snacks", "Dairy", "Electronics", "Clothing"));

        List<Shipment> savedShipments = BinaryFileHelper.readAllObjects(shipmentFile);
        shipmentList.setAll(savedShipments);
        shipmentTableView.setItems(shipmentList);
    }

    @FXML
    private void handleSaveAction() {
        String shipmentId = shipmentIdField.getText().trim();
        String productName = productNameField.getText().trim();
        LocalDate importDate = importDatePicker.getValue();
        String category = categoryComboBox.getValue();
        LocalDate expiryDate = expiryDatePicker.getValue();

        if (shipmentId.isEmpty() || productName.isEmpty() || importDate == null || category == null || expiryDate == null) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }
        Shipment shipment = new Shipment(shipmentId, productName, importDate, category, expiryDate);
        BinaryFileHelper.saveObject(shipmentFile, shipment);
        shipmentList.add(shipment);
        shipmentTableView.refresh();
        clearForm();
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

    private void clearForm() {
        shipmentIdField.clear();
        productNameField.clear();
        importDatePicker.setValue(null);
        categoryComboBox.setValue(null);
        expiryDatePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

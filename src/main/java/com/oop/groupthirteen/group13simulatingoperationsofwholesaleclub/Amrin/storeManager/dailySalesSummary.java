package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class dailySalesSummary {

    @FXML private TextField productIdField;
    @FXML private DatePicker datePicker;
    @FXML private TextField productNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    @FXML private TreeTableView<DailySales> salesTreeTableView;
    @FXML private TreeTableColumn<DailySales, String> productIdColumn;
    @FXML private TreeTableColumn<DailySales, String> dateColumn;
    @FXML private TreeTableColumn<DailySales, String> productNameColumn;
    @FXML private TreeTableColumn<DailySales, String> quantityColumn;
    @FXML private TreeTableColumn<DailySales, String> priceColumn;

    private final File salesFile = new File("dailySales.bin");
    private List<DailySales> salesList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Bind columns
        productIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getProductId()));
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getDate().toString()));
        productNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getValue().getProductName()));
        quantityColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getQuantity())));
        priceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getValue().getTotalPrice())));

        // Load saved data
        salesList = BinaryFileHelper.readAllObjects(salesFile);
        refreshTreeTable();
    }

    @FXML
    private void handleSaveAction() {
        String productId = productIdField.getText();
        LocalDate date = datePicker.getValue();
        String productName = productNameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        // Validation
        if (productId.isEmpty() || date == null || productName.isEmpty() ||
                quantityText.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        int quantity;
        double unitPrice;
        try {
            quantity = Integer.parseInt(quantityText);
            unitPrice = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Quantity and Price must be numbers.");
            return;
        }

        // Save new entry
        DailySales sale = new DailySales(productId, date, productName, quantity, unitPrice);
        salesList.add(sale);
        BinaryFileHelper.writeAllObjects(salesFile, salesList);

        // Refresh UI
        refreshTreeTable();
        clearForm();
    }

    private void refreshTreeTable() {
        TreeItem<DailySales> root = new TreeItem<>();
        root.setExpanded(true);
        for (DailySales sale : salesList) {
            root.getChildren().add(new TreeItem<>(sale));
        }
        salesTreeTableView.setRoot(root);
        salesTreeTableView.setShowRoot(false);
    }

    private void clearForm() {
        productIdField.clear();
        datePicker.setValue(null);
        productNameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    @FXML
    private void handleBackAction() {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/storeManagerDashboard");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not go back to dashboard.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

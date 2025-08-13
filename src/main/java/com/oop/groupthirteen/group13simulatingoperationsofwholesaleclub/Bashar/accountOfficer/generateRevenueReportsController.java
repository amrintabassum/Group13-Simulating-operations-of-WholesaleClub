package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer.RevenueReport;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer.InventoryPurchase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class generateRevenueReportsController {

    @FXML private ComboBox<String> productNameCombo;
    @FXML private TextField priceField;
    @FXML private DatePicker datePicker;
    @FXML private TextField revenueField;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    @FXML private TableView<RevenueReport> revenueTable;
    @FXML private TableColumn<RevenueReport, String> productNameColumn;
    @FXML private TableColumn<RevenueReport, String> priceColumn;
    @FXML private TableColumn<RevenueReport, String> dateColumn;
    @FXML private TableColumn<RevenueReport, String> revenueColumn;

    private final File inventoryFile = new File("InventoryPurchase.bin");
    private final File revenueFile = new File("revenue.bin");

    private final ObservableList<RevenueReport> revenueList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup columns
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getPrice())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        revenueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getRevenue())));

        // Load old revenue records
        List<RevenueReport> savedReports = BinaryFileHelper.readAllObjects(revenueFile);
        revenueList.addAll(savedReports);
        revenueTable.setItems(revenueList);

        // Load product names from InventoryPurchase.bin
        List<InventoryPurchase> inventoryList = BinaryFileHelper.readAllObjects(inventoryFile);
        Set<String> uniqueProductNames = inventoryList.stream()
                .map(InventoryPurchase::getProductName)
                .collect(Collectors.toCollection(TreeSet::new));
        productNameCombo.setItems(FXCollections.observableArrayList(uniqueProductNames));
    }

    @FXML
    private void handleProductSelect() {
        String selectedProduct = productNameCombo.getValue();
        if (selectedProduct == null || selectedProduct.isEmpty()) return;

        List<InventoryPurchase> inventoryList = BinaryFileHelper.readAllObjects(inventoryFile);
        inventoryList.stream()
                .filter(p -> p.getProductName().equals(selectedProduct))
                .findFirst()
                .ifPresent(p -> priceField.setText(String.format("%.2f", p.getPrice())));
    }

    @FXML
    private void handleSave() {
        String productName = productNameCombo.getValue();
        String priceText = priceField.getText();
        LocalDate date = datePicker.getValue();
        String revenueText = revenueField.getText();

        if (productName == null || priceText.isEmpty() || date == null || revenueText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            double revenue = Double.parseDouble(revenueText);

            RevenueReport report = new RevenueReport(productName, price, date, revenue);
            revenueList.add(report);
            BinaryFileHelper.writeAllObjects(revenueFile, new ArrayList<>(revenueList));
            revenueTable.refresh();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Price and Revenue must be numbers.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/accountOfficer");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open Account Officer Dashboard.");
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

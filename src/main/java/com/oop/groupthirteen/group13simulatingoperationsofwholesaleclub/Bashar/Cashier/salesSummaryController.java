package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier.SalesSummary;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class salesSummaryController {

    @FXML private TextField transactionField;
    @FXML private TextField totalProductField;
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField priceField;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    @FXML private TableView<SalesSummary> salesTable;
    @FXML private TableColumn<SalesSummary, String> transactionColumn;
    @FXML private TableColumn<SalesSummary, String> totalProductColumn;
    @FXML private TableColumn<SalesSummary, String> productNameColumn;
    @FXML private TableColumn<SalesSummary, String> priceColumn;

    private final File file = new File("salesSummary.bin");

    @FXML
    public void initialize() {
        // ComboBox items
        productComboBox.setItems(FXCollections.observableArrayList(
                "Moong Dal", "Chana Dal", "Masoor Dal", "Alu", "Rajma",
                "Chaaler Ata", "Pulao Chal", "Mouri", "Holud Guro",
                "Oil", "Chanachur", "Suji"
        ));

        transactionColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTransaction()));
        totalProductColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTotalProduct()));
        productNameColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getProductName()));
        priceColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getPrice()));

        loadTable();
    }

    @FXML
    private void handleSave() {
        String transaction = transactionField.getText();
        String totalProduct = totalProductField.getText();
        String productName = productComboBox.getValue();
        String price = priceField.getText();

        if (transaction.isEmpty() || totalProduct.isEmpty() || productName == null || price.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        SalesSummary summary = new SalesSummary(transaction, totalProduct, productName, price);
        List<SalesSummary> list = BinaryFileHelper.readAllObjects(file);
        list.add(summary);
        BinaryFileHelper.writeAllObjects(file, list);

        loadTable();
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Sales record saved.");
    }

    private void loadTable() {
        List<SalesSummary> list = BinaryFileHelper.readAllObjects(file);
        salesTable.setItems(FXCollections.observableArrayList(list));
    }

    private void clearFields() {
        transactionField.clear();
        totalProductField.clear();
        priceField.clear();
        productComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/Cashier/Cashierdashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open dashboard.");
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

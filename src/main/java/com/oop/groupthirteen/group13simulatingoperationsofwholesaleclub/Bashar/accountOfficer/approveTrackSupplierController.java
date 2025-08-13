package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class approveTrackSupplierController {

    @FXML private TextField supplierNameField;
    @FXML private ComboBox<String> paymentTypeCombo;
    @FXML private TextField totalAmountField;

    @FXML private Button payButton;
    @FXML private Button backButton;

    @FXML private TreeTableView<SupplierPayment> supplierTable;
    @FXML private TreeTableColumn<SupplierPayment, String> nameColumn;
    @FXML private TreeTableColumn<SupplierPayment, String> paymentColumn;
    @FXML private TreeTableColumn<SupplierPayment, String> amountColumn;

    private final File supplierFile = new File("TrackSupplier.bin");
    private List<SupplierPayment> paymentList = new ArrayList<>();

    @FXML
    public void initialize() {
        paymentTypeCombo.getItems().addAll("Bkash", "Nagad", "Bank");

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getSupplierName()));
        paymentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue().getPaymentType()));
        amountColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getValue().getAmount())));

        List<SupplierPayment> loaded = BinaryFileHelper.readAllObjects(supplierFile);
        if (loaded != null) paymentList = loaded;

        refreshTable();
    }

    @FXML
    private void handlePay() {
        String name = supplierNameField.getText();
        String paymentType = paymentTypeCombo.getValue();
        String amountText = totalAmountField.getText();

        if (name.isEmpty() || paymentType == null || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Amount must be a number.");
            return;
        }

        SupplierPayment payment = new SupplierPayment(name, paymentType, amount);
        paymentList.add(payment);
        BinaryFileHelper.writeAllObjects(supplierFile, paymentList);

        refreshTable();
        clearForm();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/accountOfficer");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open dashboard.");
        }
    }

    private void refreshTable() {
        TreeItem<SupplierPayment> root = new TreeItem<>();
        for (SupplierPayment sp : paymentList) {
            root.getChildren().add(new TreeItem<>(sp));
        }
        supplierTable.setRoot(root);
        supplierTable.setShowRoot(false);
    }

    private void clearForm() {
        supplierNameField.clear();
        paymentTypeCombo.getSelectionModel().clearSelection();
        totalAmountField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

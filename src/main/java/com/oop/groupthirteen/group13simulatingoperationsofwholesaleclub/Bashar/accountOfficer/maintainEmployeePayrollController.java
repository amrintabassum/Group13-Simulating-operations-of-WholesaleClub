package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class maintainEmployeePayrollController {

    @FXML private TextField employeeIdField;
    @FXML private TextField employeeNameField;
    @FXML private ComboBox<String> paymentTypeCombo;
    @FXML private TextField totalAmountField;
    @FXML private Button payButton;
    @FXML private Button backButton;

    @FXML private TreeTableView<EmployeePayroll> employeeTable;
    @FXML private TreeTableColumn<EmployeePayroll, String> idColumn;
    @FXML private TreeTableColumn<EmployeePayroll, String> nameColumn;
    @FXML private TreeTableColumn<EmployeePayroll, String> paymentColumn;
    @FXML private TreeTableColumn<EmployeePayroll, String> amountColumn;

    private final File payrollFile = new File("employee.bin");
    private List<EmployeePayroll> payrollList = new ArrayList<>();

    @FXML
    public void initialize() {
        paymentTypeCombo.getItems().addAll("Bkash", "Nagad", "Bank");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmployeeId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmployeeName()));
        paymentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getPaymentType()));
        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getTotalAmount())));

        payrollList = BinaryFileHelper.readAllObjects(payrollFile);
        refreshTable();
    }

    @FXML
    private void handlePay() {
        String id = employeeIdField.getText();
        String name = employeeNameField.getText();
        String payment = paymentTypeCombo.getValue();
        String amountText = totalAmountField.getText();

        if (id.isEmpty() || name.isEmpty() || payment == null || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please complete all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Total amount must be a number.");
            return;
        }

        EmployeePayroll payroll = new EmployeePayroll(id, name, payment, amount);
        payrollList.add(payroll);
        BinaryFileHelper.writeAllObjects(payrollFile, payrollList);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/accountOfficer");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to open dashboard.");
        }
    }

    private void refreshTable() {
        TreeItem<EmployeePayroll> root = new TreeItem<>();
        root.setExpanded(true);
        for (EmployeePayroll emp : payrollList) {
            root.getChildren().add(new TreeItem<>(emp));
        }
        employeeTable.setRoot(root);
        employeeTable.setShowRoot(false);
    }

    private void clearForm() {
        employeeIdField.clear();
        employeeNameField.clear();
        paymentTypeCombo.getSelectionModel().clearSelection();
        totalAmountField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

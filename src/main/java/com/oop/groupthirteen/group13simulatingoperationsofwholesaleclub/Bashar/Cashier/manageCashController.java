package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier.CashTransaction;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class manageCashController {

    @FXML private TextField cashInField;
    @FXML private TextField cashOutField;
    @FXML private DatePicker validateDatePicker;
    @FXML private Label confirmationLabel;
    @FXML private TableView<CashTransaction> cashTable;
    @FXML private TableColumn<CashTransaction, String> cashInColumn;
    @FXML private TableColumn<CashTransaction, String> cashOutColumn;
    @FXML private TableColumn<CashTransaction, String> validateColumn;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    private final File file = new File("manageCash.bin");

    @FXML
    public void initialize() {
        cashInColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCashIn()));
        cashOutColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCashOut()));
        validateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValidateDate()));
        loadTableData();
    }

    @FXML
    private void handleSave() {
        String cashIn = cashInField.getText();
        String cashOut = cashOutField.getText();
        String date = validateDatePicker.getValue() != null ? validateDatePicker.getValue().toString() : "";

        if (cashIn.isEmpty() || cashOut.isEmpty() || date.isEmpty()) {
            confirmationLabel.setText("Please fill all fields.");
            return;
        }

        CashTransaction transaction = new CashTransaction(cashIn, cashOut, date);
        List<CashTransaction> list = BinaryFileHelper.readAllObjects(file);
        list.add(transaction);
        BinaryFileHelper.writeAllObjects(file, list);

        confirmationLabel.setText("You cashed in on: " + date);
        clearFields();
        loadTableData();
    }

    private void clearFields() {
        cashInField.clear();
        cashOutField.clear();
        validateDatePicker.setValue(null);
    }

    private void loadTableData() {
        List<CashTransaction> list = BinaryFileHelper.readAllObjects(file);
        cashTable.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Bashar/Cashier/Cashierdashboard");
        } catch (IOException e) {
            confirmationLabel.setText("Failed to open dashboard.");
        }
    }
}

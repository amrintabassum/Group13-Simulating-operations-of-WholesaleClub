package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.List;

public class applyDiscountsController {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> numberColumn;
    @FXML private TableColumn<Customer, String> productColumn;
    @FXML private TableColumn<Customer, Double> priceColumn;

    private final File customerFile = new File("customer.bin");

    @FXML
    public void initialize() {
        setupTableColumns();
        loadCustomerData();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadCustomerData() {
        List<Customer> customers = BinaryFileHelper.readAllObjects(customerFile);
        customerTable.setItems(FXCollections.observableArrayList(customers));
    }

    @FXML
    private void handleBack() {
        // Navigation logic (optional)
    }
}

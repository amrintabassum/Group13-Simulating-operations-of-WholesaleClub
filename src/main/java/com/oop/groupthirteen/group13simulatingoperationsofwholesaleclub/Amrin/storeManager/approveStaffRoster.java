package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager.StaffRoster;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class approveStaffRoster {

    @FXML private TextField staffIdField;
    @FXML private TextField staffNameField;
    @FXML private DatePicker validationDatePicker;
    @FXML private ComboBox<String> shiftCombo;
    @FXML private ComboBox<String> jobPositionCombo;

    @FXML private TreeTableView<StaffRoster> staffTreeTable;
    @FXML private TreeTableColumn<StaffRoster, String> staffIdColumn;
    @FXML private TreeTableColumn<StaffRoster, String> staffNameColumn;
    @FXML private TreeTableColumn<StaffRoster, String> validateColumn;
    @FXML private TreeTableColumn<StaffRoster, String> shiftColumn;
    @FXML private TreeTableColumn<StaffRoster, String> positionColumn;

    private final File staffFile = new File("approveStaff.bin");
    private List<StaffRoster> staffList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Populate ComboBoxes
        shiftCombo.getItems().addAll("Day", "Night");
        jobPositionCombo.getItems().addAll("Supervisor", "Cashier", "Stocker", "Cleaner", "Security");

        // Load existing data
        staffList = BinaryFileHelper.readAllObjects(staffFile);

        // Bind columns
        staffIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStaffId()));
        staffNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStaffName()));
        validateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getValidationEnd().toString()));
        shiftColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getShift()));
        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getJobPosition()));

        refreshTreeTable();
    }

    @FXML
    private void handleSave() {
        String id = staffIdField.getText();
        String name = staffNameField.getText();
        LocalDate date = validationDatePicker.getValue();
        String shift = shiftCombo.getValue();
        String position = jobPositionCombo.getValue();

        if (id.isEmpty() || name.isEmpty() || date == null || shift == null || position == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        StaffRoster roster = new StaffRoster(id, name, date, shift, position);
        staffList.add(roster);
        BinaryFileHelper.writeAllObjects(staffFile, staffList);

        refreshTreeTable();
        clearForm();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/storeManagerDashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to return to dashboard.");
        }
    }

    private void refreshTreeTable() {
        TreeItem<StaffRoster> root = new TreeItem<>();
        root.setExpanded(true);
        for (StaffRoster roster : staffList) {
            root.getChildren().add(new TreeItem<>(roster));
        }
        staffTreeTable.setRoot(root);
        staffTreeTable.setShowRoot(false);
    }

    private void clearForm() {
        staffIdField.clear();
        staffNameField.clear();
        validationDatePicker.setValue(null);
        shiftCombo.setValue(null);
        jobPositionCombo.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

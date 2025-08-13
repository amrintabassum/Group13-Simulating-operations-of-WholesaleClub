package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ManagePromotions {

    @FXML private ComboBox<String> staffIdCombo;
    @FXML private TextField staffNameField;
    @FXML private TextField currentJobField;
    @FXML private ComboBox<String> promotionCombo;
    @FXML private Label promotionLabel;
    @FXML private Button backButton;

    private List<StaffRoster> staffList;
    private final File staffFile = new File("approveStaff.bin");

    @FXML
    public void initialize() {
        staffList = BinaryFileHelper.readAllObjects(staffFile);
        for (StaffRoster staff : staffList) {
            staffIdCombo.getItems().add(staff.getStaffId());
        }

        promotionCombo.getItems().addAll("Supervisor", "Cashier", "Stocker", "Cleaner", "Security");
    }

    @FXML
    private void handleStaffSelection() {
        String selectedId = staffIdCombo.getValue();
        for (StaffRoster staff : staffList) {
            if (staff.getStaffId().equals(selectedId)) {
                staffNameField.setText(staff.getStaffName());
                currentJobField.setText(staff.getJobPosition());
                break;
            }
        }
    }

    @FXML
    private void handlePromotion() {
        String selectedId = staffIdCombo.getValue();
        String newPosition = promotionCombo.getValue();

        if (selectedId == null || newPosition == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please select Staff ID and Promotion.");
            return;
        }

        for (StaffRoster staff : staffList) {
            if (staff.getStaffId().equals(selectedId)) {
                String oldPosition = staff.getJobPosition();
                staff.setJobPosition(newPosition);
                BinaryFileHelper.writeAllObjects(staffFile, staffList);

                promotionLabel.setText("Staff ID: " + selectedId + ", Name: " + staff.getStaffName() +
                        "\nPromoted from " + oldPosition + " to " + newPosition);
                break;
            }
        }
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/storeManagerDashboard");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not navigate to dashboard.");
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

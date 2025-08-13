package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class accountOfficerController {

    @FXML
    private void handleLogout() {
        try {
            SceneSwitcher.switchTo("Loginfxml");
        } catch (IOException e) {
            showError("Logout Error", "Failed to logout.");
        }
    }

    @FXML
    private void handleRecordInventoryPurchase() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/recordInventoryPurchase");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Inventory Purchase screen.");
        }
    }

    @FXML
    private void handleGenerateRevenueReports() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/generateRevenueReports");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Revenue Reports screen.");
        }
    }

    @FXML
    private void handleApproveTrackSupplier() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/approveTrackSupplier");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Supplier Approval screen.");
        }
    }

    @FXML
    private void handleMaintainEmployeePayroll() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/maintainEmployeePayroll");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Payroll screen.");
        }
    }

    @FXML
    private void handleTrackPaymentDues() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/trackPaymentDues");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Payment Dues screen.");
        }
    }

    @FXML
    private void handleReviewPastTransactions() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/reviewPastTransactions");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Transaction Review screen.");
        }
    }

    @FXML
    private void handleUploadFinancialDocuments() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/uploadFinancialDocuments");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Upload Documents screen.");
        }
    }

    @FXML
    private void handleGenerateExpensesReports() {
        try {
            SceneSwitcher.switchTo("Bashar/accountOfficer/generateExpensesReports");
        } catch (IOException e) {
            showError("Navigation Error", "Failed to open Expenses Report screen.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

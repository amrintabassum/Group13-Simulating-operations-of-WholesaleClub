package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.io.IOException;

public class CashierdashboardController {

    @FXML
    private void handleRegisterCustomer() {
        switchTo("Bashar/Cashier/registerCustomer");
    }

    @FXML
    private void handleApplyDiscounts() {
        switchTo("Bashar/Cashier/applyDiscounts");
    }

    @FXML
    private void handleSalesSummary() {
        switchTo("Bashar/Cashier/salesSummary");
    }

    @FXML
    private void handleManageCash() {
        switchTo("Bashar/Cashier/manageCash");
    }

    @FXML
    private void handleProcessReturns() {
        switchTo("Bashar/Cashier/processCustomerReturns");
    }

    @FXML
    private void handlePrintReceipts() {
        switchTo("Bashar/Cashier/printDuplicateReceipts");
    }

    @FXML
    private void handleTrackQueue() {
        switchTo("Bashar/Cashier/trackCustomerQueue");
    }

    @FXML
    private void handleComplaints() {
        switchTo("Bashar/Cashier/handleComplaints");
    }

    @FXML
    private void handleLogout() {
        switchTo("loginfxml");
    }

    private void switchTo(String fxmlPath) {
        try {
            SceneSwitcher.switchTo(fxmlPath);
        } catch (IOException e) {
            showAlert("Navigation Error", "Failed to open " + fxmlPath + " screen.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

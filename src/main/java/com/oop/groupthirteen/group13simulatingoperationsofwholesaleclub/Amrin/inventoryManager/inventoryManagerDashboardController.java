package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class inventoryManagerDashboardController {

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Loginfxml");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to incomingShipment scene.");
        }
    }

    @FXML
    public void handleIncomingShipment(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/inventoryManager/incomingShipment");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to incomingShipment scene.");
        }
    }

    @FXML
    public void handleTrackDamaged(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/inventoryManager/trackAddDamaged");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to add And Check Stock scene.");
        }
    }

    @FXML
    public void handleStockAudit(ActionEvent event) {
        System.out.println("Perform Stock Audit button clicked.");
    }

    @FXML
    public void handleAssignWarehouse(ActionEvent event) {
        System.out.println("Assign Warehouse Space button clicked.");
    }

    @FXML
    public void handleCheckStock(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/inventoryManager/addAndCheckStock");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to add And Check Stock scene.");
        }
    }

    @FXML
    public void handleUpdateExpiry(ActionEvent event) {
        System.out.println("Update Product Expiry Data button clicked.");
    }

    @FXML
    public void handleApproveReturn(ActionEvent event) {
        System.out.println("Approve Return-to-Vendor button clicked.");
    }

    @FXML
    public void handleViewReport(ActionEvent event) {
        System.out.println("View Supplier Performance Report button clicked.");
    }
}

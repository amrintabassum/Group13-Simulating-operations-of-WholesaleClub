package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

/**
 * Controller for the login screen of the Wholesale Club application.
 */
public class LoginController {

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private Button confirmButton;

    private static DatabaseUtil.User currentUser;

    public static DatabaseUtil.User getCurrentUser() {
        return currentUser;
    }

    @FXML
    private void initialize() {
        userTypeComboBox.getItems().addAll(
                "Inventory Manager",
                "Store Manager",
                "Account Officer",
                "Cashier",
                "Transport Manager",
                "Supplier",
                "IT System Administrator",
                "Data Entry Operator"
        );
    }

    @FXML
    private void handleConfirm(ActionEvent event) {
        String selectedUser = userTypeComboBox.getValue();

        if (selectedUser == null) {
            System.out.println("⚠️ Please select a user type!");
            return;
        }

        System.out.println("Selected user: " + selectedUser);

        try {
            switch (selectedUser) {
                case "Inventory Manager":
                    SceneSwitcher.switchTo("Amrin/inventoryManager/inventoryManagerDashboard");
                    break;
                case "Store Manager":
                    SceneSwitcher.switchTo("Amrin/storeManager/storeManagerDashboard");
                    break;
                case "Account Officer":
                    System.out.println("📝 Account Officer dashboard - Not implemented yet.");
                    break;
                case "Cashier":
                    System.out.println("💰 Cashier dashboard - Not implemented yet.");
                    break;
                case "Transport Manager":
                    System.out.println("🚚 Transport Manager dashboard - Not implemented yet.");
                    break;
                case "Supplier":
                    System.out.println("📦 Supplier dashboard - Not implemented yet.");
                    break;
                case "IT System Administrator":
                    System.out.println("🖥️ IT Admin dashboard - Not implemented yet.");
                    break;
                case "Data Entry Operator":
                    System.out.println("⌨️ Data Entry dashboard - Not implemented yet.");
                    break;
                default:
                    System.out.println("❌ Invalid user type selected!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to switch scene for: " + selectedUser);
        }
    }
}

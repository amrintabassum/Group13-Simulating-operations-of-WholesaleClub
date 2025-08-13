package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher.*;

public class storeManagerDashboardController {

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Loginfxml");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to login scene.");
        }
    }

    @FXML
    private void handleViewDailySales(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/dailySalesSummary");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to login scene.");
        }
    }

    @FXML
    private void handleMonitorSecurityLogs(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/monitorSecurityLogs");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to login scene.");
        }
    }

    @FXML
    private void handleViewCustomerFeedback(ActionEvent event) {
        System.out.println("View Customer Feedback button clicked.");
    }

    @FXML
    private void handlePrintDailyDutyAssignments(ActionEvent event) {
        System.out.println("Print Daily Duty Assignments button clicked.");
    }

    @FXML
    private void handleApproveStaffRoster(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/approveStaffRoster");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to login scene.");
        }
    }

    @FXML
    private void handleManagePromotions(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/managePromotions");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to switch to login scene.");
        }
    }

    @FXML
    private void handleOverseeStaffAttendance(ActionEvent event) {
        System.out.println("Oversee Staff Attendance button clicked.");
    }

    @FXML
    private void handleTrackShiftSwaps(ActionEvent event) {
        System.out.println("Track Shift Swaps button clicked.");
    }
}

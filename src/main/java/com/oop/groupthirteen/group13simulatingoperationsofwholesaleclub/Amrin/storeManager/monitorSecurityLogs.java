package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.SceneSwitcher;
import com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.utils.BinaryFileHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class monitorSecurityLogs {

    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private Label alertLabel;

    private final File logFile = new File("securityLog.bin");
    private List<String> logs = new ArrayList<>();

    @FXML
    public void initialize() {
        logs = BinaryFileHelper.readAllObjects(logFile);
        updateLabel();
    }

    @FXML
    private void handleAdd() {
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();

        if (date == null || time.isEmpty()) {
            showAlert(AlertType.ERROR, "Missing Fields", "Please enter both date and time.");
            return;
        }

        String log = "Alert At → Date: " + date + " | Time: " + time;
        logs.add(log);
        BinaryFileHelper.writeAllObjects(logFile, logs);
        updateLabel();
        clearInputs();
    }

    @FXML
    private void handleUpdate() {
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();

        if (logs.isEmpty()) {
            showAlert(AlertType.WARNING, "No Logs", "Nothing to update.");
            return;
        }

        if (date == null || time.isEmpty()) {
            showAlert(AlertType.ERROR, "Missing Fields", "Please enter both date and time.");
            return;
        }

        String updatedLog = "Alert At → Date: " + date + " | Time: " + time;
        logs.set(logs.size() - 1, updatedLog);
        BinaryFileHelper.writeAllObjects(logFile, logs);
        updateLabel();
        clearInputs();
    }

    @FXML
    private void handleDelete() {
        if (logs.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Empty", "No logs to delete.");
            return;
        }

        logs.remove(logs.size() - 1);
        BinaryFileHelper.writeAllObjects(logFile, logs);
        updateLabel();
    }

    @FXML
    private void handleBack() {
        try {
            SceneSwitcher.switchTo("Amrin/storeManager/storeManagerDashboard");
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Failed to return to dashboard.");
        }
    }

    private void updateLabel() {
        if (logs.isEmpty()) {
            alertLabel.setText("Alert At");
        } else {
            StringBuilder sb = new StringBuilder("Alert At\n");
            for (String log : logs) {
                sb.append(log).append("\n");
            }
            alertLabel.setText(sb.toString());
        }
    }

    private void clearInputs() {
        datePicker.setValue(null);
        timeField.clear();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

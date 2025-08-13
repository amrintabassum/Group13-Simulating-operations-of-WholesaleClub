package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import java.io.Serializable;
import java.time.LocalDate;

public class StaffRoster implements Serializable {
    private String staffId;
    private String staffName;
    private LocalDate validationEnd;
    private String shift;
    private String jobPosition;

    public StaffRoster(String staffId, String staffName, LocalDate validationEnd, String shift, String jobPosition) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.validationEnd = validationEnd;
        this.shift = shift;
        this.jobPosition = jobPosition;
    }

    public String getStaffId() { return staffId; }
    public String getStaffName() { return staffName; }
    public LocalDate getValidationEnd() { return validationEnd; }
    public String getShift() { return shift; }
    public String getJobPosition() { return jobPosition; }

    public void setStaffId(String staffId) { this.staffId = staffId; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public void setValidationEnd(LocalDate validationEnd) { this.validationEnd = validationEnd; }
    public void setShift(String shift) { this.shift = shift; }
    public void setJobPosition(String jobPosition) { this.jobPosition = jobPosition; }
}

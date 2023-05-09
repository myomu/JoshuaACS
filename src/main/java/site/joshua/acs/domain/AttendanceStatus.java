package site.joshua.acs.domain;

public enum AttendanceStatus {
    ATTENDANCE("출석"), ABSENCE("결석");

    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}

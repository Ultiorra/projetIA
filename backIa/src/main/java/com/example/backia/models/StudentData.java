package com.example.backia.models;

import java.util.ArrayList;
import java.util.List;

public class StudentData {
    private double averageNote;
    private double acceptedAppointments;
    private double rejectedAppointments;
    private double pendingAppointments;
    private boolean isIntern;

    public StudentData(double averageNote, double acceptedAppointments, double rejectedAppointments, double pendingAppointments, boolean isIntern) {
        this.averageNote = averageNote;
        this.acceptedAppointments = acceptedAppointments;
        this.rejectedAppointments = rejectedAppointments;
        this.pendingAppointments = pendingAppointments;
        this.isIntern = isIntern;
    }

    public double getAverageNote() {
        return averageNote;
    }

    public double getAcceptedAppointments() {
        return acceptedAppointments;
    }

    public double getRejectedAppointments() {
        return rejectedAppointments;
    }

    public double getPendingAppointments() {
        return pendingAppointments;
    }

    public boolean isIntern() {
        return isIntern;
    }

    public static List<StudentData> getSampleStudentData() {
        List<StudentData> studentDataList = new ArrayList<>();

        studentDataList.add(new StudentData(14.5, 0, 2, 3, true));
        studentDataList.add(new StudentData(16.0, 8, 1, 2, true));
        studentDataList.add(new StudentData(12.0, 0, 5, 1, false));
        studentDataList.add(new StudentData(15.5, 2, 9, 2, false));
        studentDataList.add(new StudentData(13.0, 5, 3, 1, true));
        studentDataList.add(new StudentData(17.0, 1, 0, 1, true));
        studentDataList.add(new StudentData(11.5, 1, 4, 0, false));
        studentDataList.add(new StudentData(15.8, 2, 2, 2, true));
        studentDataList.add(new StudentData(14.2, 1, 1, 3, false));
        studentDataList.add(new StudentData(16.5, 3, 0, 2, true));
        studentDataList.add(new StudentData(13.8, 1, 2, 10, true));
        studentDataList.add(new StudentData(12.7, 0, 3, 1, false));
        studentDataList.add(new StudentData(16.2, 2, 1, 3, true));
        studentDataList.add(new StudentData(14.9, 3, 2, 1, false));
        studentDataList.add(new StudentData(13.5, 1, 1, 4, true));
        studentDataList.add(new StudentData(15.0, 2, 2, 2, false));
        studentDataList.add(new StudentData(14.0, 1, 3, 1, true));
        studentDataList.add(new StudentData(12.5, 0, 4, 1, false));
        studentDataList.add(new StudentData(15.7, 3, 1, 1, true));
        studentDataList.add(new StudentData(13.2, 2, 2, 1, false));
        studentDataList.add(new StudentData(16.7, 4, 1, 1, true));
        studentDataList.add(new StudentData(12.3, 0, 2, 4, false));
        studentDataList.add(new StudentData(14.8, 2, 3, 1, true));
        studentDataList.add(new StudentData(15.3, 3, 1, 1, false));
        studentDataList.add(new StudentData(13.4, 1, 2, 2, true));
        studentDataList.add(new StudentData(11.8, 0, 5, 0, false));
        studentDataList.add(new StudentData(16.3, 2, 1, 3, true));
        studentDataList.add(new StudentData(14.6, 1, 3, 1, true));
        studentDataList.add(new StudentData(15.9, 3, 0, 2, true));
        studentDataList.add(new StudentData(13.1, 1, 5, 3, false));
        studentDataList.add(new StudentData(12.9, 0, 4, 1, false));
        studentDataList.add(new StudentData(12.9, 2, 1, 1, true));
        studentDataList.add(new StudentData(16.8, 3, 1, 1, true));
        studentDataList.add(new StudentData(14.4, 1, 2, 2, false));
        studentDataList.add(new StudentData(15.6, 2, 2, 1, true));
        studentDataList.add(new StudentData(13.7, 1, 1, 3, true));
        return studentDataList;
    }
}

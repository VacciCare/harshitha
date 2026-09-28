package com.example.myapplication;

public class AppointmentModel {

    private String appointmentId;
    private String child;
    private String vaccine;
    private String hospital;
    private String date;
    private String time;
    private String status;

    public AppointmentModel(
            String appointmentId,
            String child,
            String vaccine,
            String hospital,
            String date,
            String time,
            String status) {

        this.appointmentId = appointmentId;
        this.child = child;
        this.vaccine = vaccine;
        this.hospital = hospital;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getChild() {
        return child;
    }

    public String getVaccine() {
        return vaccine;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
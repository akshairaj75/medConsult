package com.backend.medconsult.dto.appointmentDto;

import java.time.LocalDateTime;

import com.backend.medconsult.entity.auth.User;

public class BookAppointmentDto {
    private String doctorId;
    private String patientId;
    private String consultaionId;
    private String appointmentType;
    private LocalDateTime scheduledAt;
    private int durationMinutes;
    // private AppointmentStatus status;
    private String location;
    private String notes;
    private boolean reminderSent;
    private User cancelledBy;
    private String cancelReason;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getConsultaionId() {
        return consultaionId;
    }

    public void setConsultaionId(String consultaionId) {
        this.consultaionId = consultaionId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    // public AppointmentStatus getStatus() {
    //     return status;
    // }

    // public void setStatus(AppointmentStatus status) {
    //     this.status = status;
    // }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    public User getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(User cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

}

package com.backend.medconsult.dto.appointmentDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.enums.AppointmentStatus;

public class AppointmentDto {

    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private UUID consultaionId;
    private String appointmentType;
    private LocalDateTime scheduledAt;
    private int durationMinutes;
    private AppointmentStatus status;
    private String location;
    private String notes;
    private boolean reminderSent;
    private UUID cancelledBy;
    private String cancelReason;
    public UUID getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
    public UUID getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }
    public UUID getPatientId() {
        return patientId;
    }
    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }
    public UUID getConsultaionId() {
        return consultaionId;
    }
    public void setConsultaionId(UUID consultaionId) {
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
    public AppointmentStatus getStatus() {
        return status;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
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
    public UUID getCancelledBy() {
        return cancelledBy;
    }
    public void setCancelledBy(UUID cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
    public String getCancelReason() {
        return cancelReason;
    }
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public static AppointmentDto fromEntity(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctor().getDoctorId());
        dto.setPatientId(appointment.getPatient().getPatientId());
        dto.setConsultaionId(
                appointment.getConsultation() != null 
                ? appointment.getConsultation().getConsultationId() 
                : null
        );
        dto.setAppointmentType(appointment.getAppointmentType());
        dto.setScheduledAt(appointment.getScheduledAt());
        dto.setDurationMinutes(appointment.getDurationMinutes());
        dto.setStatus(appointment.getStatus());
        dto.setLocation(appointment.getLocation());
        dto.setNotes(appointment.getNotes());
        dto.setReminderSent(appointment.isReminderSent());
        dto.setCancelledBy(
                appointment.getCancelledBy() != null 
                ? appointment.getCancelledBy().getUserId() 
                : null
        );
        dto.setCancelReason(appointment.getCancelReason());
        return dto;
    }

}

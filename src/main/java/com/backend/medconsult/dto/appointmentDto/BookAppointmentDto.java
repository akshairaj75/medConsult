package com.backend.medconsult.dto.appointmentDto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.medconsult.entity.appointment.Appointment;


public class BookAppointmentDto {
    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private String consultaionId;
    private LocalDateTime scheduledAt;
    private String appointmentType; 
    private String notes;



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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public static BookAppointmentDto fromEntity(Appointment appointment) {
        BookAppointmentDto dto = new BookAppointmentDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctor().getDoctorId());
        dto.setPatientId(appointment.getPatient().getPatientId());
        dto.setAppointmentType(appointment.getAppointmentType());
        dto.setScheduledAt(appointment.getScheduledAt());
        dto.setNotes(appointment.getNotes());
        return dto;
    }

}

package com.backend.medconsult.dto.chatDto;

import java.util.UUID;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;
import com.backend.medconsult.dto.consultationDto.ConsultationDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.patientDto.PatientDto;

public class ChatConsultationDto {
    private UUID consultationId;
    private AppointmentDto appointment;
    private ConsultationDto consultation;
    private DoctorDto consultingDoctor;
    private PatientDto patientDetail;
    private VitalsDto vitals;

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public AppointmentDto getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDto appointment) {
        this.appointment = appointment;
    }

    public ConsultationDto getConsultation() {
        return consultation;
    }

    public void setConsultation(ConsultationDto consultation) {
        this.consultation = consultation;
    }

    public DoctorDto getConsultingDoctor() {
        return consultingDoctor;
    }

    public void setConsultingDoctor(DoctorDto consultingDoctor) {
        this.consultingDoctor = consultingDoctor;
    }

    public PatientDto getPatientDetail() {
        return patientDetail;
    }

    public void setPatientDetail(PatientDto patientDetail) {
        this.patientDetail = patientDetail;
    }

    public VitalsDto getVitals() {
        return vitals;
    }

    public void setVitals(VitalsDto vitals) {
        this.vitals = vitals;
    }
}

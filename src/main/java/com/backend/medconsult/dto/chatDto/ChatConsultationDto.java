package com.backend.medconsult.dto.chatDto;


import java.util.List;

import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;

public class ChatConsultationDto {
    private AppointmentDto appointment;
    private VitalsDto vitals;
    private List<PrescriptionDto> prescriptions;


    public AppointmentDto getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDto appointment) {
        this.appointment = appointment;
    }

    public VitalsDto getVitals() {
        return vitals;
    }

    public void setVitals(VitalsDto vitals) {
        this.vitals = vitals;
    }
        public List<PrescriptionDto> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionDto> prescriptions) {
        this.prescriptions = prescriptions;
    }
}

package com.backend.medconsult.dto.chatDto;


import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;

public class ChatConsultationDto {
    private AppointmentDto appointment;
    private VitalsDto vitals;


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
}

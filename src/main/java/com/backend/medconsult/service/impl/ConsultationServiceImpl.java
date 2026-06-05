package com.backend.medconsult.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.HealthDto.PrescriptionDto;
import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.chatDto.ChatConsultationDto;
import com.backend.medconsult.dto.clinicalDataDto.VitalsDto;
import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.entity.clinicalData.Prescription;
import com.backend.medconsult.entity.clinicalData.Vital;
import com.backend.medconsult.repository.AppointmentRepository;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.PrescriptionRepository;
import com.backend.medconsult.repository.VitalRepository;
import com.backend.medconsult.service.ConsultationService;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    ConsultationRepository consultationRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    VitalRepository vitalRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Override
    public ChatConsultationDto getConsultDetails(UUID consultationId) {

        Appointment appointment = appointmentRepository.findByConsultation_ConsultationId(consultationId)
                .orElseThrow(() -> new RuntimeException("No consultation Details Found"));

        AppointmentDto appointmentDto = AppointmentDto.fromEntity(appointment);

        Vital vital = vitalRepository.findTopByPatient_PatientIdOrderByRecordedAtDesc(appointmentDto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Vitals not found"));

        List<Prescription> prescriptions = prescriptionRepository.findByConsultation_ConsultationId(consultationId)
                .orElse(List.of());

        VitalsDto vitalsDto = VitalsDto.fromEntity(vital);

        ChatConsultationDto dto = new ChatConsultationDto();
        dto.setAppointment(appointmentDto);
        dto.setVitals(vitalsDto);
        dto.setPrescriptions(
                prescriptions.stream()
                        .map(PrescriptionDto::fromEntity)
                        .toList());

        return dto;
    }

}

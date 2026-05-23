package com.backend.medconsult.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.appointmentDto.BookAppointmentDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.entity.clinicalData.BookedSlotDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface DoctorService {

    public List<DoctorDto> getDoctors();

    DoctorRegisterDto registerDoctor(DoctorRegisterDto dto, CustomUserPrincipal authUser);

    DoctorDto getDoctorById(UUID id);

    // public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId);
    public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId);

    // public DoctorScheduleDto addDoctorSchedule(CustomUserPrincipal authUser,
    // DoctorScheduleDto scheduleDto);
    public List<DoctorScheduleDto> addDoctorSchedule(CustomUserPrincipal authUser, DoctorScheduleDto scheduleDto);

    public BookAppointmentDto bookAppointment(UUID doctorId, CustomUserPrincipal authUser,
            BookAppointmentDto appointmentDto);

    public List<AppointmentDto> getDoctorTodayAppointments(CustomUserPrincipal authUser);

    public AppointmentDto getDoctorAppointmentById(UUID doctorId, UUID appointmentId);

    public AppointmentDto scheduleAppointment(UUID appointmentId, AppointmentDto appointmentDto,
            CustomUserPrincipal authUser);

    public List<DoctorScheduleDto> getMySchedules(CustomUserPrincipal authUser);

    public AppointmentDto updateAppointmentById(CustomUserPrincipal authUser, UUID appointmentId, AppointmentDto dto);

    public List<PatientDto> myPatients(CustomUserPrincipal authUser);

    public ResponseEntity<String> deleteSchedule(CustomUserPrincipal authUser, UUID scheduleId2);

    public List<PatientDto> labResultPatients(CustomUserPrincipal authUser);

    public List<AppointmentDto> getAllDoctorAppointments(CustomUserPrincipal authUser);

    public List<BookedSlotDto> getBookedSlots(UUID doctorId, LocalDate date, CustomUserPrincipal authUser);

}

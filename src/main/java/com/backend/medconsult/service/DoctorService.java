package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.appointmentDto.BookAppointmentDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface DoctorService {

    public List<DoctorDto> getDoctors();

    DoctorRegisterDto registerDoctor(DoctorRegisterDto dto, CustomUserPrincipal authUser);

    DoctorDto getDoctorById(UUID id);

    public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId);

    public DoctorScheduleDto addDoctorSchedule(CustomUserPrincipal authUser, DoctorScheduleDto scheduleDto);

    public BookAppointmentDto bookAppointment(UUID doctorId, CustomUserPrincipal authUser, BookAppointmentDto appointmentDto);

    public List<BookAppointmentDto> getDoctorAppointments(CustomUserPrincipal authUser);

    public AppointmentDto getDoctorAppointmentById(UUID doctorId, UUID appointmentId);

    public AppointmentDto scheduleAppointment( UUID appointmentId, AppointmentDto appointmentDto);

    public List<DoctorScheduleDto> getMySchedules(CustomUserPrincipal authUser);

}

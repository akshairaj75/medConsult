package com.backend.medconsult.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.appointmentDto.BookAppointmentDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.DoctorSchedule;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.Role;
import com.backend.medconsult.repository.AppointmentRepository;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.DoctorScheduleRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorDto> getDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorDto::fromEntity)
                .toList();
    }

    public DoctorRegisterDto registerDoctor(DoctorRegisterDto dto) {
        Doctor doctor = new Doctor();
        UUID userId = dto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        doctor.setUser(user);
        doctor.setDoctorCode(dto.getDoctorCode());
        doctor.setSpeciality(dto.getSpeciality());
        doctor.setSubSpecialities(dto.getSubSpecialities());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setLicenseAuthority(dto.getLicenseAuthority());
        doctor.setYearsExperience(dto.getYearsExperience());
        doctor.setHospitalAffiliation(dto.getHospitalAffiliation());
        doctor.getUser().setRole(Role.DOCTOR);
        doctor.setLanguagesSpoken(dto.getLanguagesSpoken());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setBio(dto.getBio());
        doctorRepository.save(doctor);
        // return DoctorRegisterDto.fromEntity(savedDoctor);
        return dto;
    }

    @Override
    public DoctorDto getDoctorById(UUID id) {
        DoctorDto doctorDetails = doctorRepository.findById(id)
                .map(DoctorDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return doctorDetails;

    }

    @Override
    public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId) {

        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findByDoctor_DoctorId(doctorId);

        return schedules.stream()
                .map(DoctorScheduleDto::fromEntity)
                .toList();
    }

    @Override
    public DoctorScheduleDto addDoctorSchedule(UUID doctorId, DoctorScheduleDto scheduleDto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setStartTime(scheduleDto.getStartTime());
        schedule.setEndTime(scheduleDto.getEndTime());
        schedule.setScheduleType(scheduleDto.getScheduleType());
        schedule.setEffectiveFrom(scheduleDto.getEffectiveFrom());
        schedule.setEffectiveUntil(scheduleDto.getEffectiveUntil());

        DoctorSchedule savedSchedule = doctorScheduleRepository.save(schedule);
        return DoctorScheduleDto.fromEntity(savedSchedule);
    }

    @Override
    public BookAppointmentDto bookAppointment(UUID doctorId, UUID patientId, BookAppointmentDto appointmentDto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentType(appointmentDto.getAppointmentType());
        appointment.setScheduledAt(appointmentDto.getScheduledAt());
        appointment.setDurationMinutes(appointmentDto.getDurationMinutes());
        appointment.setLocation(appointmentDto.getLocation());
        appointment.setNotes(appointmentDto.getNotes());
        appointment.setReminderSent(appointmentDto.isReminderSent());
        appointment.setCancelledBy(appointmentDto.getCancelledBy());
        appointment.setCancelReason(appointmentDto.getCancelReason());
        appointmentRepository.save(appointment);
        return appointmentDto;
    }

    @Override
    public List<BookAppointmentDto> getDoctorAppointments(UUID doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctor_DoctorId(doctorId);
        return appointments.stream()
                .map(BookAppointmentDto::fromEntity)
                .toList();
    }

}

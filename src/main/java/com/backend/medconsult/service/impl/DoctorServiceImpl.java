package com.backend.medconsult.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
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
import com.backend.medconsult.security.CustomUserPrincipal;
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

        public DoctorRegisterDto registerDoctor(DoctorRegisterDto dto, CustomUserPrincipal authUser) {
                User user = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                if (user.getRole() != Role.DOCTOR) {
                        Doctor doctor = new Doctor();
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

                } else {
                        throw new RuntimeException("User is already registered as a doctor");
                }

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
        public List<DoctorScheduleDto> getMySchedules(CustomUserPrincipal authUser) {
                User userEntity = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                if (userEntity.getRole() == Role.DOCTOR) {
                        UUID doctorId = userEntity.getDoctor().getDoctorId();
                        List<DoctorSchedule> schedules = doctorScheduleRepository
                                        .findByDoctor_DoctorId(doctorId);
                        return schedules.stream()
                                        .map(DoctorScheduleDto::fromEntity)
                                        .toList();
                }
                throw new RuntimeException("Not registered a doctor");
        }

        @Override
        public DoctorScheduleDto addDoctorSchedule(CustomUserPrincipal authUser, DoctorScheduleDto scheduleDto) {

                if (authUser.getUser().getRole() == Role.DOCTOR || authUser.getUser().getRole() == Role.ADMIN) {
                        Doctor doctor = doctorRepository.findById(authUser.getUserId())
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
                throw new RuntimeException("Not authorised to modify");
        }

        @Override
        public BookAppointmentDto bookAppointment(UUID doctorId, CustomUserPrincipal authUser,
                        BookAppointmentDto appointmentDto) {
                Doctor doctor = doctorRepository.findById(doctorId)
                                .orElseThrow(() -> new RuntimeException("Doctor not found"));

                Patient patient = patientRepository.findById(authUser.getUser().getPatient().getPatientId())
                                .orElseThrow(() -> new RuntimeException("Patient not found"));

                Appointment appointment = new Appointment();
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                appointment.setScheduledAt(appointmentDto.getScheduledAt());
                appointment.setAppointmentType(appointmentDto.getAppointmentType());
                appointment.setNotes(appointmentDto.getNotes());
                appointmentRepository.save(appointment);
                return BookAppointmentDto.fromEntity(appointment);
        }

        @Override
        public List<AppointmentDto> getDoctorAppointments(CustomUserPrincipal authUser) {
                User authUserEntity = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                if (authUserEntity.getRole() == Role.DOCTOR) {
                        UUID doctorId = authUserEntity.getDoctor().getDoctorId();
                        List<Appointment> appointments = appointmentRepository.findByDoctor_DoctorId(doctorId);
                        return appointments.stream()
                                        .map(AppointmentDto::fromEntity)
                                        .toList();

                }

                UUID patientId = authUserEntity.getPatient().getPatientId();
                List<Appointment> appointments = appointmentRepository.findByPatient_PatientId(patientId);
                return appointments.stream()
                                .map(AppointmentDto::fromEntity)
                                .toList();
        }

        @Override
        public AppointmentDto getDoctorAppointmentById(UUID doctorId, UUID appointmentId) {
                Appointment appointment = appointmentRepository.findById(appointmentId)
                                .orElseThrow(() -> new RuntimeException("Appointment not found"));
                if (!appointment.getDoctor().getDoctorId().equals(doctorId)) {
                        throw new RuntimeException("Appointment does not belong to the specified doctor");
                }
                return AppointmentDto.fromEntity(appointment);
        }

        @Override
        public AppointmentDto scheduleAppointment(UUID appointmentId, AppointmentDto appointmentDto) {
                Appointment appointment = appointmentRepository.findById(appointmentId)
                                .orElseThrow(() -> new RuntimeException("Appointment not found"));

                User cancellingUser = appointmentDto.getCancelledBy() != null
                                ? userRepository.findById(appointmentDto.getCancelledBy())
                                                .orElseThrow(() -> new RuntimeException("Cancelling user not found"))
                                : null;

                // if (!appointment.getDoctor().getDoctorId().equals(doctorId)) {
                // throw new RuntimeException("Appointment does not belong to the specified
                // doctor");
                // }
                appointment.setScheduledAt(appointmentDto.getScheduledAt());
                appointment.setDurationMinutes(appointmentDto.getDurationMinutes());
                appointment.setReminderSent(appointmentDto.isReminderSent());
                appointment.setCancelledBy(cancellingUser);
                appointment.setCancelReason(appointmentDto.getCancelReason());
                appointmentRepository.save(appointment);
                return AppointmentDto.fromEntity(appointment);
        }

}

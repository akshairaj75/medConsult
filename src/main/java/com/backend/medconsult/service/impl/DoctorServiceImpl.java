package com.backend.medconsult.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.appointmentDto.BookAppointmentDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.entity.people.DoctorSchedule;
import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.AppointmentStatus;
import com.backend.medconsult.enums.Priority;
import com.backend.medconsult.enums.Role;
import com.backend.medconsult.repository.AppointmentRepository;
import com.backend.medconsult.repository.ConsultationRepository;
import com.backend.medconsult.repository.DoctorRepository;
import com.backend.medconsult.repository.DoctorScheduleRepository;
import com.backend.medconsult.repository.PatientRepository;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.DoctorService;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    ConsultationRepository consultationRepository;

    @Override
    public List<DoctorDto> getDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public DoctorRegisterDto registerDoctor(
            DoctorRegisterDto dto,
            CustomUserPrincipal authUser) {
        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getDoctor() != null) {
            throw new RuntimeException("User is already registered as a doctor");
        }

        user.setRole(Role.DOCTOR);
        userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setDoctorCode(dto.getDoctorCode());
        doctor.setSpeciality(dto.getSpeciality());
        doctor.setSubSpecialities(dto.getSubSpecialities());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setLicenseAuthority(dto.getLicenseAuthority());
        doctor.setYearsExperience(dto.getYearsExperience());
        doctor.setHospitalAffiliation(dto.getHospitalAffiliation());
        doctor.setLanguagesSpoken(dto.getLanguagesSpoken());
        doctor.setConsultationFee(dto.getConsultationFee());
        doctor.setBio(dto.getBio());
        Doctor savedDoctor = doctorRepository.save(doctor);
        // return DoctorRegisterDto.fromEntity(savedDoctor);
        return DoctorRegisterDto.fromEntity(savedDoctor);

    }

    @Override
    public DoctorDto getDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .map(DoctorDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

    }

    @Override
    public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId) {

        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc(doctorId);

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
                    .findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc(doctorId);
            return schedules.stream()
                    .map(DoctorScheduleDto::fromEntity)
                    .toList();
        }
        throw new RuntimeException("Not registered a doctor");
    }

    @Override
    public List<DoctorScheduleDto> addDoctorSchedule(
            CustomUserPrincipal authUser,
            DoctorScheduleDto scheduleDto) {

        if (authUser.getUser().getRole() != Role.DOCTOR &&
                authUser.getUser().getRole() != Role.ADMIN) {

            throw new RuntimeException("Not authorised");
        }

        Doctor doctor = doctorRepository.findById(
                        authUser.getUser().getDoctor().getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime overallStart = scheduleDto.getStartTime();
        LocalTime overallEnd = scheduleDto.getEndTime();

        int duration = scheduleDto.getSlotDuration();
        int gap = scheduleDto.getGapMinutes() == null
                ? 0
                : scheduleDto.getGapMinutes();

        List<DoctorSchedule> savedSchedules = new ArrayList<>();

        LocalTime currentStart = overallStart;

        while (true) {

            LocalTime currentEnd = currentStart.plusMinutes(duration);

            if (currentEnd.isAfter(overallEnd)) {
                break;
            }

            DoctorSchedule schedule = new DoctorSchedule();

            schedule.setDoctor(doctor);
            schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
            schedule.setStartTime(currentStart);
            schedule.setEndTime(currentEnd);
            schedule.setScheduleType(scheduleDto.getScheduleType());
            schedule.setEffectiveFrom(scheduleDto.getEffectiveFrom());
            schedule.setEffectiveUntil(scheduleDto.getEffectiveUntil());

            savedSchedules.add(schedule);

            currentStart = currentEnd.plusMinutes(gap);
        }

        List<DoctorSchedule> result = doctorScheduleRepository.saveAll(savedSchedules);

        return result.stream()
                .map(DoctorScheduleDto::fromEntity)
                .toList();
    }

    @Override
    public BookAppointmentDto bookAppointment(UUID doctorId, CustomUserPrincipal authUser,
                                              BookAppointmentDto appointmentDto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(authUser.getUser().getPatient().getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        DoctorSchedule schedule = doctorScheduleRepository.findById(appointmentDto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.isBooked()) {

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setSchedule(schedule);
            appointment.setScheduledAt(appointmentDto.getScheduledAt());
            appointment.setAppointmentType(appointmentDto.getAppointmentType());
            appointment.setNotes(appointmentDto.getNotes());

            appointmentRepository.save(appointment);
            return BookAppointmentDto.fromEntity(appointment);

        }
        throw new RuntimeException("This slot have been already booked");
    }

    @Override
    public List<AppointmentDto> getDoctorTodayAppointments(CustomUserPrincipal authUser) {

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        User authUserEntity = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (authUserEntity.getRole() == Role.DOCTOR) {
            UUID doctorId = authUserEntity.getDoctor().getDoctorId();
            List<Appointment> appointments = appointmentRepository
                    .findTodayDoctorAppointments(doctorId,
                            start, end,
                            List.of(
                                    AppointmentStatus.CONFIRMED,
                                    AppointmentStatus.COMPLETED,
                                    AppointmentStatus.NO_SHOW));
            return appointments.stream()
                    .map(AppointmentDto::fromEntity)
                    .toList();
        }

        UUID patientId = authUserEntity.getPatient().getPatientId();
        List<Appointment> appointments = appointmentRepository
                .findByPatient_PatientIdOrderByScheduledAtDesc(patientId);
        return appointments.stream()
                .map(AppointmentDto::fromEntity)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAllDoctorAppointments(CustomUserPrincipal authUser) {

        User authUserEntity = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (authUserEntity.getRole() == Role.DOCTOR) {
            UUID doctorId = authUserEntity.getDoctor().getDoctorId();
            List<Appointment> appointments = appointmentRepository
                    .findActiveDoctorAppointments(doctorId, List.of());
            return appointments.stream()
                    .map(AppointmentDto::fromEntity)
                    .toList();
        }

        UUID patientId = authUserEntity.getPatient().getPatientId();
        List<Appointment> appointments = appointmentRepository
                .findByPatient_PatientIdOrderByScheduledAtDesc(patientId);
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
    public AppointmentDto scheduleAppointment(
            UUID appointmentId,
            AppointmentDto appointmentDto,
            CustomUserPrincipal authUser) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // DoctorSchedule schedule =
        // doctorScheduleRepository.findById(appointmentDto.scheduleId())

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
        // appointment.setSchedule(null);
        appointment.setCancelReason(appointmentDto.getCancelReason());
        appointmentRepository.save(appointment);
        return AppointmentDto.fromEntity(appointment);
    }

    @Override
    public AppointmentDto updateAppointmentById(CustomUserPrincipal authUser, UUID appointmentId,
                                                AppointmentDto dto) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        User userEntity = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        AppointmentStatus status = dto.getStatus();
        Patient patient = appointment.getPatient();

        DoctorSchedule schedule = appointment.getSchedule();

        if (status == null) {
            return AppointmentDto.fromEntity(appointment);
        }

        if (userEntity.getRole() == Role.DOCTOR) {

            Doctor doctor = doctorRepository.findById(
                            userEntity.getDoctor().getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            switch (status) {

                case CONFIRMED:
                    appointment.setStatus(AppointmentStatus.CONFIRMED);
                    appointment.setCancelReason(null);
                    appointment.setCancelledBy(null);
                    patient.setAssignedDoctor(doctor);
                    schedule.setBooked(true);

                    // Avoid duplicate consultation creation
                    if (appointment.getConsultation() == null) {

                        Consultation consultation = new Consultation();

                        consultation.setPatient(patient);
                        consultation.setDoctor(doctor);

                        consultation.setPriority(
                                dto.getPriority() != null
                                        ? dto.getPriority()
                                        : Priority.NORMAL);

                        String chiefComplaint = appointment.getNotes() != null
                                && !appointment.getNotes().isBlank()
                                ? appointment.getNotes()
                                : "Complaint not specified";

                        consultation.setChiefComplaint(chiefComplaint);
                        consultationRepository.save(consultation);
                        appointment.setConsultation(consultation);
                    }

                    break;

                case CANCELLED:

                    appointment.setStatus(AppointmentStatus.CANCELLED);
                    appointment.setCancelledBy(userEntity);

                    if (dto.getCancelReason() != null
                            && !dto.getCancelReason().isBlank()) {

                        appointment.setCancelReason(dto.getCancelReason());
                    }

                    break;

                case COMPLETED:

                    appointment.setStatus(AppointmentStatus.COMPLETED);
                    break;

                case NO_SHOW:

                    appointment.setStatus(AppointmentStatus.NO_SHOW);
                    break;

                default:
                    throw new RuntimeException(
                            "Doctors cannot set this status");
            }

        } else if (userEntity.getRole() == Role.PATIENT) {
            if (status != AppointmentStatus.CANCELLED) {
                throw new RuntimeException(
                        "Patients can only cancel appointments");
            }
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointment.setCancelledBy(userEntity);

            if (dto.getCancelReason() != null
                    && !dto.getCancelReason().isBlank()) {

                appointment.setCancelReason(dto.getCancelReason());
            }
        }
        // }
        appointmentRepository.save(appointment);

        return AppointmentDto.fromEntity(appointment);
    }

    
    @Override
    public List<PatientDto> myPatients(CustomUserPrincipal authUser) {
        UUID doctorId = authUser.getUser().getDoctor().getDoctorId();
        List<Patient> patients = patientRepository.findByAssignedDoctor_DoctorId(doctorId);
        return patients.stream()
                .map(PatientDto::fromEntity)
                .toList();
    }

    @Override
    public List<PatientDto> labResultPatients(CustomUserPrincipal authUser) {
        UUID doctorId = authUser.getUser().getDoctor().getDoctorId();
        List<Patient> patients = patientRepository.findPatientsAccessibleToDoctor(doctorId);
        return patients.stream()
                .map(PatientDto::fromEntity)
                .toList();
    }

    @Override
    public ResponseEntity<String> deleteSchedule(CustomUserPrincipal authUser, UUID scheduleId) {

        if (authUser.getUser().getRole() != Role.DOCTOR) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Only doctors can delete schedules");
        }

        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.getDoctor().getDoctorId()
                .equals(authUser.getUser().getDoctor().getDoctorId())) {

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Unauthorized");
        }
        doctorScheduleRepository.delete(schedule);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Schedule deleted successfully");

    }
}

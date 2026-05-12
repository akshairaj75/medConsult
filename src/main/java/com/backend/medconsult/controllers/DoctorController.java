package com.backend.medconsult.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.appointmentDto.AppointmentDto;
import com.backend.medconsult.dto.appointmentDto.BookAppointmentDto;
import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.dto.patientDto.PatientDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register") // add userId as path variable after implementing auth
    public ResponseEntity<DoctorRegisterDto> registerDoctor(@RequestBody DoctorRegisterDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        DoctorRegisterDto registered = doctorService.registerDoctor(dto, authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorDto>> getDoctors() {
        List<DoctorDto> doctors = doctorService.getDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable UUID id) {
        DoctorDto doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    // SCHEDULE ENDPOINTS
    // -------------------
    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDto>> getDoctorSchedules(@PathVariable UUID doctorId) {
        List<DoctorScheduleDto> schedule = doctorService.getDoctorSchedules(doctorId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/my-schedules")
    public ResponseEntity<List<DoctorScheduleDto>> getMySchedules(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<DoctorScheduleDto> schedule = doctorService.getMySchedules(authUser);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/schedule/delete/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @PathVariable UUID scheduleId){
        return doctorService.deleteSchedule(authUser, scheduleId);
        // return ResponseEntity.ok(response);
    }

    @GetMapping("/my-patients")
    public ResponseEntity<List<PatientDto>> myPatients(@AuthenticationPrincipal CustomUserPrincipal authUser){
        List<PatientDto> dto = doctorService.myPatients(authUser);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/lab-patient")
    public ResponseEntity<List<PatientDto>> labResultPatients(@AuthenticationPrincipal CustomUserPrincipal authUser){
        List<PatientDto> dto = doctorService.labResultPatients(authUser);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/add-schedules")
    public ResponseEntity<DoctorScheduleDto> addDoctorSchedule(@AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody DoctorScheduleDto scheduleDto) {
        DoctorScheduleDto createdSchedule = doctorService.addDoctorSchedule(authUser, scheduleDto);
        if (createdSchedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @PostMapping("/{doctorId}/book_appointment")
    public ResponseEntity<BookAppointmentDto> bookAppointment(
            @PathVariable UUID doctorId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody BookAppointmentDto appointmentDto) {
        BookAppointmentDto bookedAppointment = doctorService.bookAppointment(doctorId, authUser, appointmentDto);
        if (bookedAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookedAppointment);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDto>> getDoctorAppointments(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<AppointmentDto> appointments = doctorService.getDoctorAppointments(authUser);
        if (appointments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{doctorId}/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDto> getDoctorAppointmentById(
            @PathVariable UUID doctorId,
            @PathVariable UUID appointmentId) {
        AppointmentDto appointment = doctorService.getDoctorAppointmentById(doctorId, appointmentId);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDto> updateAppointmentById(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable UUID appointmentId,
            @RequestBody AppointmentDto dto
        ) {
        AppointmentDto appointment = doctorService.updateAppointmentById(authUser, appointmentId, dto);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/appointments/{appointmentId}/schedule")
    public ResponseEntity<AppointmentDto> scheduleAppointment(
            @PathVariable UUID appointmentId,
            @RequestBody AppointmentDto appointmentDto,
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        AppointmentDto updatedAppointment = doctorService.scheduleAppointment(appointmentId, appointmentDto,authUser);
        if (updatedAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAppointment);
    }

}
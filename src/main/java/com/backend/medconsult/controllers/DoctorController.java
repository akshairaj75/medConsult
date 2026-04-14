package com.backend.medconsult.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;
import com.backend.medconsult.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<DoctorRegisterDto> registerDoctor(@RequestBody DoctorRegisterDto dto) {
        DoctorRegisterDto registered = doctorService.registerDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<DoctorDto>> getDoctors() {
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

    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDto>> getDoctorSchedules(@PathVariable UUID doctorId) {
        List<DoctorScheduleDto> schedule = doctorService.getDoctorSchedules(doctorId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }



    // @GetMapping("/all")
    // public ResponseEntity<List<DoctorRegisterDto>> getAllDoctors() {
    //     List<DoctorRegisterDto> doctors = doctorService.getDoctors()
    //             .stream()
    //             .map(DoctorRegisterDto::fromEntity)
    //             .toList();
    //     return ResponseEntity.ok(doctors);
    // }

}

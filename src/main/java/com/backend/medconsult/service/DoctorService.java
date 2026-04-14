package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.doctorDto.DoctorDto;
import com.backend.medconsult.dto.doctorDto.DoctorRegisterDto;
import com.backend.medconsult.dto.doctorDto.DoctorScheduleDto;

public interface DoctorService {

    public List<DoctorDto> getDoctors();

    DoctorRegisterDto registerDoctor(DoctorRegisterDto dto);

    DoctorDto getDoctorById(UUID id);

    public List<DoctorScheduleDto> getDoctorSchedules(UUID doctorId);
}

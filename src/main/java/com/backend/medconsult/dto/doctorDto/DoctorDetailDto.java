package com.backend.medconsult.dto.doctorDto;

import java.util.UUID;

import com.backend.medconsult.entity.people.Doctor;

public class DoctorDetailDto {
    private String name;
    private String phone;
    private String specialization;
    private UUID doctorId;
    private String hospitalAffiliation;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalAffiliation() {
        return hospitalAffiliation;
    }

    public void setHospitalAffiliation(String hospitalAffiliation) {
        this.hospitalAffiliation = hospitalAffiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static DoctorDetailDto fromEntity(Doctor doctor) {
        DoctorDetailDto dto = new DoctorDetailDto();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setEmail(doctor.getUser().getEmail());
        dto.setHospitalAffiliation(doctor.getHospitalAffiliation());
        dto.setName(doctor.getUser().getFullName());
        dto.setPhone(doctor.getUser().getPhone());
        dto.setSpecialization(doctor.getSpeciality());
        return dto;
    }

}

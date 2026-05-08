package com.backend.medconsult.dto.doctorDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.backend.medconsult.entity.people.Doctor;

public class DoctorRegisterDto {
    public UUID userId;
    public String doctorCode;
    public String speciality;
    private List<String> subSpecialities;
    public String licenseNumber;
    public String licenseAuthority;
    public int yearsExperience;
    public String hospitalAffiliation;
    private List<String> languagesSpoken;
    public BigDecimal consultationFee;
    public String bio;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<String> getSubSpecialities() {
        return subSpecialities;
    }

    public void setSubSpecialities(List<String> subSpecialities) {
        this.subSpecialities = subSpecialities;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseAuthority() {
        return licenseAuthority;
    }

    public void setLicenseAuthority(String licenseAuthority) {
        this.licenseAuthority = licenseAuthority;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getHospitalAffiliation() {
        return hospitalAffiliation;
    }

    public void setHospitalAffiliation(String hospitalAffiliation) {
        this.hospitalAffiliation = hospitalAffiliation;
    }

    public List<String> getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(List<String> languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public static DoctorRegisterDto fromEntity(Doctor savedDoctor) {

        DoctorRegisterDto dto = new DoctorRegisterDto();
        dto.setUserId(savedDoctor.getDoctorId());
        dto.setBio(savedDoctor.getBio());
        dto.setDoctorCode(savedDoctor.getDoctorCode());
        dto.setSpeciality(savedDoctor.getSpeciality());
        dto.setLicenseNumber(savedDoctor.getLicenseNumber());
        dto.setLicenseAuthority(savedDoctor.getLicenseAuthority());
        dto.setLanguagesSpoken(savedDoctor.getLanguagesSpoken());
        dto.setYearsExperience(savedDoctor.getYearsExperience());
        dto.setHospitalAffiliation(savedDoctor.getHospitalAffiliation());;
        dto.setConsultationFee(savedDoctor.getConsultationFee());
        return dto;
        // throw new UnsupportedOperationException("Unimplemented method 'fromEntity'");
    }
}

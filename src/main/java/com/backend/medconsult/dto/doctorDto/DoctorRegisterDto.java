package com.backend.medconsult.dto.doctorDto;

import java.math.BigDecimal;
import java.util.UUID;

public class DoctorRegisterDto {
    public UUID userId;
    public String doctorCode;
    public String speciality;
    public String[] subSpecialities;
    public String licenseNumber;
    public String licenseAuthority;
    public int yearsExperience;
    public String hospitalAffiliation;
    public String[] languagesSpoken;
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

    public String[] getSubSpecialities() {
        return subSpecialities;
    }

    public void setSubSpecialities(String[] subSpecialities) {
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

    public String[] getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String[] languagesSpoken) {
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

    // public static DoctorRegisterDto fromEntity(Doctor savedDoctor) {

    //     DoctorRegisterDto dto = new DoctorRegisterDto(); 
    //     dto.userId = savedDoctor.getDoctorId();
    //     dto.doctorCode = savedDoctor.getDoctorCode();
    //     dto.speciality = savedDoctor.getSpeciality();
    //     dto.licenseNumber = savedDoctor.getLicenseNumber();
    //     dto.licenseAuthority = savedDoctor.getLicenseAuthority();   
    //     dto.yearsExperience = savedDoctor.getYearsExperience();
    //     dto.hospitalAffiliation = savedDoctor.getHospitalAffiliation();
    //     dto.consultationFee = savedDoctor.getConsultationFee();
    //     dto.bio = savedDoctor.getBio();
    // throw new UnsupportedOperationException("Unimplemented method 'fromEntity'");
    // }
}

package com.backend.medconsult.dto.doctorDto;

import java.math.BigDecimal;
import com.backend.medconsult.entity.people.Doctor;
import com.backend.medconsult.enums.AvailabilityStatus;

public class DoctorDto {

    private String name;
    private String id;
    private String specialization;
    private String profilePhotoUrl;
    private int yearsExperience;
    private BigDecimal avgRating;
    private String hospitalAffiliation;
    private String[] languagesSpoken;
    private AvailabilityStatus availabilityStatus;
    private String email;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
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

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static DoctorDto fromEntity(Doctor doctor) {
        if (doctor == null)
            return null;

        DoctorDto dto = new DoctorDto();

        if (doctor.getUser() != null) {
            dto.setId(doctor.getDoctorId().toString());
            dto.setName(doctor.getUser().getFullName());
            dto.setProfilePhotoUrl(doctor.getUser().getProfilePhotoUrl());
            dto.email = doctor.getUser().getEmail();
            dto.phone = doctor.getUser().getPhone();
        }

        dto.setSpecialization(doctor.getSpeciality());
        dto.yearsExperience = doctor.getYearsExperience();
        dto.avgRating = doctor.getAvgRating();
        dto.hospitalAffiliation = doctor.getHospitalAffiliation();
        dto.availabilityStatus = doctor.getAvailabilityStatus();

        dto.languagesSpoken = doctor.getLanguagesSpoken() == null
                ? new String[0]
                : doctor.getLanguagesSpoken().toArray(new String[0]);

        return dto;
    }

}

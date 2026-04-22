package com.backend.medconsult.dto.patientDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.backend.medconsult.entity.people.Patient;
import com.backend.medconsult.enums.BloodType;
import com.backend.medconsult.enums.Gender;

public class PatientRegisterDto {
    private UUID userId;  
    private String patientCode;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;  
    private String nationalId;  
    private String insuranceProvider;  
    private String insurancePolicyNo;  
    private LocalDate insuranceExpiry;  
    private String emergencyContactName;  
    private String emergencyContactPhone;  
    private List<String> allergies;   
    private List<String> chronicConditions;   
    private UUID assignedDoctor;  
    private String notes;  

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getInsurancePolicyNo() {
        return insurancePolicyNo;
    }

    public void setInsurancePolicyNo(String insurancePolicyNo) {
        this.insurancePolicyNo = insurancePolicyNo;
    }

    public LocalDate getinsuranceExpiry() {
        return insuranceExpiry;
    }

    public void setinsuranceExpiry(LocalDate insuranceExpiry) {
        this.insuranceExpiry = insuranceExpiry;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(List<String> chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public UUID getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(UUID assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static PatientRegisterDto fromEntity(Patient patient) {
        PatientRegisterDto dto = new PatientRegisterDto();
        dto.setUserId(patient.getUser().getUserId());
        dto.setPatientCode(patient.getPatientCode());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setBloodType(patient.getBloodType());
        dto.setNationalId(patient.getNationalId());
        dto.setInsuranceProvider(patient.getInsuranceProvider());
        dto.setInsurancePolicyNo(patient.getInsurancePolicyNo());
        dto.setinsuranceExpiry(patient.getInsuranceExpiry());
        dto.setEmergencyContactName(patient.getEmergencyContactName());
        dto.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        dto.setAllergies(patient.getAllergies());
        dto.setChronicConditions(patient.getChronicConditions());
        if(patient.getAssignedDoctor() != null){
            dto.setAssignedDoctor(patient.getAssignedDoctor().getDoctorId());
        }dto.setAssignedDoctor(null);
        
        dto.setNotes(patient.getNotes());
        return dto;
    }
}

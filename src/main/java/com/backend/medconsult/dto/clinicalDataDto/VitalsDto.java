package com.backend.medconsult.dto.clinicalDataDto;

import java.math.BigDecimal;
import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.Vital;

public class VitalsDto {

    private UUID vitalId;
    private UUID patientId;
    private UUID recordedByUserId;
    private Integer heartRateBpm;
    private Integer bpSystolic;
    private Integer bpDiastolic;
    private BigDecimal temperatureC;
    private BigDecimal spo2Percent;
    private BigDecimal weightKg;
    private BigDecimal heightCm;
    private BigDecimal bmi;
    private BigDecimal bloodGlucoseMmol;


    public UUID getVitalId() {
        return vitalId;
    }

    public void setVitalId(UUID vitalId) {
        this.vitalId = vitalId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getRecordedByUserId() {
        return recordedByUserId;
    }

    public void setRecordedByUserId(UUID recordedByUserId) {
        this.recordedByUserId = recordedByUserId;
    }

    public Integer getHeartRateBpm() {
        return heartRateBpm;
    }

    public void setHeartRateBpm(Integer heartRateBpm) {
        this.heartRateBpm = heartRateBpm;
    }

    public Integer getBpSystolic() {
        return bpSystolic;
    }

    public void setBpSystolic(Integer bpSystolic) {
        this.bpSystolic = bpSystolic;
    }

    public Integer getBpDiastolic() {
        return bpDiastolic;
    }

    public void setBpDiastolic(Integer bpDiastolic) {
        this.bpDiastolic = bpDiastolic;
    }

    public BigDecimal getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(BigDecimal temperatureC) {
        this.temperatureC = temperatureC;
    }

    public BigDecimal getSpo2Percent() {
        return spo2Percent;
    }

    public void setSpo2Percent(BigDecimal spo2Percent) {
        this.spo2Percent = spo2Percent;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public BigDecimal getBloodGlucoseMmol() {
        return bloodGlucoseMmol;
    }

    public void setBloodGlucoseMmol(BigDecimal bloodGlucoseMmol) {
        this.bloodGlucoseMmol = bloodGlucoseMmol;
    }

    public static VitalsDto fromEntity(Vital vital) {
        VitalsDto dto = new VitalsDto();
        dto.setVitalId(vital.getVitalId());
        dto.setPatientId(vital.getPatient().getPatientId());
        dto.setRecordedByUserId(vital.getRecordedBy().getUserId());
        dto.setHeartRateBpm(vital.getHeartRateBpm());
        dto.setBpSystolic(vital.getBpSystolic());
        dto.setBpDiastolic(vital.getBpDiastolic());
        dto.setTemperatureC(vital.getTemperatureC());
        dto.setSpo2Percent(vital.getSpo2Percent());
        dto.setWeightKg(vital.getWeightKg());
        dto.setHeightCm(vital.getHeightCm());
        dto.setBmi(vital.getBmi());
        dto.setBloodGlucoseMmol(vital.getBloodGlucoseMmol());
        return dto;
    }

}

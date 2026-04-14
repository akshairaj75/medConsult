package com.backend.medconsult.dto.doctorDto;

import java.time.LocalDate;

import com.backend.medconsult.entity.people.DoctorSchedule;
import com.backend.medconsult.enums.ScheduleType;

public class DoctorScheduleDto {
    private String doctorId;
    private String doctorName;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private ScheduleType scheduleType;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public static DoctorScheduleDto fromEntity(DoctorSchedule schedule) {
        DoctorScheduleDto dto = new DoctorScheduleDto();
        dto.setDoctorId(schedule.getDoctor().getDoctorId().toString());
        dto.setDoctorName(schedule.getDoctor().getUser().getFullName());
        dto.setDayOfWeek(schedule.getDayOfWeek().name());
        dto.setStartTime(schedule.getStartTime().toString());
        dto.setEndTime(schedule.getEndTime().toString());
        dto.setScheduleType(schedule.getScheduleType());
        dto.setEffectiveFrom(schedule.getEffectiveFrom());
        dto.setEffectiveTo(schedule.getEffectiveUntil());
        return dto;
    }

}

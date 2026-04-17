package com.backend.medconsult.dto.doctorDto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.backend.medconsult.entity.people.DoctorSchedule;
import com.backend.medconsult.enums.ScheduleType;
import com.backend.medconsult.enums.Weekday;

public class DoctorScheduleDto {
    private String doctorId;
    private String doctorName;
    private Weekday dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private ScheduleType scheduleType;
    private LocalDate effectiveFrom;
    private LocalDate effectiveUntil;

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

    public Weekday getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Weekday dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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

    public LocalDate getEffectiveUntil() {
        return effectiveUntil;
    }

    public void setEffectiveUntil(LocalDate effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }

    public static DoctorScheduleDto fromEntity(DoctorSchedule schedule) {
        DoctorScheduleDto dto = new DoctorScheduleDto();
        dto.setDoctorId(schedule.getDoctor().getDoctorId().toString());
        dto.setDoctorName(schedule.getDoctor().getUser().getFullName());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setScheduleType(schedule.getScheduleType());
        dto.setEffectiveFrom(schedule.getEffectiveFrom());
        dto.setEffectiveUntil(schedule.getEffectiveUntil());
        return dto;
    }

}

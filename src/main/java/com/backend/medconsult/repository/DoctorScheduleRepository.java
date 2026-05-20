package com.backend.medconsult.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.medconsult.entity.people.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, UUID> {

    // List<DoctorSchedule> findByDoctor_DoctorIdOrderByDayOfWeekAsc(UUID doctorId);
    List<DoctorSchedule> findByDoctor_DoctorIdOrderByDayOfWeekAscStartTimeAsc(UUID doctorId);
}
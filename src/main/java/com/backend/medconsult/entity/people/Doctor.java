package com.backend.medconsult.entity.people;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.appointment.Appointment;
import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.entity.caseDiscussion.CaseRoom;
import com.backend.medconsult.entity.caseDiscussion.CaseRoomMember;
import com.backend.medconsult.entity.clinicalData.LabResult;
import com.backend.medconsult.entity.clinicalData.Prescription;
import com.backend.medconsult.entity.consultations.Consultation;
import com.backend.medconsult.enums.AvailabilityStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "doctors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "doctor_code"),
        @UniqueConstraint(columnNames = "license_number")
}, indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_doctor_code", columnList = "doctor_code")
})
public class Doctor {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "doctor_id", nullable = false, updatable = false, length = 36)
    private UUID doctorId;

    // FK → users(user_id)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "doctor_code", nullable = false, length = 20, unique = true)
    private String doctorCode;

    @Column(nullable = false, length = 100)
    private String speciality;

    // JSON array
    @Column(name = "sub_specialities", columnDefinition = "JSON")
    private List<String> subSpecialities;

    @Column(name = "license_number", nullable = false, length = 60, unique = true)
    private String licenseNumber;

    @Column(name = "license_authority", nullable = false, length = 100)
    private String licenseAuthority;

    @Column(name = "years_experience", nullable = false)
    private int yearsExperience = 0;

    @Column(name = "hospital_affiliation", length = 150)
    private String hospitalAffiliation;

    // JSON array
    @Column(name = "languages_spoken", columnDefinition = "JSON")
    private List<String> languagesSpoken;

    @Column(name = "consultation_fee", precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    @Column(name = "avg_rating", precision = 3, scale = 2)
    private BigDecimal avgRating;

    @Column(name = "total_reviews", nullable = false)
    private int totalReviews = 0;

    @Column(name = "total_consultations", nullable = false)
    private int totalConsultations = 0;

    @Column(name = "is_senior", nullable = false)
    private boolean isSenior = false;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor")
    private List<Review> reviews;

    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations;

    @ManyToMany(mappedBy = "doctors")
    private List<CaseRoom> caseRooms;

    @OneToMany(mappedBy = "doctor")
    private List<CaseRoomMember> caseMemberships;

    @OneToMany(mappedBy = "orderedBy")
    private List<LabResult> orderedLabs;

    @OneToMany(mappedBy = "reviewedBy")
    private List<LabResult> reviewedLabs;

    @OneToMany(mappedBy = "doctor")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorSchedule> schedules;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(int totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public boolean isSenior() {
        return isSenior;
    }

    public void setSenior(boolean isSenior) {
        this.isSenior = isSenior;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DoctorSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<DoctorSchedule> schedules) {
        this.schedules = schedules;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<CaseRoom> getCaseRooms() {
        return caseRooms;
    }

    public void setCaseRooms(List<CaseRoom> caseRooms) {
        this.caseRooms = caseRooms;
    }

    public List<CaseRoomMember> getCaseMemberships() {
        return caseMemberships;
    }

    public void setCaseMemberships(List<CaseRoomMember> caseMemberships) {
        this.caseMemberships = caseMemberships;
    }

    public List<LabResult> getOrderedLabs() {
        return orderedLabs;
    }

    public void setOrderedLabs(List<LabResult> orderedLabs) {
        this.orderedLabs = orderedLabs;
    }

    public List<LabResult> getReviewedLabs() {
        return reviewedLabs;
    }

    public void setReviewedLabs(List<LabResult> reviewedLabs) {
        this.reviewedLabs = reviewedLabs;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}

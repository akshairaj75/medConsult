package com.backend.medconsult.entity.privacy;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.auth.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "privacy_settings", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class PrivacySettings {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "privacy_id", nullable = false, updatable = false, length=36)
    private UUID privacyId;

    // FK → users(user_id) UNIQUE (1:1)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "profile_visible_to_doctors", nullable = false)
    private boolean profileVisibleToDoctors = true;

    @Column(name = "profile_visible_in_cases", nullable = false)
    private boolean profileVisibleInCases = true;

    @Column(name = "allow_anonymous_opinion", nullable = false)
    private boolean allowAnonymousOpinion = false;

    @Column(name = "share_vitals_with_doctor", nullable = false)
    private boolean shareVitalsWithDoctor = true;

    @Column(name = "share_labs_with_specialists", nullable = false)
    private boolean shareLabsWithSpecialists = true;

    @Column(name = "allow_research_use", nullable = false)
    private boolean allowResearchUse = false;

    @Column(name = "share_with_family", nullable = false)
    private boolean shareWithFamily = false;

    // Self FK → users(user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_member_user_id")
    private User familyMember;

    @Column(name = "biometric_login_enabled", nullable = false)
    private boolean biometricLoginEnabled = false;

    @Column(name = "session_timeout_minutes", nullable = false)
    private int sessionTimeoutMinutes = 30;

    @Column(name = "notify_messages", nullable = false)
    private boolean notifyMessages = true;

    @Column(name = "notify_lab_results", nullable = false)
    private boolean notifyLabResults = true;

    @Column(name = "notify_appointments", nullable = false)
    private boolean notifyAppointments = true;

    @Column(name = "notify_prescriptions", nullable = false)
    private boolean notifyPrescriptions = true;

    @Column(name = "notify_case_activity", nullable = false)
    private boolean notifyCaseActivity = false;

    @Column(name = "notify_email_digest", nullable = false)
    private boolean notifyEmailDigest = false;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public UUID getPrivacyId() {
        return privacyId;
    }

    public void setPrivacyId(UUID privacyId) {
        this.privacyId = privacyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isProfileVisibleToDoctors() {
        return profileVisibleToDoctors;
    }

    public void setProfileVisibleToDoctors(boolean profileVisibleToDoctors) {
        this.profileVisibleToDoctors = profileVisibleToDoctors;
    }

    public boolean isProfileVisibleInCases() {
        return profileVisibleInCases;
    }

    public void setProfileVisibleInCases(boolean profileVisibleInCases) {
        this.profileVisibleInCases = profileVisibleInCases;
    }

    public boolean isAllowAnonymousOpinion() {
        return allowAnonymousOpinion;
    }

    public void setAllowAnonymousOpinion(boolean allowAnonymousOpinion) {
        this.allowAnonymousOpinion = allowAnonymousOpinion;
    }

    public boolean isShareVitalsWithDoctor() {
        return shareVitalsWithDoctor;
    }

    public void setShareVitalsWithDoctor(boolean shareVitalsWithDoctor) {
        this.shareVitalsWithDoctor = shareVitalsWithDoctor;
    }

    public boolean isShareLabsWithSpecialists() {
        return shareLabsWithSpecialists;
    }

    public void setShareLabsWithSpecialists(boolean shareLabsWithSpecialists) {
        this.shareLabsWithSpecialists = shareLabsWithSpecialists;
    }

    public boolean isAllowResearchUse() {
        return allowResearchUse;
    }

    public void setAllowResearchUse(boolean allowResearchUse) {
        this.allowResearchUse = allowResearchUse;
    }

    public boolean isShareWithFamily() {
        return shareWithFamily;
    }

    public void setShareWithFamily(boolean shareWithFamily) {
        this.shareWithFamily = shareWithFamily;
    }

    public User getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(User familyMember) {
        this.familyMember = familyMember;
    }

    public boolean isBiometricLoginEnabled() {
        return biometricLoginEnabled;
    }

    public void setBiometricLoginEnabled(boolean biometricLoginEnabled) {
        this.biometricLoginEnabled = biometricLoginEnabled;
    }

    public int getSessionTimeoutMinutes() {
        return sessionTimeoutMinutes;
    }

    public void setSessionTimeoutMinutes(int sessionTimeoutMinutes) {
        this.sessionTimeoutMinutes = sessionTimeoutMinutes;
    }

    public boolean isNotifyMessages() {
        return notifyMessages;
    }

    public void setNotifyMessages(boolean notifyMessages) {
        this.notifyMessages = notifyMessages;
    }

    public boolean isNotifyLabResults() {
        return notifyLabResults;
    }

    public void setNotifyLabResults(boolean notifyLabResults) {
        this.notifyLabResults = notifyLabResults;
    }

    public boolean isNotifyAppointments() {
        return notifyAppointments;
    }

    public void setNotifyAppointments(boolean notifyAppointments) {
        this.notifyAppointments = notifyAppointments;
    }

    public boolean isNotifyPrescriptions() {
        return notifyPrescriptions;
    }

    public void setNotifyPrescriptions(boolean notifyPrescriptions) {
        this.notifyPrescriptions = notifyPrescriptions;
    }

    public boolean isNotifyCaseActivity() {
        return notifyCaseActivity;
    }

    public void setNotifyCaseActivity(boolean notifyCaseActivity) {
        this.notifyCaseActivity = notifyCaseActivity;
    }

    public boolean isNotifyEmailDigest() {
        return notifyEmailDigest;
    }

    public void setNotifyEmailDigest(boolean notifyEmailDigest) {
        this.notifyEmailDigest = notifyEmailDigest;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}

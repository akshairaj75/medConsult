package com.backend.medconsult.entity.clinicalData;

import java.time.LocalDateTime;

public class BookedSlotDto {

    private LocalDateTime scheduledAt;
    private String status;

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

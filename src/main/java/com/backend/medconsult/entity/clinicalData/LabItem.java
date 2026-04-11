package com.backend.medconsult.entity.clinicalData;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.enums.LabItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lab_items", indexes = {
        @Index(name = "idx_lab_item_result", columnList = "lab_result_id")
})
public class LabItem {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)

    @Column(name = "item_id", nullable = false, updatable = false, length=36
)
    private UUID itemId;

    // FK → lab_results(lab_result_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_result_id", nullable = false)
    private LabResult labResult;

    @Column(name = "test_name", nullable = false, length = 100)
    private String testName;

    @Column(nullable = false, length = 50)
    private String value; // stored as string (important)

    @Column(length = 30)
    private String unit;

    @Column(name = "reference_min", length = 30)
    private String referenceMin;

    @Column(name = "reference_max", length = 30)
    private String referenceMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabItemStatus status = LabItemStatus.NORMAL;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder = 0;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public LabResult getLabResult() {
        return labResult;
    }

    public void setLabResult(LabResult labResult) {
        this.labResult = labResult;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReferenceMin() {
        return referenceMin;
    }

    public void setReferenceMin(String referenceMin) {
        this.referenceMin = referenceMin;
    }

    public String getReferenceMax() {
        return referenceMax;
    }

    public void setReferenceMax(String referenceMax) {
        this.referenceMax = referenceMax;
    }

    public LabItemStatus getStatus() {
        return status;
    }

    public void setStatus(LabItemStatus status) {
        this.status = status;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

}

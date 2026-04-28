package com.backend.medconsult.dto.clinicalDataDto;

import java.util.UUID;

import com.backend.medconsult.entity.clinicalData.LabItem;
import com.backend.medconsult.enums.LabItemStatus;

public class LabItemDto {
    private UUID itemId;
    private String testName;
    private String value;
    private String unit;
    private String referenceMin;
    private String referenceMax;
    private LabItemStatus itemStatus;
    private int sortOrder;

    public UUID getItemId() {
        return itemId;
    }
    public void setItemId(UUID itemId) {
        this.itemId = itemId;
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
    public LabItemStatus getItemStatus() {
        return itemStatus;
    }
    public void setItemStatus(LabItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
    public int getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public static LabItemDto fromEntity(LabItem labItem){
        LabItemDto dto = new LabItemDto();
        dto.setItemId(labItem.getItemId());
        dto.setTestName(labItem.getTestName());
        dto.setValue(labItem.getValue());
        dto.setUnit(labItem.getUnit());
        dto.setReferenceMin(labItem.getReferenceMin());
        dto.setReferenceMax(labItem.getReferenceMax());
        dto.setItemStatus(labItem.getItemStatus());
        dto.setSortOrder(labItem.getSortOrder());
        return dto;

    }
}

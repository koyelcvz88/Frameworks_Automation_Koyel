package com.thetestingacademy.model;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    // ===== Suite + Flow Control =====
    private String suiteType;
    private String flowType;

    // ===== Request Hierarchy =====
    private String requestType;
    private String requestSubType;
    private String requestSegment;

    // ===== Business Fields =====
    private String ocFeesPayer;
    private String internalCounsel;
    private String outsideCounselFirm;
    private String contactAttorney;

    // ===== Flags / Conditions =====
    private String isOcConflicted;
    private String requestClose;

    // ===== Location Data =====
    private String state;

    // =========================================================
    // ✅ NEW FIELD (REQUIRED FOR ReviewNewVendorPage)
    // =========================================================
    private String comment;

    // ================================
    // GETTERS & SETTERS (UNCHANGED + NEW ADDITION)
    // ================================

    public String getSuiteType() { return suiteType; }
    public void setSuiteType(String suiteType) { this.suiteType = suiteType; }

    public String getFlowType() { return flowType; }
    public void setFlowType(String flowType) { this.flowType = flowType; }

    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }

    public String getRequestSubType() { return requestSubType; }
    public void setRequestSubType(String requestSubType) { this.requestSubType = requestSubType; }

    public String getRequestSegment() { return requestSegment; }
    public void setRequestSegment(String requestSegment) { this.requestSegment = requestSegment; }

    public String getOcFeesPayer() { return ocFeesPayer; }
    public void setOcFeesPayer(String ocFeesPayer) { this.ocFeesPayer = ocFeesPayer; }

    public String getInternalCounsel() { return internalCounsel; }
    public void setInternalCounsel(String internalCounsel) { this.internalCounsel = internalCounsel; }

    public String getOutsideCounselFirm() { return outsideCounselFirm; }
    public void setOutsideCounselFirm(String outsideCounselFirm) { this.outsideCounselFirm = outsideCounselFirm; }

    public String getContactAttorney() { return contactAttorney; }
    public void setContactAttorney(String contactAttorney) { this.contactAttorney = contactAttorney; }

    public String getIsOcConflicted() { return isOcConflicted; }
    public void setIsOcConflicted(String isOcConflicted) { this.isOcConflicted = isOcConflicted; }

    public String getRequestClose() { return requestClose; }
    public void setRequestClose(String requestClose) { this.requestClose = requestClose; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    // =========================================================
    // ✅ NEW GETTER/SETTER (COMMENT FIELD)
    // =========================================================
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // =========================================================
    // NORMALIZE DATA (UPDATED)
    // =========================================================
    public void normalize() {

        suiteType = clean(suiteType);
        flowType = clean(flowType);

        requestType = clean(requestType);
        requestSubType = clean(requestSubType);
        requestSegment = clean(requestSegment);

        ocFeesPayer = clean(ocFeesPayer);
        internalCounsel = clean(internalCounsel);

        outsideCounselFirm = clean(outsideCounselFirm);
        contactAttorney = clean(contactAttorney);

        isOcConflicted = clean(isOcConflicted);
        requestClose = clean(requestClose);

        state = clean(state);

        // ✅ NEW
        comment = clean(comment);
    }

    // =========================================================
    // VALIDATION (UPDATED)
    // =========================================================
    public void validateRequiredFields() {

        List<String> missingFields = new ArrayList<>();

        if (isBlank(suiteType)) missingFields.add("suiteType");
        if (isBlank(flowType)) missingFields.add("flowType");

        if (isBlank(requestType)) missingFields.add("requestType");
        if (isBlank(requestSubType)) missingFields.add("requestSubType");
        if (isBlank(requestSegment)) missingFields.add("requestSegment");

        if (isBlank(ocFeesPayer)) missingFields.add("ocFeesPayer");
        if (isBlank(internalCounsel)) missingFields.add("internalCounsel");

        if (isBlank(state)) missingFields.add("state");

        // ❗ comment is OPTIONAL (kept flexible for approve/reject flows)
        // If you want strict enforcement, uncomment below:
        // if (isBlank(comment)) missingFields.add("comment");

        if (!missingFields.isEmpty()) {
            throw new RuntimeException(
                    "❌ DataModel Validation Failed. Missing fields: " + missingFields +
                            "\nFull DataModel: " + this
            );
        }
    }

    // =========================================================
    // SAFE CALL
    // =========================================================
    public void validateOrFail() {
        normalize();
        validateRequiredFields();
    }

    public boolean isValid() {
        try {
            validateRequiredFields();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================
    // HELPERS
    // =========================================================
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String clean(String value) {
        if (value == null) return null;

        value = value.trim();

        if (value.equalsIgnoreCase("null")) return null;
        if (value.isEmpty()) return null;

        return value;
    }

    // ================================
    // DEBUG
    // ================================
    @Override
    public String toString() {
        return "DataModel{" +
                "suiteType='" + suiteType + '\'' +
                ", flowType='" + flowType + '\'' +
                ", requestType='" + requestType + '\'' +
                ", requestSubType='" + requestSubType + '\'' +
                ", requestSegment='" + requestSegment + '\'' +
                ", ocFeesPayer='" + ocFeesPayer + '\'' +
                ", internalCounsel='" + internalCounsel + '\'' +
                ", outsideCounselFirm='" + outsideCounselFirm + '\'' +
                ", contactAttorney='" + contactAttorney + '\'' +
                ", isOcConflicted='" + isOcConflicted + '\'' +
                ", requestClose='" + requestClose + '\'' +
                ", state='" + state + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
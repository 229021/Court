package com.san.platform.JSON;

import java.io.Serializable;

public class QueryFace implements Serializable {

    Integer searchResultPosition;
    Integer maxResults;
    String faceLibType;
    String FDID;
    String certificateNumber;

    @Override
    public String toString() {
        return "QueryFace{" +
                "searchResultPosition='" + searchResultPosition + '\'' +
                ", maxResults='" + maxResults + '\'' +
                ", faceLibType='" + faceLibType + '\'' +
                ", FDID='" + FDID + '\'' +
                ", certificateNumber='" + certificateNumber + '\'' +
                '}';
    }

    public Integer getSearchResultPosition() {
        return searchResultPosition;
    }

    public void setSearchResultPosition(Integer searchResultPosition) {
        this.searchResultPosition = searchResultPosition;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public String getFaceLibType() {
        return faceLibType;
    }

    public void setFaceLibType(String faceLibType) {
        this.faceLibType = faceLibType;
    }

    public String getFDID() {
        return FDID;
    }

    public void setFDID(String FDID) {
        this.FDID = FDID;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}

package com.san.platform.trajectory;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Trajectory implements Serializable {


    @Id
    @Column(name = "trackPerId")
    private Integer trackPerId;

    @Column(name = "mapId")
    private Integer mapId;

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    @Column(name = "trackFaceImageBig")
    private String trackFaceImageBig;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "trackDate")
    private Date trackDate;

    @Column(name = "mapRegion")
    private Integer mapRegion;

    @Column(name = "mapX")
    private Integer mapX;

    @Column(name = "mapY")
    private Integer mapY;

    @Column(name = "deviceName")
    private String deviceName;

    @Column(name = "mapImage")
    private String mapImage;

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getTrackPerId() {
        return trackPerId;
    }

    public void setTrackPerId(Integer trackPerId) {
        this.trackPerId = trackPerId;
    }

    public String getTrackFaceImageBig() {
        return trackFaceImageBig;
    }

    public void setTrackFaceImageBig(String trackFaceImageBig) {
        this.trackFaceImageBig = trackFaceImageBig;
    }

    public Date getTrackDate() {
        return trackDate;
    }

    public void setTrackDate(Date trackDate) {
        this.trackDate = trackDate;
    }

    public Integer getMapRegion() {
        return mapRegion;
    }

    public void setMapRegion(Integer mapRegion) {
        this.mapRegion = mapRegion;
    }

    public Integer getMapX() {
        return mapX;
    }

    public void setMapX(Integer mapX) {
        this.mapX = mapX;
    }

    public Integer getMapY() {
        return mapY;
    }

    public void setMapY(Integer mapY) {
        this.mapY = mapY;
    }
}

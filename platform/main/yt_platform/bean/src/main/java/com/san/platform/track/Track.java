package com.san.platform.track;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_track")
public class Track implements Serializable {

    @Id
    @Column(name = "id")
    Integer id;
    @Column(name = "trackPerId")
    Integer trackPerId;
    @Column(name = "trackDeviceId")
    Integer trackDeviceId;
    @Column(name = "trackFaceImageSmail")
    String trackFaceImageSmail;
    @Column(name = "trackFaceImageBig")
    String trackFaceImageBig;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "trackDate")
    Date trackDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrackPerId() {
        return trackPerId;
    }

    public void setTrackPerId(Integer trackPerId) {
        this.trackPerId = trackPerId;
    }

    public Integer getTrackDeviceId() {
        return trackDeviceId;
    }

    public void setTrackDeviceId(Integer trackDeviceId) {
        this.trackDeviceId = trackDeviceId;
    }

    public String getTrackFaceImageSmail() {
        return trackFaceImageSmail;
    }

    public void setTrackFaceImageSmail(String trackFaceImageSmail) {
        this.trackFaceImageSmail = trackFaceImageSmail;
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
}

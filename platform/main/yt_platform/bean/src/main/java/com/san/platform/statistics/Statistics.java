package com.san.platform.statistics;

import java.io.Serializable;

public class Statistics implements Serializable {

    private String time;
    private Integer enter;
    private Integer leave;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getEnter() {
        return enter;
    }

    public void setEnter(Integer enter) {
        this.enter = enter;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "time='" + time + '\'' +
                ", enter=" + enter +
                ", leave=" + leave +
                '}';
    }
}

package com.san.platform.option;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/18 10:58
 * @Description: 选项模块实体
*/
@Entity
@Table(name = "tbl_option")
public class Option extends Page implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "optionId")
    //选项ID
    private Integer optionId;

    //选项Name
    @Column(name = "optionName")
    private String optionName;

    //选项优先级
    @Column(name = "optionLevel")
    private Integer optionLevel;

    //选项备注
    @Column(name = "optionMemo")
    private String optionMemo;

    //选项创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "optionCreateTime")
    private Date optionCreateTime;

    //选项删除时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "optionModTime")
    private Date optionModTime;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getOptionLevel() {
        return optionLevel;
    }

    public void setOptionLevel(Integer optionLevel) {
        this.optionLevel = optionLevel;
    }

    public String getOptionMemo() {
        return optionMemo;
    }

    public void setOptionMemo(String optionMemo) {
        this.optionMemo = optionMemo;
    }

    public Date getOptionCreateTime() {
        return optionCreateTime;
    }

    public void setOptionCreateTime(Date optionCreateTime) {
        this.optionCreateTime = optionCreateTime;
    }

    public Date getOptionModTime() {
        return optionModTime;
    }

    public void setOptionModTime(Date optionModTime) {
        this.optionModTime = optionModTime;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", optionName='" + optionName + '\'' +
                ", optionLevel=" + optionLevel +
                ", optionMemo='" + optionMemo + '\'' +
                ", optionCreateTime=" + optionCreateTime +
                ", optionModTime=" + optionModTime +
                '}';
    }

}

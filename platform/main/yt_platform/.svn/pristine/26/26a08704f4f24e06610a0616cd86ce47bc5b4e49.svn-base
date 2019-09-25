package com.san.platform.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户模块bean
 */
@Entity
@Table(name = "tbl_user")
public class User extends Page implements Serializable {
    @Override
    public String toString() {
        return "tbl_user{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", relName='" + relName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", relRoleId=" + relRoleId +
                ", userPhone='" + userPhone + '\'' +
                ", userStatus=" + userStatus +
                ", userMemo='" + userMemo + '\'' +
                ", userCreateTime='" + userCreateTime + '\'' +
                ", userModTime='" + userModTime + '\'' +
                ", isLock=" + isLock +
                ", changePwdTime='" + changePwdTime + '\'' +
                ", loginErrNum=" + loginErrNum +
                ", lockTime='" + lockTime + '\'' +
                ", safeQuestion='" + safeQuestion + '\'' +
                ", safeAnswer='" + safeAnswer + '\'' +
                '}';
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userId")
    Integer userId;
    @Column(name = "userName")
    String userName;
    @Column(name = "relName")
    String relName;
    @Column(name = "userPwd")
    String userPwd;
    @Column(name = "relRoleId")
    Integer relRoleId;
    @Column(name = "userPhone")
    String userPhone;
    @Column(name = "userStatus")
    Integer userStatus;
    @Column(name = "userMemo")
    String userMemo;
    @Column(name = "userCreateTime")
    Date userCreateTime;
    @Column(name = "userModTime")
    Date userModTime;
    @Column(name = "isLock")
    Integer isLock;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "changePwdTime")
    Date changePwdTime;
    @Column(name = "loginErrNum")
    Integer loginErrNum;
    @Column(name = "lockTime")
    Date lockTime;
    @Column(name = "safeQuestion")
    String safeQuestion;
    @Column(name = "safeAnswer")
    String safeAnswer;
    @Column(name = "userIds")
    private List<Integer> userIds;

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getRelRoleId() {
        return relRoleId;
    }

    public void setRelRoleId(Integer relRoleId) {
        this.relRoleId = relRoleId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserModTime() {
        return userModTime;
    }

    public void setUserModTime(Date userModTime) {
        this.userModTime = userModTime;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Date getChangePwdTime() {
        return changePwdTime;
    }

    public void setChangePwdTime(Date changePwdTime) {
        this.changePwdTime = changePwdTime;
    }

    public Integer getLoginErrNum() {
        return loginErrNum;
    }

    public void setLoginErrNum(Integer loginErrNum) {
        this.loginErrNum = loginErrNum;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getSafeQuestion() {
        return safeQuestion;
    }

    public void setSafeQuestion(String safeQuestion) {
        this.safeQuestion = safeQuestion;
    }

    public String getSafeAnswer() {
        return safeAnswer;
    }

    public void setSafeAnswer(String safeAnswer) {
        this.safeAnswer = safeAnswer;
    }
}

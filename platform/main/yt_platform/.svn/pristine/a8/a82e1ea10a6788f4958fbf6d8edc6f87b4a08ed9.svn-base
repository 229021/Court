package com.san.platform.face;

import javax.persistence.*;

@Entity
@Table(name = "tbl_faceinfo")
public class FaceServer implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8201405835580211997L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 10)
    private Integer id;

    /** faceGuid */
    @Column(name = "faceGuid", nullable = true, length = 20)
    private String faceGuid;

    /** 服务IP */
    @Column(name = "faceServerIp", nullable = true, length = 20)
    private String faceServerIp;

    /** 服务端口 */
    @Column(name = "faceServerPort", nullable = true, length = 10)
    private Integer faceServerPort;

    /** 登陆用户名 */
    @Column(name = "faceUserName", nullable = true, length = 255)
    private String faceUserName;

    /** 登陆密码 */
    @Column(name = "faceUserPwd", nullable = true, length = 255)
    private String faceUserPwd;

    /** 厂商信息 */
    @Column(name = "faceFirm", nullable = true, length = 255)
    private String faceFirm;

    /** 备注 */
    @Column(name = "faceMemo", nullable = true, length = 65535)
    private String faceMemo;

    /**
     * 获取id
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     *
     * @param id
     *          id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取faceGuid
     *
     * @return faceGuid
     */
    public String getFaceGuid() {
        return this.faceGuid;
    }

    /**
     * 设置faceGuid
     *
     * @param faceGuid
     */
    public void setFaceGuid(String faceGuid) {
        this.faceGuid = faceGuid;
    }

    /**
     * 获取服务IP
     *
     * @return 服务IP
     */
    public String getFaceServerIp() {
        return this.faceServerIp;
    }

    /**
     * 设置服务IP
     *
     * @param faceServerIp
     *          服务IP
     */
    public void setFaceServerIp(String faceServerIp) {
        this.faceServerIp = faceServerIp;
    }

    /**
     * 获取服务端口
     *
     * @return 服务端口
     */
    public Integer getFaceServerPort() {
        return this.faceServerPort;
    }

    /**
     * 设置服务端口
     *
     * @param faceServerPort
     *          服务端口
     */
    public void setFaceServerPort(Integer faceServerPort) {
        this.faceServerPort = faceServerPort;
    }

    /**
     * 获取登陆用户名
     *
     * @return 登陆用户名
     */
    public String getFaceUserName() {
        return this.faceUserName;
    }

    /**
     * 设置登陆用户名
     *
     * @param faceUserName
     *          登陆用户名
     */
    public void setFaceUserName(String faceUserName) {
        this.faceUserName = faceUserName;
    }

    /**
     * 获取登陆密码
     *
     * @return 登陆密码
     */
    public String getFaceUserPwd() {
        return this.faceUserPwd;
    }

    /**
     * 设置登陆密码
     *
     * @param faceUserPwd
     *          登陆密码
     */
    public void setFaceUserPwd(String faceUserPwd) {
        this.faceUserPwd = faceUserPwd;
    }

    /**
     * 获取厂商信息
     *
     * @return 厂商信息
     */
    public String getFaceFirm() {
        return this.faceFirm;
    }

    /**
     * 设置厂商信息
     *
     * @param faceFirm
     *          厂商信息
     */
    public void setFaceFirm(String faceFirm) {
        this.faceFirm = faceFirm;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getFaceMemo() {
        return this.faceMemo;
    }

    /**
     * 设置备注
     *
     * @param faceMemo
     *          备注
     */
    public void setFaceMemo(String faceMemo) {
        this.faceMemo = faceMemo;
    }
}

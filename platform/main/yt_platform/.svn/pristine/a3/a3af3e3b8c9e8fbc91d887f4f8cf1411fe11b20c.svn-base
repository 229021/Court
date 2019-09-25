package com.san.platform.region;

import javax.persistence.*;

@Entity
@Table(name = "tbl_region")
public class Region implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2715117580337219596L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regionId", unique = true, nullable = false, length = 10)
    private Integer regionId;

    /** 父id */
    @Column(name = "regionParentId", nullable = true, length = 10)
    private Integer regionParentId;

    /** 区域名称 */
    @Column(name = "regionName", nullable = true, length = 255)
    private String regionName;

    /** 优先级 */
    @Column(name = "regionIndex", nullable = true, length = 10)
    private Integer regionIndex;

    /** 备注 */
    @Column(name = "regionMemo", nullable = true, length = 65535)
    private String regionMemo;

    /** 所有子信息 */
    @Column(name = "regionSon", nullable = true, length = 255)
    private String regionSon;

    /**
     * 获取id
     *
     * @return id
     */
    public Integer getRegionId() {
        return this.regionId;
    }

    /**
     * 设置id
     *
     * @param regionId
     *          id
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * 获取父id
     *
     * @return 父id
     */
    public Integer getRegionParentId() {
        return this.regionParentId;
    }

    /**
     * 设置父id
     *
     * @param regionParentId
     *          父id
     */
    public void setRegionParentId(Integer regionParentId) {
        this.regionParentId = regionParentId;
    }

    /**
     * 获取区域名称
     *
     * @return 区域名称
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置区域名称
     *
     * @param regionName
     *          区域名称
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 获取优先级
     *
     * @return 优先级
     */
    public Integer getRegionIndex() {
        return this.regionIndex;
    }

    /**
     * 设置优先级
     *
     * @param regionIndex
     *          优先级
     */
    public void setRegionIndex(Integer regionIndex) {
        this.regionIndex = regionIndex;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRegionMemo() {
        return this.regionMemo;
    }

    /**
     * 设置备注
     *
     * @param regionMemo
     *          备注
     */
    public void setRegionMemo(String regionMemo) {
        this.regionMemo = regionMemo;
    }

    /**
     * 获取所有子信息
     *
     * @return 所有子信息
     */
    public String getRegionSon() {
        return this.regionSon;
    }

    /**
     * 设置所有子信息
     *
     * @param regionSon
     *          所有子信息
     */
    public void setRegionSon(String regionSon) {
        this.regionSon = regionSon;
    }
}

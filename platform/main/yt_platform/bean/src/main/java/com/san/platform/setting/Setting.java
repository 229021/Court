package com.san.platform.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBL_SETTING 系统设置实体bean
 *
 *
 * @author zsa
 * @version 1.0.0 2019-04-15
 */
@Entity
@Table(name = "tbl_setting")
public class Setting implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2340387629160156865L;

    /** id */
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 10)
    private Integer id;

    /** key */
    @Column(name = "setKey", nullable = true, length = 255)
    private String setKey;

    /** value值 */
    @Column(name = "setValue", nullable = true, length = 255)
    private String setValue;

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
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取key
     *
     * @return key
     */
    public String getSetKey() {
        return this.setKey;
    }

    /**
     * 设置key
     *
     * @param setKey
     *          key
     */
    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }

    /**
     * 获取value值
     *
     * @return value值
     */
    public String getSetValue() {
        return this.setValue;
    }

    /**
     * 设置value值
     *
     * @param setValue
     *          value值
     */
    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }
}

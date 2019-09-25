package com.san.platform.map;

import com.san.platform.page.Page;

import javax.persistence.*;

@Entity
@Table(name = "tbl_map")
public class Map extends Page implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7462009331120778523L;

    /** mapId */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapId", unique = true, nullable = false, length = 10)
    private Integer mapId;

    /** mapName */
    @Column(name = "mapName", nullable = true, length = 255)
    private String mapName;

    /** mapRegion */
    @Column(name = "mapRegion", nullable = true, length = 10)
    private Integer mapRegion;

    /** mapImage */
    @Column(name = "mapImage", nullable = true, length = 255)
    private String mapImage;

    /** mapx */
    @Column(name = "mapX", nullable = true, length = 10)
    private Integer mapx;

    /** mapy */
    @Column(name = "mapY", nullable = true, length = 10)
    private Integer mapy;

    /** mapMemo */
    @Column(name = "mapMemo", nullable = true, length = 65535)
    private String mapMemo;

    /**
     * 获取mapId
     *
     * @return mapId
     */
    public Integer getMapId() {
        return this.mapId;
    }

    /**
     * 设置mapId
     *
     * @param mapId
     */
    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    /**
     * 获取mapName
     *
     * @return mapName
     */
    public String getMapName() {
        return this.mapName;
    }

    /**
     * 设置mapName
     *
     * @param mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * 获取mapRegion
     *
     * @return mapRegion
     */
    public Integer getMapRegion() {
        return this.mapRegion;
    }

    /**
     * 设置mapRegion
     *
     * @param mapRegion
     */
    public void setMapRegion(Integer mapRegion) {
        this.mapRegion = mapRegion;
    }

    /**
     * 获取mapImage
     *
     * @return mapImage
     */
    public String getMapImage() {
        return this.mapImage;
    }

    /**
     * 设置mapImage
     *
     * @param mapImage
     */
    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    /**
     * 获取mapx
     *
     * @return mapx
     */
    public Integer getMapx() {
        return this.mapx;
    }

    /**
     * 设置mapx
     *
     * @param mapx
     */
    public void setMapx(Integer mapx) {
        this.mapx = mapx;
    }

    /**
     * 获取mapy
     *
     * @return mapy
     */
    public Integer getMapy() {
        return this.mapy;
    }

    /**
     * 设置mapy
     *
     * @param mapy
     */
    public void setMapy(Integer mapy) {
        this.mapy = mapy;
    }

    /**
     * 获取mapMemo
     *
     * @return mapMemo
     */
    public String getMapMemo() {
        return this.mapMemo;
    }

    /**
     * 设置mapMemo
     *
     * @param mapMemo
     */
    public void setMapMemo(String mapMemo) {
        this.mapMemo = mapMemo;
    }
}

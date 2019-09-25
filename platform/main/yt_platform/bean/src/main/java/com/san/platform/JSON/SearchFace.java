package com.san.platform.JSON;

/**
 * 海康-以图搜图
 */
public class SearchFace {
    private int searchResultPosition = 0;
    private int modelMaxNum = 50;
    private int maxResults = 20;
    private String dataType = "modelData";
    private String targetModelData = "";
    private double minSimilarity = 0.3;

    public int getSearchResultPosition() {
        return searchResultPosition;
    }

    public void setSearchResultPosition(int searchResultPosition) {
        this.searchResultPosition = searchResultPosition;
    }

    public int getModelMaxNum() {
        return modelMaxNum;
    }

    public void setModelMaxNum(int modelMaxNum) {
        this.modelMaxNum = modelMaxNum;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTargetModelData() {
        return targetModelData;
    }

    public void setTargetModelData(String targetModelData) {
        this.targetModelData = targetModelData;
    }

    public double getMinSimilarity() {
        return minSimilarity;
    }

    public void setMinSimilarity(double minSimilarity) {
        this.minSimilarity = minSimilarity;
    }

    public int getMaxSimilarity() {
        return maxSimilarity;
    }

    public void setMaxSimilarity(int maxSimilarity) {
        this.maxSimilarity = maxSimilarity;
    }

    private int maxSimilarity = 1;

}

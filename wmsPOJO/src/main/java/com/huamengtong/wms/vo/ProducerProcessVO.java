package com.huamengtong.wms.vo;

import java.io.Serializable;

/**
 * Created by Edwin on 2017/1/10.
 */
public class ProducerProcessVO implements Serializable {

    private String skuName; //商品名称

    private Integer skuType; //商品类别

    private String spec; //规格

    private Long inventoryId; //原料来源

    private Integer qty; //数量

    private String workGroupNo; //批次号


    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getSkuType() {
        return skuType;
    }

    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getWorkGroupNo() {
        return workGroupNo;
    }

    public void setWorkGroupNo(String workGroupNo) {
        this.workGroupNo = workGroupNo;
    }
}

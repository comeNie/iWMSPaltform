package com.huamengtong.wms.dto.sku;

import com.huamengtong.wms.entity.po.BasePO;

import java.io.Serializable;

/**
 * Created by StickT on 2017/1/5.
 */
public class TWmsSkuCargoOwnerDTO extends BasePO implements Serializable {
    private Long skuId;

    private String sku;

    private Long cargoOwnerId;

    private String cargoOwnerBarcode;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public String getCargoOwnerBarcode() {
        return cargoOwnerBarcode;
    }

    public void setCargoOwnerBarcode(String cargoOwnerBarcode) {
        this.cargoOwnerBarcode = cargoOwnerBarcode;
    }
}

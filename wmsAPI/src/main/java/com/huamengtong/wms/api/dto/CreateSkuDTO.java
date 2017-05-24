package com.huamengtong.wms.api.dto;

public class CreateSkuDTO {

    /***
     * 货主ID
     */
    private Long cargoOwnerId;

    /***
     * 商品sku
     */
    private String sku;

    /***
     * 商品upc码
     */
    private String upc;

    /***
     * 商品条码
     */
    private String barcode;

    /***
     * 商品名称
     */
    private String itemName;

    /***
     * 商品类别
     */
    private Long categorysId;

    /**
     * 商品规格
     */
    private String spec;

    public Long getCargoOwnerId() {
        return cargoOwnerId;
    }

    public void setCargoOwnerId(Long cargoOwnerId) {
        this.cargoOwnerId = cargoOwnerId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getCategorysId() {
        return categorysId;
    }

    public void setCategorysId(Long categorysId) {
        this.categorysId = categorysId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}

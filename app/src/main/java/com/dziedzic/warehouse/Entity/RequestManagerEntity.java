package com.dziedzic.warehouse.Entity;

public class RequestManagerEntity {
    private String requestName;
    private ProductDTO productDTO;
    private  ProductEditDTO productEditDTO;

    public RequestManagerEntity(String requestName, ProductDTO productDTO, ProductEditDTO productEditDTO) {
        this.requestName = requestName;
        this.productDTO = productDTO;
        this.productEditDTO = productEditDTO;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public ProductEditDTO getProductEditDTO() {
        return productEditDTO;
    }

    public void setProductEditDTO(ProductEditDTO productEditDTO) {
        this.productEditDTO = productEditDTO;
    }
}

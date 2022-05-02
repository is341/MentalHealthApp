package com.mental_health.dataModel;

public class DataModelDoctors {

    private String id;
    private String image;
    private String name;
    private String status;
    private String mobile;


    public DataModelDoctors(String id,String image, String name, String status, String mobile) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.status = status;
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

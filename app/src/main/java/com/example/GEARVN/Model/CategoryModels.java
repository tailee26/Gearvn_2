package com.example.GEARVN.Model;


public class CategoryModels {

    private String id;
    private String tenloai;
    private String hinhanh;

    public CategoryModels() {
    }


    public CategoryModels(String tenloai, String hinhanh, String id) {
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
        this.id = id;
    }
    public String getId( ) {return id;}

    public void setId(String id){this.id = id; }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}

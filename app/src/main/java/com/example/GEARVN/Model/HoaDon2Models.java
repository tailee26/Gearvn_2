package com.example.GEARVN.Model;


public class HoaDon2Models {

    @Override
    public String toString() {
        return "HoaDonReRach{" +
                "UID=" + UID +
                ", diachi=" + diachi +
                ", hoten=" + hoten +
                ", ngaydat='" + ngaydat + '\'' +
                ", sdt='" + sdt + '\'' +
                ", tongtien='" + tongtien + '\'' +
                ", trangthai='" + trangthai + '\'' +
                '}';
    }

    private  String UID;
    private  String diachi;
    private  String hoten;
    private  String ngaydat;
    private  String phuongthuc;
    private  String sdt;
    private  long tongtien;
    private  long trangthai;

    public HoaDon2Models(){

    }
    public HoaDon2Models(String UID, String diachi, String hoten, String ngaydat, String phuongthuc, String sdt, long tongtien, long trangthai) {
        this.UID = UID;
        this.diachi = diachi;
        this.hoten = hoten;
        this.ngaydat = ngaydat;
        this.phuongthuc = phuongthuc;
        this.sdt = sdt;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public long getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(long trangthai) {
        this.trangthai = trangthai;
    }
}

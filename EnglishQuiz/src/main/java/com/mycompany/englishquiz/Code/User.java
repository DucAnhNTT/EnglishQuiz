package com.mycompany.englishquiz.Code;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

    private int id;
    private String hoTen;
    private String matKhau;
    private String queQuan;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private String ngayGiaNhap;

    public User(String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinhStr, String ngayGiaNhapStr) throws ParseException {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.ngayGiaNhap = ngayGiaNhapStr;
    }

    public User(int id, String hoTen, String matKhau, String queQuan, String gioiTinh, LocalDate ngaySinh, String ngayGiaNhap) {
        this.id = id;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayGiaNhap = ngayGiaNhap;
    }

    public User(int id, String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinhStr, String ngayGiaNhapStr) {
        this.id = id;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.ngayGiaNhap = ngayGiaNhapStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    public void setNgayGiaNhap(String ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }
}

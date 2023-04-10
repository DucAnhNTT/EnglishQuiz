package com.mycompany.englishquiz.Code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {

    private int id;
    private String hoTen;
    private String matKhau;
    private String queQuan;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private LocalDate ngayGiaNhap;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public User(String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinhStr, String ngayGiaNhapStr) {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = LocalDate.parse(ngaySinhStr, DATE_FORMATTER);
        this.ngayGiaNhap = LocalDate.parse(ngayGiaNhapStr, DATE_FORMATTER);
    }

    public User(String hoTen, String matKhau, String queQuan, String gioiTinh, LocalDate ngaySinh, LocalDate ngayGiaNhap) {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayGiaNhap = ngayGiaNhap;
    }

    public User(int id, String hoTen, String matKhau, String queQuan, String gioiTinh, LocalDate ngaySinh, LocalDate ngayGiaNhap) {
        this.id = id;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayGiaNhap = ngayGiaNhap;
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

    public LocalDate getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    public void setNgayGiaNhap(LocalDate ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }
}

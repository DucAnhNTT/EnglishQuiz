package com.mycompany.englishquiz.Code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class User {

    private int id;
    private String hoTen;
    private String matKhau;
    private String queQuan;
    private String gioiTinh;
    private LocalDate ngaySinh;
    private LocalDate ngayGiaNhap;
    private Map<String, Double> marks;
    private int type_User;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public User() {
    }

    public User(int id, String hoTen, String matKhau, String queQuan, String gioiTinh, LocalDate ngaySinh, LocalDate ngayGiaNhap, int typeUser) {
        this.id = id;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayGiaNhap = ngayGiaNhap;
        this.type_User = typeUser;
    }

    public User(int id, String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinh, String ngayGiaNhap, int type_User) {
        this.id = id;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = LocalDate.parse(ngaySinh, DATE_FORMATTER);
        this.ngayGiaNhap = LocalDate.parse(ngayGiaNhap, DATE_FORMATTER);
        this.type_User = type_User;
    }


    public User(String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinhStr, String ngayGiaNhapStr, int type_User) {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = LocalDate.parse(ngaySinhStr, DATE_FORMATTER);
        this.ngayGiaNhap = LocalDate.parse(ngayGiaNhapStr, DATE_FORMATTER);
        this.type_User = type_User;
    }

    public User(String hoTen, String matKhau, String queQuan, String gioiTinh, LocalDate ngaySinh, LocalDate ngayGiaNhap, int type_User) {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayGiaNhap = ngayGiaNhap;
        this.type_User = type_User;
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

    public Map<String, Double> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, Double> marks) {
        this.marks = marks;
    }

    public int getType_User() {
        return type_User;
    }

    public void setType_User(int type_User) {
        this.type_User = type_User;
    }

    public boolean isManager() {
        return type_User == 1;
    }
}

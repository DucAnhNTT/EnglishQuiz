package com.mycompany.englishquiz.Code;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private int id;
    private String hoTen;
    private String matKhau;
    private String queQuan;
    private String gioiTinh;
    private Date ngaySinh;
    private String ngayGiaNhap;

 public User(String hoTen, String matKhau, String queQuan, String gioiTinh, String ngaySinhStr, String ngayGiaNhap) throws ParseException {
    if (matKhau == null) {
        throw new IllegalArgumentException("matKhau field cannot be null");
    }
    this.hoTen = hoTen;
    this.matKhau = matKhau;
    this.queQuan = queQuan;
    this.gioiTinh = gioiTinh;
    try {
        this.ngaySinh = Utils.f.parse(ngaySinhStr);
    } catch (ParseException e) {
        // If the date is not in the format "yyyy-MM-dd", try parsing it as "dd/MM/yyyy"
        try {
            this.ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinhStr);
        } catch (ParseException ex) {
            throw new ParseException("Invalid date format. Please enter date in yyyy-MM-dd or dd/MM/yyyy format.", 0);
        }
    }
    this.ngayGiaNhap = ngayGiaNhap;
}

    // Getter and setter methods for the instance variables
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the hoTen
     */
    public String getHoTen() {
        return hoTen;
    }

    /**
     * @param hoTen the hoTen to set
     */
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    /**
     * @return the queQuan
     */
    public String getQueQuan() {
        return queQuan;
    }

    /**
     * @param queQuan the queQuan to set
     */
    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    /**
     * @return the gioiTinh
     */
    public String getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    /**
     * @return the ngaySinh
     */
    public Date getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * @return the ngayGiaNhap
     */
    public String getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    /**
     * @param ngayGiaNhap the ngayGiaNhap to set
     */
    public void setNgayGiaNhap(String ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }

    /**
     * @return the matKhau
     */
    public String getMatKhau() {
        return matKhau;
    }

    /**
     * @param matKhau the matKhau to set
     */
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}

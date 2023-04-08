package com.mycompany.englishquiz.Code;

import com.mycompany.englishquiz.Code.Utils;
import java.text.ParseException;
import java.util.Date;

public class User {

    private int id;
    private String hoTen;
    private String queQuan;
    private String gioiTinh;
    private Date ngaySinh;
    private String ngayGiaNhap;

    public User(String hoTen, String queQuan, String gioiTinh, String ngaySinhStr) throws ParseException {
        this.hoTen = hoTen;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        if (!ngaySinhStr.isEmpty()) {
            try {
                this.ngaySinh = Utils.f.parse(ngaySinhStr);
            } catch (ParseException e) {
                throw new ParseException("Invalid date format. Please enter date in yyyy-MM-dd format.", 0);
            }
        }
    }

    public User(String hoTen, String queQuan, String gioiTinh, String ngaySinh, String ngayGiaNhap) throws ParseException {
        this.hoTen = hoTen;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
        try {
            this.ngaySinh = Utils.f.parse(ngaySinh);
        } catch (ParseException e) {
            throw new ParseException("Invalid date format. Please enter date in dd/MM/yyyy format.", 0);
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
}

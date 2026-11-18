package model;

import util.TinhDiem;

/** Một dòng điểm: sinh viên + môn học + điểm quá trình + điểm thi. Khóa chính là (MaSV, MaMH). */
public class Diem {
    private String maSV;
    private String hoTen;
    private String maMH;
    private String tenMH;
    private int soTinChi;
    private double diemQT;
    private double diemThi;

    public Diem(String maSV, String hoTen, String maMH, String tenMH, int soTinChi, double diemQT, double diemThi) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.diemQT = diemQT;
        this.diemThi = diemThi;
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public String getMaMH() { return maMH; }
    public String getTenMH() { return tenMH; }
    public int getSoTinChi() { return soTinChi; }
    public double getDiemQT() { return diemQT; }
    public double getDiemThi() { return diemThi; }

    public double getTongKet() { return TinhDiem.tongKet(diemQT, diemThi); }
    public String getXepLoai() { return TinhDiem.xepLoai(getTongKet()); }
}

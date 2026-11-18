package model;

/** Một môn học (tương ứng bảng MonHoc). */
public class MonHoc {
    private String maMH;
    private String tenMH;
    private int soTinChi;

    public MonHoc(String maMH, String tenMH, int soTinChi) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
    }

    public String getMaMH() { return maMH; }
    public String getTenMH() { return tenMH; }
    public int getSoTinChi() { return soTinChi; }

    @Override
    public String toString() { return maMH + " - " + tenMH; }
}

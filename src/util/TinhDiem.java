package util;

/** Các công thức tính điểm. Điểm tổng kết = Điểm quá trình x 40% + Điểm thi x 60%. */
public class TinhDiem {
    public static final double HE_SO_QT = 0.4;
    public static final double HE_SO_THI = 0.6;

    /** Chuyển chuỗi thành điểm, báo lỗi nếu rỗng, không phải số hoặc ngoài khoảng 0-10. */
    public static double docDiem(String text, String tenO) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(tenO + " không được để trống.");
        }
        double d;
        try {
            d = Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(tenO + " phải là số.");
        }
        if (d < 0 || d > 10) {
            throw new IllegalArgumentException(tenO + " phải nằm trong khoảng 0 đến 10.");
        }
        return d;
    }

    /** Điểm tổng kết, làm tròn 2 chữ số thập phân (giống cột DiemTK trong SQL). */
    public static double tongKet(double qt, double thi) {
        double kq = qt * HE_SO_QT + thi * HE_SO_THI;
        return Math.round(kq * 100.0) / 100.0;
    }

    public static String xepLoai(double tk) {
        if (tk >= 8.0) return "Giỏi";
        if (tk >= 6.5) return "Khá";
        if (tk >= 5.0) return "Trung bình";
        return "Yếu";
    }

    /** Điểm trung bình = Tổng(TK x tín chỉ) / Tổng tín chỉ. Không có môn nào thì trả về -1. */
    public static double trungBinh(double[] tongKet, int[] tinChi) {
        double tong = 0;
        int tc = 0;
        for (int i = 0; i < tongKet.length; i++) {
            tong += tongKet[i] * tinChi[i];
            tc += tinChi[i];
        }
        if (tc == 0) return -1;
        return Math.round(tong / tc * 100.0) / 100.0;
    }
}

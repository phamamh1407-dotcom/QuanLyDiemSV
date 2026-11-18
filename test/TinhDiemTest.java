import util.TinhDiem;

/** Kiểm thử công thức tính điểm (không cần database). Chạy: xem README. */
public class TinhDiemTest {
    static int loi = 0;

    static void kiemTra(String ten, boolean dung) {
        System.out.println((dung ? "PASS  " : "FAIL  ") + ten);
        if (!dung) loi++;
    }

    static boolean nemLoi(String s) {
        try { TinhDiem.docDiem(s, "Điểm"); return false; } catch (IllegalArgumentException e) { return true; }
    }

    public static void main(String[] args) {
        kiemTra("TK(8, 9) = 8.6", TinhDiem.tongKet(8, 9) == 8.6);
        kiemTra("TK(0, 0) = 0.0", TinhDiem.tongKet(0, 0) == 0.0);
        kiemTra("TK(10, 10) = 10.0", TinhDiem.tongKet(10, 10) == 10.0);
        kiemTra("TK(7.5, 6.5) = 6.9", TinhDiem.tongKet(7.5, 6.5) == 6.9);
        kiemTra("TK(7.3, 6.1) = 6.58 (2 chu so thap phan)", TinhDiem.tongKet(7.3, 6.1) == 6.58);
        kiemTra("TK SV001 JAVA (8.0, 7.5) = 7.7", TinhDiem.tongKet(8.0, 7.5) == 7.7);
        kiemTra("DTB SV001 (7.7x3 + 8.7x3)/6 = 8.2", TinhDiem.trungBinh(new double[]{7.7, 8.7}, new int[]{3, 3}) == 8.2);
        kiemTra("Xep loai 8.0 = Giỏi", TinhDiem.xepLoai(8.0).equals("Giỏi"));
        kiemTra("Xep loai 6.5 = Khá", TinhDiem.xepLoai(6.5).equals("Khá"));
        kiemTra("Xep loai 5.0 = Trung bình", TinhDiem.xepLoai(5.0).equals("Trung bình"));
        kiemTra("Xep loai 4.9 = Yếu", TinhDiem.xepLoai(4.9).equals("Yếu"));
        kiemTra("DTB 1 mon (8.0, 3 TC) = 8.0", TinhDiem.trungBinh(new double[]{8.0}, new int[]{3}) == 8.0);
        kiemTra("DTB nhieu mon: (8x3 + 6x2)/5 = 7.2",
                TinhDiem.trungBinh(new double[]{8.0, 6.0}, new int[]{3, 2}) == 7.2);
        kiemTra("DTB chua co mon nao = -1 (khong chia cho 0)", TinhDiem.trungBinh(new double[]{}, new int[]{}) == -1);
        kiemTra("Diem 11 bi tu choi", nemLoi("11"));
        kiemTra("Diem am bi tu choi", nemLoi("-1"));
        kiemTra("Diem la chu bi tu choi", nemLoi("abc"));
        kiemTra("Diem rong bi tu choi", nemLoi("  "));
        kiemTra("Diem 8,5 (dau phay) hop le", TinhDiem.docDiem("8,5", "Điểm") == 8.5);
        kiemTra("Diem 0 va 10 hop le", TinhDiem.docDiem("0", "x") == 0 && TinhDiem.docDiem("10", "x") == 10);
        System.out.println(loi == 0 ? "\nTẤT CẢ ĐẠT" : "\nCÓ " + loi + " LỖI");
        System.exit(loi == 0 ? 0 : 1);
    }
}

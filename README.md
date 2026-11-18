# Quản lý điểm sinh viên bằng Java

Đồ án môn Java (Java3004). Công nghệ: **Java Swing + JDBC + Microsoft SQL Server**, soạn bằng VS Code.

## Chức năng
- **Trang chủ**: menu, thống kê số sinh viên / môn học / bản ghi điểm, báo trạng thái kết nối CSDL.
- **Quản lý sinh viên**: thêm, sửa, xóa (có xác nhận), tìm theo mã / tên / lớp.
- **Quản lý điểm**: thêm, sửa, xóa, tìm theo mã SV / tên / môn; tự tính điểm tổng kết và xếp loại; nút tính điểm trung bình theo tín chỉ.

## Công thức
- Điểm tổng kết = Điểm quá trình × 40% + Điểm thi × 60% (làm tròn 2 chữ số, giống cột `DiemTK` trong SQL)
- Điểm trung bình = Σ(Tổng kết × Tín chỉ) / Σ Tín chỉ (làm tròn 2 chữ số)
- Xếp loại: ≥ 8.0 Giỏi; ≥ 6.5 Khá; ≥ 5.0 Trung bình; còn lại Yếu
- Điểm hợp lệ từ 0 đến 10. (Công thức là quy ước của đồ án; nếu giảng viên yêu cầu khác, sửa trong `src/util/TinhDiem.java`.)

## Cấu trúc
```
src/Main.java            điểm bắt đầu
src/model/               SinhVien, MonHoc, Diem
src/dao/                 SinhVienDAO, MonHocDAO, DiemDAO, ThongKeDAO (JDBC + PreparedStatement)
src/ui/                  MainFrame, SinhVienFrame, DiemFrame (Swing)
src/util/                DBConnection (kết nối), TinhDiem (công thức)
sql/QuanLyDiemSV.sql     script CSDL của đồ án (5 bảng: Khoa, Lop, SinhVien, MonHoc, Diem) + dữ liệu mẫu
lib/                     chép JDBC driver mssql-jdbc-*.jar vào đây
test/TinhDiemTest.java   kiểm thử công thức (không cần CSDL)
docs/                    hướng dẫn từng bước, test case, báo cáo kiểm tra
db.properties.example    mẫu cấu hình kết nối
chay.bat / test.bat      chạy chương trình / chạy kiểm thử
```

## Cách chạy nhanh
1. Cài JDK 17+, SQL Server + SSMS, bật SQL Server Authentication và TCP/IP (xem `docs/HUONG_DAN_TUNG_BUOC.md`).
2. Mở `sql/QuanLyDiemSV.sql` trong SSMS, bấm Execute.
3. Tải JDBC driver, chép file `.jar` vào `lib/`.
4. Chạy `chay.bat` lần đầu → sửa mật khẩu trong `db.properties` → chạy `chay.bat` lần nữa.

## Lỗi thường gặp
| Thông báo | Cách xử lý |
|---|---|
| Thiếu JDBC Driver | Chép `mssql-jdbc-*.jar` vào `lib/` |
| Login failed for user 'sa' | Sai mật khẩu, hoặc chưa bật Mixed Mode / chưa bật tài khoản sa |
| The TCP/IP connection to the host ... has failed | Chưa bật TCP/IP port 1433 hoặc chưa restart dịch vụ SQL Server |
| Cannot open database "QuanLyDiemSV" | Chưa chạy file SQL |
| Chữ tiếng Việt bị lỗi | Mở lại file SQL bằng SSMS (UTF-8) và chạy lại |

> Không đưa `db.properties` (chứa mật khẩu) lên GitHub — đã có trong `.gitignore`.

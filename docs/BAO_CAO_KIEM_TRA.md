# BÁO CÁO KIỂM TRA (trung thực)

| Hạng mục | Trạng thái | Bằng chứng / ghi chú |
|---|---|---|
| Biên dịch toàn bộ source (JDK 17) | PASS | `javac` biên dịch 12 file, chỉ có cảnh báo serialVersionUID (vô hại) |
| Kiểm thử công thức tính điểm | PASS | `TinhDiemTest`: 20/20 đạt |
| Chạy script SQL trên SQL Server | NOT TESTED | Môi trường tạo file không có SQL Server. Script chưa được thực thi. |
| Kết nối JDBC thực tế | BLOCKED | Cần SQL Server + driver trên máy của bạn |
| CRUD sinh viên / điểm với CSDL thật | NOT TESTED | Chỉ kiểm tra bằng đọc code; chạy TC10–TC27 trên máy thật |
| Giao diện hiển thị | NOT TESTED | Không mở được cửa sổ Swing trong môi trường tạo file |
| Báo cáo Word, thiết kế CSDL Word, ERD, hướng dẫn bảo vệ | CHƯA LÀM | Làm ở bước tiếp theo sau khi phần mềm chạy được |
| Đẩy lên GitHub | CHƯA LÀM | Cần tài khoản của bạn; xem BƯỚC 7 |

## Giả định cần xác nhận với giảng viên
- Java được viết theo đúng file SQL gốc của bạn (5 bảng: Khoa, Lop, SinhVien, MonHoc, Diem); file SQL được giữ nguyên. Không có source Java gốc nên phần Java viết mới, 3 form.
- Công thức 40% / 60% lấy từ cột DiemTK trong SQL của bạn; thang xếp loại là quy ước của mình, chưa có yêu cầu chính thức.

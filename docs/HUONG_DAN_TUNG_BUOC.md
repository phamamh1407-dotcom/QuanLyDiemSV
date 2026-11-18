# HƯỚNG DẪN TỪNG BƯỚC (làm trong ngày hôm nay)

## BƯỚC 1 — Cài phần mềm (bỏ qua cái nào đã có)
1. **JDK 17**: tải "Temurin JDK 17" (adoptium.net), cài đặt, tick "Add to PATH". Mở CMD gõ `javac -version` → thấy `javac 17...` là xong.
2. **VS Code** + extension **Extension Pack for Java**.
3. **SQL Server** (bản Express/Developer) + **SSMS**.
   - Khi cài chọn **Mixed Mode**, đặt mật khẩu cho `sa`. (Nếu đã cài Windows Authentication: SSMS → chuột phải server → Properties → Security → chọn *SQL Server and Windows Authentication* → OK; Security → Logins → sa → Properties → đổi mật khẩu, Status → Enabled; restart dịch vụ.)
   - Bật TCP/IP: mở **SQL Server Configuration Manager** → SQL Server Network Configuration → Protocols for ... → TCP/IP → **Enabled** → Properties → IP Addresses → kéo xuống **IPAll**: TCP Port = `1433` → OK → vào SQL Server Services → chuột phải SQL Server → **Restart**.

## BƯỚC 2 — Tạo CSDL
1. Mở SSMS, đăng nhập, mở file `sql/QuanLyDiemSV.sql` (File → Open → File).
2. Bấm **Execute** (F5). Các bảng kết quả hiện: Khoa 2 dòng, Lop 3, SinhVien 4, MonHoc 3, Diem 6.

## BƯỚC 3 — Tải JDBC Driver
1. Vào trang Microsoft "Download Microsoft JDBC Driver for SQL Server", tải file zip, giải nén.
2. Vào thư mục `jars`, chép file `mssql-jdbc-xx.x.x.jre11.jar` vào thư mục `lib` của project.

## BƯỚC 4 — Cấu hình kết nối
1. Chạy `chay.bat` (nhấp đúp). Lần đầu nó tạo `db.properties` và mở Notepad.
2. Sửa dòng `password=` thành mật khẩu `sa` của bạn, lưu, đóng.
   (Nếu SQL Server dùng instance tên riêng, ví dụ `localhost\SQLEXPRESS`, hãy dùng port 1433 như bước 1 và giữ `host=localhost`.)

## BƯỚC 5 — Chạy phần mềm
1. Nhấp đúp `chay.bat` lần nữa. Cửa sổ "Quản lý điểm sinh viên" hiện ra, dòng cuối màu xanh "Đã kết nối SQL Server thành công", thống kê hiện 4 / 3 / 6.
2. Nếu dòng đỏ báo lỗi → xem bảng "Lỗi thường gặp" trong `README.md`.
3. Chạy kiểm thử công thức (không cần SQL): nhấp đúp `test.bat` → phải thấy "TẤT CẢ ĐẠT".

## BƯỚC 6 — Tự kiểm tra chức năng (10 phút) rồi chụp ảnh
Làm theo `docs/TEST_CASES.md`, điền cột "Kết quả thực tế". Chụp ảnh 3 form để đưa vào báo cáo.

## BƯỚC 7 — Đưa lên GitHub
1. Tạo tài khoản tại github.com (nếu chưa có). Bấm **New repository** → tên `QuanLyDiemSV` → **Public** → *không* tick "Add README" → Create.
2. Cài **Git for Windows** (git-scm.com). Mở CMD trong thư mục project (gõ `cmd` vào thanh địa chỉ của File Explorer):
```
git config --global user.name "Ten Cua Ban"
git config --global user.email "email@cua-ban.com"
git init
git add .
git status
```
   Kiểm tra `git status`: **không** được thấy `db.properties`. (Thấy `db.properties.example` là đúng.)
```
git commit -m "Do an Java quan ly diem sinh vien"
git branch -M main
git remote add origin https://github.com/TEN_GITHUB_CUA_BAN/QuanLyDiemSV.git
git push -u origin main
```
3. Khi hỏi đăng nhập, đăng nhập GitHub trên cửa sổ trình duyệt hiện ra. Tải lại trang repository, thấy code là xong.
4. Copy link repository (`https://github.com/TEN_GITHUB_CUA_BAN/QuanLyDiemSV`).

## BƯỚC 8 — Nộp link
Vào https://pit.hubt.edu.vn → khóa "Đồ án phần mềm Java" → **Nơi nộp link phần mềm** (hạn **19/11/2026 17:00**) → dán link GitHub → Lưu.

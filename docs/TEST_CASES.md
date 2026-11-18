# TEST CASES

Cột "Kiểu": **Auto** = đã chạy tự động (có kết quả thật); **Tay** = bạn tự chạy trên máy có SQL Server rồi điền kết quả (hiện NOT TESTED).

| ID | Chức năng | Dữ liệu / thao tác | Kết quả mong đợi | Kiểu | Kết quả thực tế |
|---|---|---|---|---|---|
| TC01 | Tính tổng kết | QT 8, Thi 9 | 8.6 | Auto | PASS |
| TC02 | Tính tổng kết | QT 0, Thi 0 / 10, 10 | 0.0 / 10.0 | Auto | PASS |
| TC03 | Xếp loại | 8.0 / 6.5 / 5.0 / 4.9 | Giỏi / Khá / TB / Yếu | Auto | PASS |
| TC04 | Điểm TB 1 môn | 8.0, 3 TC | 8.0 | Auto | PASS |
| TC05 | Điểm TB nhiều môn | (8×3 + 6×2)/5 | 7.2 | Auto | PASS |
| TC06 | Chưa có điểm | Không có môn | Không lỗi, không chia cho 0 | Auto | PASS |
| TC07 | Nhập điểm 11 | "11" | Báo lỗi | Auto | PASS |
| TC08 | Nhập điểm âm | "-1" | Báo lỗi | Auto | PASS |
| TC09 | Nhập chữ / rỗng | "abc", "" | Báo lỗi | Auto | PASS |
| TC10 | Kết nối | Mở chay.bat | Dòng xanh "Đã kết nối" | Tay | NOT TESTED |
| TC11 | Mất kết nối | Dừng dịch vụ SQL Server rồi mở | Dòng đỏ báo lỗi rõ ràng | Tay | NOT TESTED |
| TC12 | Thêm SV | SV009, họ tên, 01/01/2005, lớp CNTT01 | Thêm thành công, hiện trong bảng | Tay | NOT TESTED |
| TC13 | Thêm SV trùng mã | SV001 | Báo "đã tồn tại" | Tay | NOT TESTED |
| TC14 | Thêm SV thiếu tên | để trống Họ tên | Báo thiếu dữ liệu | Tay | NOT TESTED |
| TC15 | Ngày sinh sai | "2005-01-01" hoặc "abc" | Báo sai định dạng | Tay | NOT TESTED |
| TC16 | Sửa SV | Chọn SV009, đổi địa chỉ, Sửa | Cập nhật | Tay | NOT TESTED |
| TC17 | Tìm SV | Từ khóa "An", "SV00", "CNTT01", "xyz" | Có kết quả / thông báo không tìm thấy | Tay | NOT TESTED |
| TC18 | Xóa SV | Chọn SV009, Xóa, Yes | Xóa đúng, No thì không xóa | Tay | NOT TESTED |
| TC19 | Xóa SV có điểm (khóa ngoại) | Chọn SV004, Xóa | Xóa SV cùng điểm, không lỗi | Tay | NOT TESTED |
| TC20 | Thêm điểm | SV003, MH01, 7, 8 | Thêm thành công, TK 7.6 | Tay | NOT TESTED |
| TC21 | Thêm điểm trùng | SV001 + MH01 | Báo đã có điểm | Tay | NOT TESTED |
| TC22 | Sửa điểm | Chọn dòng, đổi Thi → 10 | Tổng kết đổi theo | Tay | NOT TESTED |
| TC23 | Xóa điểm | Chọn dòng, Xóa, Yes | Dòng biến mất | Tay | NOT TESTED |
| TC24 | Tìm điểm | "Java", "SV002" | Lọc đúng | Tay | NOT TESTED |
| TC25 | ĐTB SV001 | Chọn SV001, Tính ĐTB | 2 môn: (7.7×3 + 8.7×3)/6 = 8.2 | Tay | NOT TESTED |
| TC26 | ĐTB SV009 | Thêm SV009 rồi chọn SV009 (chưa có điểm) | Thông báo chưa có điểm | Tay | NOT TESTED |
| TC27 | Thống kê | Thêm/xóa SV rồi về trang chủ | Số liệu tự cập nhật | Tay | NOT TESTED |

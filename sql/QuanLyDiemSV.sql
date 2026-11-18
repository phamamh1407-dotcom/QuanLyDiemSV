-- =====================================================
-- DO AN: PHAN MEM QUAN LY DIEM SINH VIEN BANG JAVA
-- SQL Server | Script tao CSDL va du lieu mau
-- Script co the chay lai: khong tu dong xoa du lieu.
-- =====================================================
IF DB_ID(N'QuanLyDiemSV') IS NULL
    CREATE DATABASE QuanLyDiemSV;
GO
USE QuanLyDiemSV;
GO

IF OBJECT_ID(N'dbo.Khoa', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Khoa (
        MaKhoa VARCHAR(10) NOT NULL CONSTRAINT PK_Khoa PRIMARY KEY,
        TenKhoa NVARCHAR(100) NOT NULL
    );
END;
GO

IF OBJECT_ID(N'dbo.Lop', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Lop (
        MaLop VARCHAR(10) NOT NULL CONSTRAINT PK_Lop PRIMARY KEY,
        TenLop NVARCHAR(100) NOT NULL,
        MaKhoa VARCHAR(10) NOT NULL,
        CONSTRAINT FK_Lop_Khoa FOREIGN KEY (MaKhoa) REFERENCES dbo.Khoa(MaKhoa)
    );
END;
GO

IF OBJECT_ID(N'dbo.SinhVien', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.SinhVien (
        MaSV VARCHAR(10) NOT NULL CONSTRAINT PK_SinhVien PRIMARY KEY,
        HoTen NVARCHAR(100) NOT NULL,
        NgaySinh DATE NULL,
        GioiTinh NVARCHAR(5) NULL,
        DiaChi NVARCHAR(200) NULL,
        MaLop VARCHAR(10) NOT NULL,
        CONSTRAINT CK_SinhVien_GioiTinh CHECK (GioiTinh IS NULL OR GioiTinh IN (N'Nam', N'Nữ')),
        CONSTRAINT FK_SinhVien_Lop FOREIGN KEY (MaLop) REFERENCES dbo.Lop(MaLop)
    );
END;
GO

IF OBJECT_ID(N'dbo.MonHoc', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.MonHoc (
        MaMH VARCHAR(10) NOT NULL CONSTRAINT PK_MonHoc PRIMARY KEY,
        TenMH NVARCHAR(100) NOT NULL,
        SoTinChi INT NOT NULL CONSTRAINT CK_MonHoc_SoTinChi CHECK (SoTinChi > 0)
    );
END;
GO

IF OBJECT_ID(N'dbo.Diem', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Diem (
        MaSV VARCHAR(10) NOT NULL,
        MaMH VARCHAR(10) NOT NULL,
        DiemQT DECIMAL(4,2) NOT NULL CONSTRAINT CK_Diem_DiemQT CHECK (DiemQT BETWEEN 0 AND 10),
        DiemThi DECIMAL(4,2) NOT NULL CONSTRAINT CK_Diem_DiemThi CHECK (DiemThi BETWEEN 0 AND 10),
        DiemTK AS (CONVERT(DECIMAL(4,2), DiemQT * 0.4 + DiemThi * 0.6)) PERSISTED,
        CONSTRAINT PK_Diem PRIMARY KEY (MaSV, MaMH),
        CONSTRAINT FK_Diem_SinhVien FOREIGN KEY (MaSV) REFERENCES dbo.SinhVien(MaSV) ON DELETE CASCADE,
        CONSTRAINT FK_Diem_MonHoc FOREIGN KEY (MaMH) REFERENCES dbo.MonHoc(MaMH)
    );
END;
GO

-- Du lieu mau: chi them ban ghi chua ton tai.
IF NOT EXISTS (SELECT 1 FROM dbo.Khoa WHERE MaKhoa='CNTT')
    INSERT dbo.Khoa(MaKhoa, TenKhoa) VALUES ('CNTT', N'Công nghệ thông tin');
IF NOT EXISTS (SELECT 1 FROM dbo.Khoa WHERE MaKhoa='KT')
    INSERT dbo.Khoa(MaKhoa, TenKhoa) VALUES ('KT', N'Kế toán');

IF NOT EXISTS (SELECT 1 FROM dbo.Lop WHERE MaLop='CNTT01')
    INSERT dbo.Lop(MaLop, TenLop, MaKhoa) VALUES ('CNTT01', N'Công nghệ thông tin 01', 'CNTT');
IF NOT EXISTS (SELECT 1 FROM dbo.Lop WHERE MaLop='CNTT02')
    INSERT dbo.Lop(MaLop, TenLop, MaKhoa) VALUES ('CNTT02', N'Công nghệ thông tin 02', 'CNTT');
IF NOT EXISTS (SELECT 1 FROM dbo.Lop WHERE MaLop='KT01')
    INSERT dbo.Lop(MaLop, TenLop, MaKhoa) VALUES ('KT01', N'Kế toán 01', 'KT');

IF NOT EXISTS (SELECT 1 FROM dbo.SinhVien WHERE MaSV='SV001')
    INSERT dbo.SinhVien(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop)
    VALUES ('SV001', N'Nguyễn Văn An', '2005-03-12', N'Nam', N'Hà Nội', 'CNTT01');
IF NOT EXISTS (SELECT 1 FROM dbo.SinhVien WHERE MaSV='SV002')
    INSERT dbo.SinhVien(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop)
    VALUES ('SV002', N'Trần Thị Bình', '2005-07-25', N'Nữ', N'Hải Phòng', 'CNTT01');
IF NOT EXISTS (SELECT 1 FROM dbo.SinhVien WHERE MaSV='SV003')
    INSERT dbo.SinhVien(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop)
    VALUES ('SV003', N'Lê Minh Cường', '2005-11-02', N'Nam', N'Nam Định', 'CNTT02');
IF NOT EXISTS (SELECT 1 FROM dbo.SinhVien WHERE MaSV='SV004')
    INSERT dbo.SinhVien(MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop)
    VALUES ('SV004', N'Phạm Thu Dung', '2005-01-18', N'Nữ', N'Hà Nam', 'KT01');

IF NOT EXISTS (SELECT 1 FROM dbo.MonHoc WHERE MaMH='MH01')
    INSERT dbo.MonHoc(MaMH, TenMH, SoTinChi) VALUES ('MH01', N'Lập trình Java', 3);
IF NOT EXISTS (SELECT 1 FROM dbo.MonHoc WHERE MaMH='MH02')
    INSERT dbo.MonHoc(MaMH, TenMH, SoTinChi) VALUES ('MH02', N'Cơ sở dữ liệu', 3);
IF NOT EXISTS (SELECT 1 FROM dbo.MonHoc WHERE MaMH='MH03')
    INSERT dbo.MonHoc(MaMH, TenMH, SoTinChi) VALUES ('MH03', N'Toán rời rạc', 2);

IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV001' AND MaMH='MH01')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV001','MH01',8.0,7.5);
IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV001' AND MaMH='MH02')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV001','MH02',9.0,8.5);
IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV002' AND MaMH='MH01')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV002','MH01',7.0,6.5);
IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV002' AND MaMH='MH03')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV002','MH03',8.0,9.0);
IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV003' AND MaMH='MH02')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV003','MH02',6.0,7.0);
IF NOT EXISTS (SELECT 1 FROM dbo.Diem WHERE MaSV='SV004' AND MaMH='MH03')
    INSERT dbo.Diem(MaSV, MaMH, DiemQT, DiemThi) VALUES ('SV004','MH03',9.0,9.5);
GO

-- Kiem tra nhanh sau khi chay script
SELECT * FROM dbo.Khoa;
SELECT * FROM dbo.Lop;
SELECT * FROM dbo.SinhVien;
SELECT * FROM dbo.MonHoc;
SELECT * FROM dbo.Diem;

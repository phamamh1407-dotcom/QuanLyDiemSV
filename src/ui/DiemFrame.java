package ui;

import dao.DiemDAO;
import dao.MonHocDAO;
import dao.SinhVienDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Diem;
import model.MonHoc;
import model.SinhVien;
import util.TinhDiem;

/** FORM 3 - Quản lý điểm: thêm, sửa, xóa, tìm kiếm, tính điểm tổng kết và điểm trung bình. */
public class DiemFrame extends JFrame {
    private JComboBox<String> cboSV = new JComboBox<>();
    private JComboBox<MonHoc> cboMon = new JComboBox<>();
    private JTextField txtQT = new JTextField(6);
    private JTextField txtThi = new JTextField(6);
    private JLabel lblTongKet = new JLabel("Tổng kết: -");
    private JTextField txtTim = new JTextField(18);
    private DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã SV", "Họ tên", "Mã MH", "Tên môn", "Tín chỉ", "Điểm QT", "Điểm thi", "Tổng kết", "Xếp loại"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private JTable table = new JTable(model);
    private DiemDAO dao = new DiemDAO();

    public DiemFrame() {
        setTitle("Quản lý điểm");
        setSize(980, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel nhap = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        nhap.setBorder(BorderFactory.createTitledBorder("Nhập điểm (Tổng kết = QT x 40% + Thi x 60%)"));
        nhap.add(new JLabel("Sinh viên:"));
        nhap.add(cboSV);
        nhap.add(new JLabel("Môn học:"));
        nhap.add(cboMon);
        nhap.add(new JLabel("Điểm QT:"));
        nhap.add(txtQT);
        nhap.add(new JLabel("Điểm thi:"));
        nhap.add(txtThi);
        nhap.add(lblTongKet);

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnMoi = new JButton("Làm mới");
        JButton btnDTB = new JButton("Tính ĐTB sinh viên");
        JButton btnTim = new JButton("Tìm kiếm");
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        nut.add(btnThem);
        nut.add(btnSua);
        nut.add(btnXoa);
        nut.add(btnMoi);
        nut.add(btnDTB);
        nut.add(new JLabel("   Từ khóa:"));
        nut.add(txtTim);
        nut.add(btnTim);

        JPanel tren = new JPanel(new BorderLayout());
        tren.add(nhap, BorderLayout.CENTER);
        tren.add(nut, BorderLayout.SOUTH);
        add(tren, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnMoi.addActionListener(e -> lamMoi());
        btnDTB.addActionListener(e -> tinhDTB());
        btnTim.addActionListener(e -> napBang(txtTim.getText()));
        txtTim.addActionListener(e -> napBang(txtTim.getText()));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) chonDong();
        });

        napCombo();
        napBang("");
    }

    private void napCombo() {
        try {
            cboSV.removeAllItems();
            for (SinhVien s : new SinhVienDAO().layTatCa()) cboSV.addItem(s.getMaSV() + " - " + s.getHoTen());
            cboMon.removeAllItems();
            for (MonHoc m : new MonHocDAO().layTatCa()) cboMon.addItem(m);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void napBang(String tuKhoa) {
        try {
            model.setRowCount(0);
            List<Diem> ds = dao.timKiem(tuKhoa);
            for (Diem d : ds) {
                model.addRow(new Object[]{d.getMaSV(), d.getHoTen(), d.getMaMH(), d.getTenMH(),
                        d.getSoTinChi(), d.getDiemQT(), d.getDiemThi(), d.getTongKet(), d.getXepLoai()});
            }
            if (ds.isEmpty() && !tuKhoa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp.");
            }
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private String maSVDangChon() {
        Object o = cboSV.getSelectedItem();
        return o == null ? null : o.toString().split(" - ")[0];
    }

    private void chonDong() {
        int r = table.getSelectedRow();
        if (r < 0) return;
        String maSV = model.getValueAt(r, 0).toString();
        String maMH = model.getValueAt(r, 2).toString();
        for (int i = 0; i < cboSV.getItemCount(); i++) {
            if (cboSV.getItemAt(i).startsWith(maSV + " - ")) cboSV.setSelectedIndex(i);
        }
        for (int i = 0; i < cboMon.getItemCount(); i++) {
            if (cboMon.getItemAt(i).getMaMH().equals(maMH)) cboMon.setSelectedIndex(i);
        }
        txtQT.setText(model.getValueAt(r, 5).toString());
        txtThi.setText(model.getValueAt(r, 6).toString());
        lblTongKet.setText("Tổng kết: " + model.getValueAt(r, 7) + " (" + model.getValueAt(r, 8) + ")");
        cboSV.setEnabled(false); // khi sửa không đổi sinh viên/môn
        cboMon.setEnabled(false);
    }

    private void lamMoi() {
        txtQT.setText("");
        txtThi.setText("");
        txtTim.setText("");
        lblTongKet.setText("Tổng kết: -");
        cboSV.setEnabled(true);
        cboMon.setEnabled(true);
        table.clearSelection();
        napBang("");
    }

    private void xuLyThem() {
        String maSV = maSVDangChon();
        MonHoc mon = (MonHoc) cboMon.getSelectedItem();
        if (maSV == null || mon == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn sinh viên và môn học.");
            return;
        }
        try {
            double qt = TinhDiem.docDiem(txtQT.getText(), "Điểm quá trình");
            double thi = TinhDiem.docDiem(txtThi.getText(), "Điểm thi");
            if (dao.tonTai(maSV, mon.getMaMH())) {
                JOptionPane.showMessageDialog(this, "Sinh viên này đã có điểm môn " + mon.getTenMH()
                        + ". Hãy chọn dòng đó và bấm Sửa.", "Trùng dữ liệu", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dao.them(maSV, mon.getMaMH(), qt, thi);
            JOptionPane.showMessageDialog(this, "Thêm điểm thành công. Tổng kết: " + TinhDiem.tongKet(qt, thi)
                    + " (" + TinhDiem.xepLoai(TinhDiem.tongKet(qt, thi)) + ")");
            lamMoi();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Sai dữ liệu", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void xuLySua() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng điểm trong bảng để sửa.");
            return;
        }
        try {
            double qt = TinhDiem.docDiem(txtQT.getText(), "Điểm quá trình");
            double thi = TinhDiem.docDiem(txtThi.getText(), "Điểm thi");
            String maSV = model.getValueAt(r, 0).toString();
            String maMH = model.getValueAt(r, 2).toString();
            if (dao.sua(maSV, maMH, qt, thi) == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy bản ghi để sửa.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công.");
            lamMoi();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Sai dữ liệu", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void xuLyXoa() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng điểm trong bảng để xóa.");
            return;
        }
        int kq = JOptionPane.showConfirmDialog(this, "Xóa điểm môn " + model.getValueAt(r, 3)
                + " của sinh viên " + model.getValueAt(r, 0) + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (kq != JOptionPane.YES_OPTION) return;
        try {
            dao.xoa(model.getValueAt(r, 0).toString(), model.getValueAt(r, 2).toString());
            JOptionPane.showMessageDialog(this, "Đã xóa điểm.");
            lamMoi();
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    /** Tính điểm trung bình (theo tín chỉ) của sinh viên đang chọn ở ô Sinh viên. */
    private void tinhDTB() {
        String maSV = maSVDangChon();
        if (maSV == null) {
            JOptionPane.showMessageDialog(this, "Hãy chọn sinh viên.");
            return;
        }
        try {
            List<Diem> ds = dao.layTheoSinhVien(maSV);
            if (ds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sinh viên " + maSV + " chưa có điểm môn nào.");
                return;
            }
            double[] tk = new double[ds.size()];
            int[] tc = new int[ds.size()];
            for (int i = 0; i < ds.size(); i++) {
                tk[i] = ds.get(i).getTongKet();
                tc[i] = ds.get(i).getSoTinChi();
            }
            double dtb = TinhDiem.trungBinh(tk, tc);
            JOptionPane.showMessageDialog(this, "Sinh viên " + maSV + " có " + ds.size() + " môn.\n"
                    + "Điểm trung bình: " + dtb + "\nXếp loại: " + TinhDiem.xepLoai(dtb),
                    "Điểm trung bình", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void baoLoi(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu:\n" + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

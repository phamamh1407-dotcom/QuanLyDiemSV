package ui;

import dao.SinhVienDAO;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.SinhVien;

/** FORM 2 - Quản lý sinh viên: thêm, sửa, xóa, tìm kiếm. */
public class SinhVienFrame extends JFrame {
    private static final DateTimeFormatter DINH_DANG = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtMa = new JTextField(12);
    private JTextField txtTen = new JTextField(20);
    private JTextField txtNgaySinh = new JTextField(10);
    private JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
    private JTextField txtDiaChi = new JTextField(25);
    private JComboBox<String> cboLop = new JComboBox<>();
    private JTextField txtTim = new JTextField(18);
    private DefaultTableModel model = new DefaultTableModel(
            new String[]{"Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Lớp"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private JTable table = new JTable(model);
    private SinhVienDAO dao = new SinhVienDAO();

    public SinhVienFrame() {
        setTitle("Quản lý sinh viên");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // --- Khung nhập liệu ---
        JPanel nhap = new JPanel(new GridBagLayout());
        nhap.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;
        them(nhap, g, 0, 0, new JLabel("Mã SV (*):"));
        them(nhap, g, 1, 0, txtMa);
        them(nhap, g, 2, 0, new JLabel("Họ tên (*):"));
        them(nhap, g, 3, 0, txtTen);
        them(nhap, g, 0, 1, new JLabel("Ngày sinh (dd/MM/yyyy) (*):"));
        them(nhap, g, 1, 1, txtNgaySinh);
        them(nhap, g, 2, 1, new JLabel("Giới tính:"));
        them(nhap, g, 3, 1, cboGioiTinh);
        them(nhap, g, 0, 2, new JLabel("Địa chỉ:"));
        them(nhap, g, 1, 2, txtDiaChi);
        them(nhap, g, 2, 2, new JLabel("Lớp (*):"));
        them(nhap, g, 3, 2, cboLop);

        // --- Nút chức năng ---
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnMoi = new JButton("Làm mới");
        JButton btnTim = new JButton("Tìm kiếm");
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        nut.add(btnThem);
        nut.add(btnSua);
        nut.add(btnXoa);
        nut.add(btnMoi);
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

        // --- Sự kiện ---
        btnThem.addActionListener(e -> xuLyThem());
        btnSua.addActionListener(e -> xuLySua());
        btnXoa.addActionListener(e -> xuLyXoa());
        btnMoi.addActionListener(e -> lamMoi());
        btnTim.addActionListener(e -> napBang(txtTim.getText()));
        txtTim.addActionListener(e -> napBang(txtTim.getText()));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) chonDong();
        });

        napLop();
        napBang("");
    }

    private void them(JPanel p, GridBagConstraints g, int x, int y, JComponent c) {
        g.gridx = x;
        g.gridy = y;
        p.add(c, g);
    }

    private void napLop() {
        try {
            cboLop.removeAllItems();
            for (String l : dao.layDanhSachLop()) cboLop.addItem(l);
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void napBang(String tuKhoa) {
        try {
            model.setRowCount(0);
            List<SinhVien> ds = dao.timKiem(tuKhoa);
            for (SinhVien s : ds) {
                model.addRow(new Object[]{s.getMaSV(), s.getHoTen(), (s.getNgaySinh() == null ? "" : s.getNgaySinh().format(DINH_DANG)),
                        s.getGioiTinh(), s.getDiaChi(), s.getMaLop()});
            }
            if (ds.isEmpty() && !tuKhoa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên phù hợp.");
            }
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void chonDong() {
        int r = table.getSelectedRow();
        if (r < 0) return;
        txtMa.setText(model.getValueAt(r, 0).toString());
        txtTen.setText(model.getValueAt(r, 1).toString());
        txtNgaySinh.setText(model.getValueAt(r, 2).toString());
        cboGioiTinh.setSelectedItem(model.getValueAt(r, 3));
        Object dc = model.getValueAt(r, 4);
        txtDiaChi.setText(dc == null ? "" : dc.toString());
        cboLop.setSelectedItem(model.getValueAt(r, 5));
        txtMa.setEditable(false); // không cho sửa mã khi đang sửa
    }

    private void lamMoi() {
        txtMa.setText("");
        txtTen.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtTim.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtMa.setEditable(true);
        table.clearSelection();
        napBang("");
    }

    /** Đọc và kiểm tra dữ liệu trên form. Trả về null nếu có lỗi (đã báo cho người dùng). */
    private SinhVien docForm() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String ns = txtNgaySinh.getText().trim();
        if (ma.isEmpty() || ten.isEmpty() || ns.isEmpty() || cboLop.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Mã SV, Họ tên, Ngày sinh và chọn Lớp.",
                    "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (!ma.matches("[A-Za-z0-9]{1,10}")) {
            JOptionPane.showMessageDialog(this, "Mã SV chỉ gồm chữ và số, tối đa 10 ký tự.",
                    "Sai dữ liệu", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        LocalDate ngay;
        try {
            ngay = LocalDate.parse(ns, DINH_DANG);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh sai định dạng. Ví dụ đúng: 25/03/2005",
                    "Sai dữ liệu", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (ngay.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được lớn hơn ngày hôm nay.",
                    "Sai dữ liệu", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return new SinhVien(ma.toUpperCase(), ten, ngay, (String) cboGioiTinh.getSelectedItem(),
                txtDiaChi.getText().trim(), (String) cboLop.getSelectedItem());
    }

    private void xuLyThem() {
        SinhVien sv = docForm();
        if (sv == null) return;
        try {
            if (dao.tonTai(sv.getMaSV())) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên " + sv.getMaSV() + " đã tồn tại.",
                        "Trùng mã", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dao.them(sv);
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công.");
            lamMoi();
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void xuLySua() {
        if (table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một sinh viên trong bảng để sửa.");
            return;
        }
        SinhVien sv = docForm();
        if (sv == null) return;
        try {
            int n = dao.sua(sv);
            if (n == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên để sửa.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
            lamMoi();
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void xuLyXoa() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một sinh viên trong bảng để xóa.");
            return;
        }
        String ma = model.getValueAt(r, 0).toString();
        int kq = JOptionPane.showConfirmDialog(this,
                "Xóa sinh viên " + ma + "?\nTất cả điểm của sinh viên này cũng sẽ bị xóa.",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (kq != JOptionPane.YES_OPTION) return;
        try {
            dao.xoa(ma);
            JOptionPane.showMessageDialog(this, "Đã xóa sinh viên.");
            lamMoi();
        } catch (SQLException ex) {
            baoLoi(ex);
        }
    }

    private void baoLoi(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu:\n" + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

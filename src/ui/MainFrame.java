package ui;

import dao.ThongKeDAO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/** FORM 1 - Trang chủ: menu và thống kê. */
public class MainFrame extends JFrame {
    private JLabel lblSV = new JLabel("-");
    private JLabel lblMH = new JLabel("-");
    private JLabel lblDiem = new JLabel("-");
    private JLabel lblTrangThai = new JLabel(" ");

    public MainFrame() {
        setTitle("Quản lý điểm sinh viên");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel tieuDe = new JLabel("PHẦN MỀM QUẢN LÝ ĐIỂM SINH VIÊN", SwingConstants.CENTER);
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tieuDe.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(tieuDe, BorderLayout.NORTH);

        JPanel thongKe = new JPanel(new GridLayout(1, 3, 15, 0));
        thongKe.setBorder(BorderFactory.createTitledBorder("Thống kê"));
        thongKe.add(oThongKe("Tổng số sinh viên", lblSV));
        thongKe.add(oThongKe("Tổng số môn học", lblMH));
        thongKe.add(oThongKe("Số bản ghi điểm", lblDiem));

        JButton btnSV = new JButton("Quản lý sinh viên");
        JButton btnDiem = new JButton("Quản lý điểm");
        JButton btnThoat = new JButton("Thoát");
        for (JButton b : new JButton[]{btnSV, btnDiem, btnThoat}) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            b.setPreferredSize(new Dimension(200, 45));
        }
        JPanel nut = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        nut.add(btnSV);
        nut.add(btnDiem);
        nut.add(btnThoat);

        JPanel giua = new JPanel(new BorderLayout(10, 10));
        giua.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        giua.add(thongKe, BorderLayout.NORTH);
        giua.add(nut, BorderLayout.CENTER);
        add(giua, BorderLayout.CENTER);

        lblTrangThai.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        add(lblTrangThai, BorderLayout.SOUTH);

        btnSV.addActionListener(e -> new SinhVienFrame().setVisible(true));
        btnDiem.addActionListener(e -> new DiemFrame().setVisible(true));
        btnThoat.addActionListener(e -> System.exit(0));

        // Cập nhật thống kê mỗi khi quay lại trang chủ
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) { napThongKe(); }
        });
        napThongKe();
    }

    private JPanel oThongKe(String ten, JLabel giaTri) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        JLabel t = new JLabel(ten, SwingConstants.CENTER);
        giaTri.setHorizontalAlignment(SwingConstants.CENTER);
        giaTri.setFont(new Font("Segoe UI", Font.BOLD, 28));
        p.add(t);
        p.add(giaTri);
        return p;
    }

    private void napThongKe() {
        try {
            ThongKeDAO dao = new ThongKeDAO();
            lblSV.setText(String.valueOf(dao.demSinhVien()));
            lblMH.setText(String.valueOf(dao.demMonHoc()));
            lblDiem.setText(String.valueOf(dao.demDiem()));
            lblTrangThai.setForeground(new Color(0, 120, 0));
            lblTrangThai.setText("Đã kết nối SQL Server thành công.");
        } catch (Exception ex) {
            lblSV.setText("-");
            lblMH.setText("-");
            lblDiem.setText("-");
            lblTrangThai.setForeground(Color.RED);
            lblTrangThai.setText("Lỗi kết nối CSDL: " + ex.getMessage());
        }
    }
}

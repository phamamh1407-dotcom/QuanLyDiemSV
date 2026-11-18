import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.MainFrame;

/** Điểm bắt đầu của chương trình. */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // bỏ qua, dùng giao diện mặc định
        }
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

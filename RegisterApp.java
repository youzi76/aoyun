import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterApp extends JFrame {
    private LoginApp loginApp;

    public RegisterApp(LoginApp loginApp) {
        this.loginApp = loginApp;
        setTitle("注册");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label1 = new JLabel("用户名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label1, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        JLabel label2 = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label2, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        JButton registerButton = new JButton("注册");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        JButton cancelButton = new JButton("取消");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(cancelButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 连接数据库并插入用户信息
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aoyuncaipan", "root", "Hzt14701470");
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO caipan (name, paw) VALUES (?, ?)");
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    int affectedRows = stmt.executeUpdate();

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(RegisterApp.this, "注册成功！");
                        loginApp.showLoginApp(); // 重新显示登录界面
                        dispose(); // 关闭注册界面
                    } else {
                        JOptionPane.showMessageDialog(RegisterApp.this, "注册失败，请重试！");
                    }

                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginApp.showLoginApp(); // 重新显示登录界面
                dispose(); // 取消注册，关闭注册界面
            }
        });

        add(panel);
    }
}

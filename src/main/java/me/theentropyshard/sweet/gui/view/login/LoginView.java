/*
 * Sweet - https://github.com/TheEntropyShard/Sweet
 * Copyright (C) 2025 TheEntropyShard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.sweet.gui.view.login;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import me.theentropyshard.sweet.gui.RemoveOutlineOnInputDocumentListener;

public class LoginView extends JPanel {
    private final LoginListener listener;

    private final JTextField instanceField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginView(LoginListener listener) {
        this.listener = listener;

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel container = new JPanel(new MigLayout("fillx, insets 24 32 32 32")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(UIManager.getColor("secondaryContainer"));
                g2d.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 16, 16);

                g2d.setColor(UIManager.getColor("background"));
                g2d.fillRoundRect(8, 8, this.getWidth() - 17, this.getHeight() - 17, 8, 8);

                super.paintComponent(g2d);
            }
        };
        container.setOpaque(false);

        JLabel label = new JLabel("<html><b>Login</b></html>");
        label.setFont(label.getFont().deriveFont(24f));
        container.add(label, "wrap, al 50% 50%");

        container.add(new JLabel("<html><b>Instance</b></html>"), "wrap");
        this.instanceField = new JTextField();
        this.instanceField.putClientProperty(FlatClientProperties.STYLE, "arc: 16");
        RemoveOutlineOnInputDocumentListener.install(this.instanceField);
        container.add(this.instanceField, "wmin 320px, hmin 32px, growx, wrap 16px");

        container.add(new JLabel("<html><b>Username</b></html>"), "wrap");
        this.usernameField = new JTextField();
        this.usernameField.putClientProperty(FlatClientProperties.STYLE, "arc: 16");
        RemoveOutlineOnInputDocumentListener.install(this.usernameField);
        container.add(this.usernameField, "wmin 320px, hmin 32px, growx, wrap 16px");

        container.add(new JLabel("<html><b>Password</b></html>"), "wrap");
        this.passwordField = new JPasswordField();
        this.passwordField.putClientProperty(FlatClientProperties.STYLE, "arc: 16");
        RemoveOutlineOnInputDocumentListener.install(this.passwordField);
        container.add(this.passwordField, "wmin 320px, hmin 32px, growx, wrap 32px");

        JButton button = new JButton("<html><b>Login</b></html>");
        button.putClientProperty(FlatClientProperties.STYLE, "arc: 16; background: $primary; foreground: $onPrimary");
        button.addActionListener(this::onLoginButtonClicked);
        container.add(button, "hmin 32px, growx");

        this.add(container, gbc);

        this.instanceField.setText(System.getenv("SWEET_INSTANCE"));
        this.usernameField.setText(System.getenv("SWEET_USERNAME"));
        this.passwordField.setText(System.getenv("SWEET_PASSWORD"));
    }

    private void onLoginButtonClicked(ActionEvent e) {
        String instance = this.instanceField.getText().trim();

        if (instance.isEmpty()) {
            this.instanceField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            this.instanceField.requestFocus();

            return;
        }

        String username = this.usernameField.getText().trim();

        if (username.isEmpty()) {
            this.usernameField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            this.usernameField.requestFocus();

            return;
        }

        String password = new String(this.passwordField.getPassword()).trim();

        if (password.isEmpty()) {
            this.passwordField.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
            this.passwordField.requestFocus();

            return;
        }

        this.listener.onLogin(instance, username, password);
    }

    public interface LoginListener {
        void onLogin(String instance, String username, String password);
    }
}
